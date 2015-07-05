package pl.appsilon.marek.sparkdatalog.eval

import pl.appsilon.marek.sparkdatalog
import pl.appsilon.marek.sparkdatalog.{DatabaseRepr, Database}
import pl.appsilon.marek.sparkdatalog.ast.Program
import pl.appsilon.marek.sparkdatalog.ast.rule.Rule
import pl.appsilon.marek.sparkdatalog.eval.nonsharded.NonshardedState
import pl.appsilon.marek.sparkdatalog.util.Timed

object SparkEvaluator {

  private def makeIteration(
      staticContext: StaticEvaluationContext,
      rules: Iterable[Rule],
      state: NonshardedState,
      calculateDelta: Boolean,
      onlyRecursiveRules: Boolean): NonshardedState = {
    val generatedRelations = rules.filter(!onlyRecursiveRules || _.isRecursiveInStratum).map(_.evaluateOnSpark(staticContext, state))
    val (newFullDatabase, deltaDatabase) = state.database.mergeIn(generatedRelations)
    state.copy(database = newFullDatabase, previousDatabase = state.database, delta = deltaDatabase)
  }

  def evaluate(database: Database, program: Program): Database = {
    var state = NonshardedState.fromDatabase(DatabaseRepr(database, program.aggregations))
    var iteration = 0
    state.cache()

    val strata: Seq[Seq[Rule]] = Stratify(program)
    val checkpointFrequency = 5

    for ((stratum, stratumId) <- strata.zipWithIndex) {

      val idb = stratum.map(_.head.name).toSet
      state = state.prepareForIteration(idb)
      stratum.foreach(_.analyze(state))
      iteration = 0
      val maxIters = if(stratum.size == 1 && !stratum.head.isRecursive) 1 else Int.MaxValue

      do {
        val oldState = state
        val isFirstIteration = iteration == 0
        val isLastIteration = iteration + 1 >= maxIters
        state = makeIteration(StaticEvaluationContext(program.aggregations), stratum, state, !isLastIteration, !isFirstIteration)
        state.cache()
        if((iteration + checkpointFrequency - 1) % checkpointFrequency == 0) Timed("checkpoint", state.checkpoint())
        Timed("materialize", state.materialize())
        if (!isFirstIteration) {
          oldState.unpersistExceptLast(blocking = false)
        }

        iteration += 1
      } while (iteration < maxIters && !state.deltaEmpty)
    }

    state.database.toDatabase
  }
}
