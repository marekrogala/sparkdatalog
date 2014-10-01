package pl.appsilon.marek.sparkdatalog.eval

import pl.appsilon.marek.sparkdatalog.Database
import pl.appsilon.marek.sparkdatalog.ast.Program
import pl.appsilon.marek.sparkdatalog.ast.rule.Rule
import pl.appsilon.marek.sparkdatalog.util.{NTimed, Timed}

object LocalEvaluator {

  private def makeIteration(
      staticContext: StaticEvaluationContext,
      rules: Iterable[Rule],
      state: Seq[(Long, StateShard)]): Seq[(Long, StateShard)] = {
    def generateMessages(key: Long, shard: StateShard): Seq[(Long, RelationInstance)] = {
      Timed("generateMessages " + key, () => rules.map(_.evaluate(staticContext, shard)).reduce(_ ++ _))
    }

    val rawMessages = Timed("generateAllMsgs", () => state.flatMap(Function.tupled(generateMessages)))
    val messages = NTimed("groupBy", () => rawMessages.groupBy(_._1).mapValues(_.map(_._2).groupBy(_.name)))
    val oldStateShards = NTimed("state.toMap", () => state.toMap)
    val newStateShards = Timed("newStateShards", () => {
      val keys = (state.map(_._1) ++ messages.keys).toSet.toSeq
      keys.map({
        case key =>
          (oldStateShards.get(key), messages.get(key)) match {
            case (Some(left), Some(right)) => (key, NTimed("merge", () => left.merge(right, staticContext)))
            case (Some(left), None) => (key, left)
            case (None, Some(right)) => (key, NTimed("fromRelationInstances", () => StateShard.fromRelationInstances(right, staticContext)))
            case _ => ???
          }
      })
    })
    //println("All messages = " + messages + "\n+old shards = " + state + " \n --> new shards = " + newStateShards)

    val result = NTimed("calculating delta", () => newStateShards.map({
      case (key, left) => key -> NTimed("delted " + key, () => left.delted(oldStateShards.get(key)))
    }))
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
      state = Timed("iteration ", () => makeIteration(context, program.rules, state))
      iteration += 1
   //   println("stan: " + state)
    } while (!state.forall(_._2.delta.forall(_._2.isEmpty)))

    println(state.toString)

    state
  }
}
