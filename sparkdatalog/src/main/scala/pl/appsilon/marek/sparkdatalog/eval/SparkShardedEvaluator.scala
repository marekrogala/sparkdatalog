package pl.appsilon.marek.sparkdatalog.eval

import org.apache.spark.SparkContext._
import pl.appsilon.marek.sparkdatalog.spark.OuterJoinableRDD._
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
      val intermediate = rules.map(_.evaluate(staticContext, shard))
      //println("generateMessages(%s, %s): \n  result: %s".format(key.toString, shard.toString, intermediate.toString))  // TODO: duplikaty?!
      val result = intermediate.flatten.reduce(_ ++ _)

      result
    }

    val messages: RDD[(Long, RelationInstance)] = state.shards.flatMap(Function.tupled(generateMessages)).reduceByKey(_.merge(_, staticContext))
    //println("messages: \n\t" + messages.collect().mkString(", "))

    val newStateShards: RDD[(Long, StateShard)] = state.shards.fullOuterJoin(messages) map {
      case (key, (None, Some(right))) => (key, StateShard(Map()).merge(right, staticContext))
      case (key, (Some(left), Some(right))) => (key, left.merge(right, staticContext))
      case (key, (Some(left), None)) => (key, left)
    }
    //println("newStateShards: \n\t" + newStateShards.collect().mkString(", "))
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

//    println(state.toString)

    state.toDatabase(program.tables.map(_.name))
  }
}
