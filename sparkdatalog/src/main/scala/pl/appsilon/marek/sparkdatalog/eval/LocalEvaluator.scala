package pl.appsilon.marek.sparkdatalog.eval

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog
import pl.appsilon.marek.sparkdatalog.{Fact, Database}
import pl.appsilon.marek.sparkdatalog.ast.Program
import pl.appsilon.marek.sparkdatalog.ast.rule.Rule

object LocalEvaluator {

  private def makeIteration(
      staticContext: StaticEvaluationContext,
      rules: Iterable[Rule],
      state: Seq[(Long, StateShard)]): Seq[(Long, StateShard)] = {
    def generateMessages(key: Long, shard: StateShard): Seq[(Long, RelationInstance)] = {
      rules.map(_.evaluate(staticContext, shard)).flatten.reduce(_ ++ _)
    }

    val rawMessages = state.flatMap(Function.tupled(generateMessages))
    val messages = rawMessages.groupBy(_._1).map({
      case (key, values) => key -> values.map(_._2).reduce(_.merge(_, staticContext))
    })
    val newStateShards = state.map({
      case (key, left) =>
        messages.get(key) match {
          case Some(right) => (key, left.merge(right, staticContext))
          case None => (key, left)
        }
    })

    val oldStateShards = state.toMap
    val result = newStateShards.map({
      case (key, left) => key -> left.delted(oldStateShards.get(key))
    })
    result
  }

  def evaluate(database: Database, program: Program): Seq[(Long, StateShard)] = {
    val context = StaticEvaluationContext(program.aggregations)

    val relationsWithKeys = database.relations.toSeq.map({
      case (name, instance) => RelationInstance(name, instance.data.collect()).toKeyValue }).flatten
    var state: Seq[(Long, StateShard)] = relationsWithKeys.groupBy(_._1).map({
      case (key, relations) =>
        key -> relations.map(_._2).map(rel => StateShard(Map(rel.name -> rel))).reduce(_ ++ _ )
    }).toSeq

    var iteration = 0

    println("Initial state= " + state.toString)

    do {
      println("Making iteration " + iteration)
      state = makeIteration(context, program.rules, state)
      iteration += 1
   //   println("stan: " + state)
    } while (!state.forall(_._2.delta.forall(_._2.isEmpty)))

    println(state.toString)

    state
  }
}
