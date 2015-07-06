package pl.appsilon.marek.sparkdatalog

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

case class RelationRepr(name: String, aggregation: Option[Aggregation], data: DataFrame) {

  def toRelation: Relation = Relation(name, data)

  def coalesce(numPartitions: Int): RelationRepr = copy(data = data.coalesce(numPartitions))

  def isEmpty: Boolean = data.count() == 0

  def combined: RelationRepr = {
    val combinedData = aggregation.map { aggregation =>
      // TODO: agg columns
      val cols = List("a", "b")
      data.groupBy("x", cols:_*).agg(max("age"))  // TODO: agg fun depending on the program
    } getOrElse {
      data.dropDuplicates()
    }
    copy(data = combinedData)
  }

  def union(relation: RelationRepr): RelationRepr = {
    copy(data = data.unionAll(relation.data))
  }

  def unionDelta(relation: RelationRepr): (RelationRepr, RelationRepr) = {
    // TODO: can we avoid "except" ?
    val delta = data.unionAll(relation.data)
    val full = relation.data.except(data)
    (copy(data = full), copy(data = delta))
  }

  def persist(): RelationRepr = {
    copy(data = data.persist())
  }

//  def partitionByAndPersist(partitioner: Partitioner): RelationRepr = {
//    copy(data = data.partitionBy(partitioner).persist())
//  }

  override def toString = name + ": " + data.collect().map("(" + _.toString() + ")").mkString(", ")
}

object RelationRepr {
  def apply(relation: Relation, aggregationOption: Option[Aggregation]) =
    new RelationRepr(
      relation.name,
      aggregationOption,
      relation.data)
}
