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
      facts.map(aggregation.partition).groupBy(_._1)
        .map({case (key, values) => key -> values.map(_._2).reduce(aggregation.operator)})
        .map(aggregation.merge).toSeq // TODO: moze iterable?
    } getOrElse facts
    copy(facts = combined)
  }

  def merge(other: RelationInstance, aggregation: Option[Aggregation]) = {
    require(name == other.name)
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
