package pl.appsilon.marek.sparkdatalog.eval

import pl.appsilon.marek.sparkdatalog
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
      calculateDelta: Boolean,
      onlyRecursiveRules: Boolean): NonshardedState = {
    //println("making interation, delta=" + state.delta.relations.values.headOption.map(_.data.mapPartitionsWithIndex({ case (ind, f) => if(f.isEmpty) Iterator() else Iterator(ind -> f.toSeq) }).collect().mkString(", ")))
    //println("partitions: " + state.database.relations.values.map(_.data.partitions.size) + " delta "  + state.delta.relations.values.map(_.data.partitions.size))

    val generatedRelations = rules.filter(!onlyRecursiveRules || _.isRecursiveInStratum).map(_.evaluateOnSpark(staticContext, state))
    val newFullDatabase = state.database.mergeIn(generatedRelations, staticContext.aggregations).coalesce(sparkdatalog.numPartitions)
    if(calculateDelta) {
      state.step(newFullDatabase)
    } else {
      state.copy(database = newFullDatabase, delta = Database.empty)
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
      state = state.prepareForIteration(idb)
      stratum.foreach(_.analyze(state))
      iteration = 0
      val maxIters = if(stratum.size == 1 && !stratum.head.isRecursive) 1 else Int.MaxValue

      do {
        println("Making iteration " + iteration)

        val oldState = state
        val isFirstIteration = iteration == 0
        val isLastIteration = iteration + 1 >= maxIters
        state = makeIteration(StaticEvaluationContext(program.aggregations), stratum, state, !isLastIteration, !isFirstIteration)
        state.cache()
        if((iteration + checkpointFrequency - 1) % checkpointFrequency == 0) Timed("checkpoint", state.checkpoint())
        Timed("materialize", state.materialize())
        oldState.unpersist(blocking = false)

        iteration += 1
      } while (iteration < maxIters && !state.deltaEmpty)
    }

//    println(state.toString)

    state.toDatabase
  }
}
