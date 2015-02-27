package pl.appsilon.marek.sparkdatalog

import org.apache.spark.Partitioner
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

case class RelationRepr(name: String, aggregation: Option[Aggregation], data: RDD[(Fact, Int)]) {
  def mergedData: RDD[Fact] = aggregation.map(aggr => data.map(aggr.merge)).getOrElse(data.map(_._1))

  def toRelation: Relation = Relation(name, mergedData)

  def coalesce(numPartitions: Int): RelationRepr = copy(data = data.coalesce(numPartitions))

  def subtract(other: RelationRepr) = {
    require(name == other.name, "RelationRepr cannot be subtracted from a relation with a different name")
    copy(data = data.subtract(other.data))
  }

  def isEmpty: Boolean = data.count() == 0

  def combined: RelationRepr = {
    println("Combining", data.partitioner, data.partitions.size)
    val combinedData = aggregation.map { aggregation =>
      data.reduceByKey(aggregation.operator)
    } getOrElse {
      data.distinct()
    }
    println("after Combining", combinedData.partitioner, combinedData.partitions.size)
    copy(data = combinedData)
  }

  def union(relation: RelationRepr): RelationRepr = {
    println("Union", data.partitioner, data.partitions.size, relation.data.partitioner)
//    val combinedData: RDD[(Fact, Int)] = data.union(relation.data)
    val combinedData: RDD[(Fact, Int)] = data.cogroup(relation.data).flatMapValues {
      case (vs, ws) => vs.iterator ++ ws.iterator
    } //           ????????????????/
    println("after Union", combinedData.partitioner, combinedData.partitions.size)
    copy(data =  combinedData)
  }

  def partitionByAndPersist(partitioner: Partitioner): RelationRepr = {
    copy(data = data.partitionBy(partitioner).persist())
  }

  override def toString = name + ": " + data.collect().map("(" + _.toString() + ")").mkString(", ")
}

object RelationRepr {
  def apply(relation: Relation, aggregationOption: Option[Aggregation]) =
    new RelationRepr(
      relation.name,
      aggregationOption,
      aggregationOption.map(aggregation => relation.data.map(aggregation.partition))
        .getOrElse(relation.data.map((_, 0))))
}
