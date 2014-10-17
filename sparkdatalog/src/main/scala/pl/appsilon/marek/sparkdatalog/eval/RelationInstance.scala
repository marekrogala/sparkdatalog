package pl.appsilon.marek.sparkdatalog.eval

import pl.appsilon.marek.sparkdatalog
import pl.appsilon.marek.sparkdatalog.util.{NTimed, Timed}
import pl.appsilon.marek.sparkdatalog.{Aggregation, Fact}

case class RelationInstance(name: String, facts: Seq[Fact]) {
  def isEmpty: Boolean = facts.isEmpty

  def subtract(other: RelationInstance): RelationInstance = {
    require(name == other.name)
    copy(facts = facts.diff(other.facts))
  }

  def combine(aggregation: Option[Aggregation]): RelationInstance = NTimed("combine %d facts".format(facts.size), () => {
    val combined = aggregation.map { aggregation =>
      val operator = aggregation.operator
      val partitioned = NTimed("partitioned", () => facts.map(aggregation.partition))
      val grouped = NTimed("grouped", () => partitioned.groupBy(_._1))
      val reduced = NTimed("reduced", () => grouped.map({case (key, values) => key -> values.map(_._2).reduce(operator)}))
      val merged = NTimed("merged", () => reduced.map(aggregation.merge).toSeq) // TODO: moze iterable?
      merged
    } getOrElse facts.distinct
    copy(facts = combined)
  })

  def merge(other: RelationInstance, aggregation: Option[Aggregation]): RelationInstance = {
    require(name == other.name, "Tried to merge different relations %s and %s".format(name, other.name))
    val result = RelationInstance(name, facts ++ other.facts).combine(aggregation)
    //println("Merge " + other.toString + " into " + this.toString + " with " + aggregation.toString + " ---> " + result.toString)
    result
  }

  def merge(instance: RelationInstance, context: StaticEvaluationContext): RelationInstance =
    merge(instance, context.aggregations.get(name))

  def toKeyValue: Seq[(Long, RelationInstance)] =
    facts.groupBy(sparkdatalog.keyForFact).map({
      case (key, value) => (key, RelationInstance(name, value))
    }).toSeq

  override def toString = "RelationInstance " + name + " : " + facts.map(fact => fact.toString + fact.mkString("(", ", ", ")")).mkString(" ")
}

object RelationInstance {
  def createCombined(instances: Iterable[RelationInstance], context: StaticEvaluationContext): RelationInstance = {
    val name = instances.head.name
    RelationInstance(name, instances.flatMap(_.facts)(collection.breakOut)).combine(context.aggregations.get(name))
  }
}
