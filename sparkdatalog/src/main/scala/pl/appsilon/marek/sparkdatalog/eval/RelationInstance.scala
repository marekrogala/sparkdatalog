package pl.appsilon.marek.sparkdatalog.eval

import pl.appsilon.marek.sparkdatalog
import pl.appsilon.marek.sparkdatalog.{Aggregation, Fact}

case class RelationInstance(name: String, facts: Seq[Fact]) {
  def isEmpty: Boolean = facts.isEmpty

  def subtract(other: RelationInstance): RelationInstance = {
    require(name == other.name)
    copy(facts = facts.diff(other.facts))
  }

  def combine(aggregation: Option[Aggregation]): RelationInstance = {
    val combined = aggregation.map { aggregation =>
      val operator = aggregation.operator
      val partitioned = facts.map(aggregation.partition)
      val grouped = partitioned.groupBy(_._1)
      val reduced = grouped.map({case (key, values) => key -> values.map(_._2).reduce(operator)})
      val merged = reduced.map(aggregation.merge).toSeq
      merged
    } getOrElse facts.distinct
    copy(facts = combined)
  }

  def merge(other: RelationInstance, aggregation: Option[Aggregation]): RelationInstance = {
    require(name == other.name, "Tried to merge different relations %s and %s".format(name, other.name))
    val result = RelationInstance(name, facts ++ other.facts).combine(aggregation)
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
