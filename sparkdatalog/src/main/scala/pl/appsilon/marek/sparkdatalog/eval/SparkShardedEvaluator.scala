package pl.appsilon.marek.sparkdatalog.eval

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.Database
import pl.appsilon.marek.sparkdatalog.ast.Program
import pl.appsilon.marek.sparkdatalog.ast.rule.Rule

object SparkShardedEvaluator {

  private def makeIteration(
      staticContext: StaticEvaluationContext,
      rules: Iterable[Rule],
      state: State): State = {
    def generateMessages(key: Long, shard: StateShard): Seq[(Long, RelationInstance)] = {
      rules.map(_.evaluate(staticContext, shard)).flatten.reduce(_ ++ _)
    }

    val messages = state.shards.flatMap(Function.tupled(generateMessages)).reduceByKey(_.merge(_, staticContext))
    val newStateShards: RDD[(Long, StateShard)] = state.shards.leftOuterJoin(messages) map {
      case (key, (left, Some(right))) => (key, left.merge(right, staticContext))
      case (key, (left, None)) => (key, left)
    }

    state.step(newStateShards)
  }

  def evaluate(database: Database, program: Program): Database = {
    var state = State.fromDatabase(database)
    var iteration = 0
    state.cache()

    println("Initial state= " + state.toString)

    do {
      println("Making iteration " + iteration)

      val oldState = state
      state = makeIteration(StaticEvaluationContext(program.aggregations), program.rules, state)
      state.cache()
      //state.materialize()
      oldState.unpersist(blocking = false)

      iteration += 1
    } while (!state.deltaEmpty)

    println(state.toString)

    state.toDatabase(program.tables.map(_.name))
  }
}
