package pl.appsilon.marek.sparkdatalog.eval

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.Database
import pl.appsilon.marek.sparkdatalog.ast.Program
import pl.appsilon.marek.sparkdatalog.ast.rule.Rule
import pl.appsilon.marek.sparkdatalog.eval.nonsharded.NonshardedState
import pl.appsilon.marek.sparkdatalog.spark.OuterJoinableRDD._

object SparkEvaluator {

  private def makeIteration(
      staticContext: StaticEvaluationContext,
      rules: Iterable[Rule],
      state: NonshardedState): NonshardedState = {
    //println("making interation, delta=" + state.delta.toString)

    val generatedRelations = rules.map(_.evaluateOnSpark(staticContext, state))
    val newFullDatabase = state.database.mergeIn(generatedRelations, staticContext.aggregations)
    state.step(newFullDatabase)
  }

  def evaluate(database: Database, program: Program): Database = {
    var state = NonshardedState.fromDatabase(database)
    var iteration = 0
    state.cache()

    val strata: Seq[Seq[Rule]] = Stratify(program)
    val checkpointFrequency = 5

    for ((stratum, stratumId) <- strata.zipWithIndex) {

      println("Processing stratum %d: %s".format(stratumId, stratum.toString()))
      state = state.withAllInDelta
      iteration = 0

      do {
        println("Making iteration " + iteration)

        val oldState = state
        state = makeIteration(StaticEvaluationContext(program.aggregations), stratum, state)
        state.cache()
        state.checkpoint()
        state.materialize()
        oldState.unpersist(blocking = false)

        iteration += 1
      } while (!state.deltaEmpty)
    }

//    println(state.toString)

    state.toDatabase
  }
}
