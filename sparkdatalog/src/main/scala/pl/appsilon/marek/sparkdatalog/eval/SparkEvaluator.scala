package pl.appsilon.marek.sparkdatalog.eval

import pl.appsilon.marek.sparkdatalog.Database
import pl.appsilon.marek.sparkdatalog.ast.Program
import pl.appsilon.marek.sparkdatalog.ast.rule.Rule
import pl.appsilon.marek.sparkdatalog.eval.nonsharded.NonshardedState
import pl.appsilon.marek.sparkdatalog.util.Timed

object SparkEvaluator {

  private def makeIteration(
      staticContext: StaticEvaluationContext,
      rules: Iterable[Rule],
      state: NonshardedState,
      calculateDelta: Boolean): NonshardedState = {
    println("making interation, delta=" + state.delta.relations.keys.toString)

    val generatedRelations = rules.map(_.evaluateOnSpark(staticContext, state))
    val newFullDatabase = state.database.mergeIn(generatedRelations, staticContext.aggregations)
    if(calculateDelta) {
      state.step(newFullDatabase)
    } else {
      NonshardedState(newFullDatabase)
    }
  }

  def evaluate(database: Database, program: Program): Database = {
    var state = NonshardedState.fromDatabase(database)
    var iteration = 0
    state.cache()

    val strata: Seq[Seq[Rule]] = Stratify(program)
    val checkpointFrequency = 5

    for ((stratum, stratumId) <- strata.zipWithIndex) {

      println("Processing stratum %d: %s".format(stratumId, stratum.toString()))
      val idb = stratum.map(_.head.name).toSet
      stratum.foreach(_.analyze(idb))
      state = state.prepareForIteration(idb)
      iteration = 0
      val maxIters = if(stratum.size == 1 && !stratum.head.isRecursive) 1 else Int.MaxValue

      do {
        println("Making iteration " + iteration)

        val oldState = state
        val isLastIteration = iteration + 1 >= maxIters
        state = makeIteration(StaticEvaluationContext(program.aggregations), stratum, state, !isLastIteration)
        state.cache()
        Timed("checkpoint", state.checkpoint())
        Timed("materialize", state.materialize())
        oldState.unpersist(blocking = false)

        iteration += 1
      } while (iteration < maxIters && !state.deltaEmpty)
    }

//    println(state.toString)

    state.toDatabase
  }
}
