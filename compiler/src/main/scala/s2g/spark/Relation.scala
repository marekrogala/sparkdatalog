package s2g.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext._

case class Relation(name: String, data: RDD[Fact]) {
  def subtract(other: Relation) = {
    require(name == other.name, "Relation cannot be subtracted from a relation with a different name")
    copy(data = data.subtract(other.data))
  }

  def empty: Boolean = data.count() == 0

  def combine(aggregation: Option[Aggregation]): Relation = {
    val combinedData = aggregation.map { aggregation =>
      data.map(aggregation.partition).reduceByKey(aggregation.operator).map(aggregation.merge)
    } getOrElse data.distinct()
    copy(data = combinedData)
  }

  def union(relation: Relation, aggregation: Option[Aggregation]): Relation =
    copy(data = data.union(relation.data)).combine(aggregation)

  def + (relation: Relation): Relation = copy(data = data.union(relation.data))

  override def toString = data.collect().map(name + "(" + _.toString() + ")").mkString("; ")
}

object Relation {
  def fromUnary(name: String, data: RDD[Int]) = Relation(name, data.map(item => Seq(item)))
  def fromTuple2(name: String, data: RDD[(Int, Int)]) = Relation(name, data.map(item => Seq(item._1, item._2)))
  def fromTuple3(name: String, data: RDD[(Int, Int, Int)]) = Relation(name, data.map(item => Seq(item._1, item._2, item._3)))
}
