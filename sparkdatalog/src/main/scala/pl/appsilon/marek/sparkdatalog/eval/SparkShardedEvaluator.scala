package pl.appsilon.marek.sparkdatalog.eval

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.Database
import pl.appsilon.marek.sparkdatalog.ast.Program
import pl.appsilon.marek.sparkdatalog.ast.rule.Rule
import pl.appsilon.marek.sparkdatalog.spark.OuterJoinableRDD._

object SparkShardedEvaluator {

  private def makeIteration(
      staticContext: StaticEvaluationContext,
      rules: Iterable[Rule],
      state: State): State = {
    //println("making interation, "+state.deltaEmpty+"delta=" + state.shards.collect().map(_._2.delta).mkString("; "))

    def generateMessages(key: Long, shard: StateShard): Seq[(Long, RelationInstance)] = {
      val intermediate = rules.map(_.evaluate(staticContext, shard))
      //println("generateMessages(%s, %s): \n  result: %s".format(key.toString, shard.toString, intermediate.toString))  // TODO: duplikaty?!
      val result = intermediate.reduce(_ ++ _)

      result
    }

    val messages: RDD[(Long, StateShard)] =
      state.shards.flatMap(Function.tupled(generateMessages)).groupByKey().mapValues({ messagesInstances =>
        StateShard.fromRelationInstances(messagesInstances, staticContext)
      })
    messages.cache()

    val newStateShards: RDD[(Long, StateShard)] = state.shards.fullOuterJoin(messages) map {
      case (key, (None, Some(right))) => (key, right)
      case (key, (Some(left), Some(right))) => (key, left.merge(right.relations.mapValues(Seq(_)), staticContext))
      case (key, (Some(left), None)) => (key, left)
    }
    newStateShards.count()
    messages.unpersist(blocking = false)
    //println("newStateShards: \n\t" + newStateShards.collect().mkString(", "))
    state.step(newStateShards)
  }

  def evaluate(database: Database, program: Program): Database = {
    var state = State.fromDatabase(database)
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
        if(iteration % checkpointFrequency == 0) state.shards.checkpoint()
        state.materialize()
        oldState.unpersist(blocking = false)

        iteration += 1
      } while (!state.deltaEmpty)
    }

//    println(state.toString)

    state.toDatabase(program.tables.map(_.name))
  }
}
