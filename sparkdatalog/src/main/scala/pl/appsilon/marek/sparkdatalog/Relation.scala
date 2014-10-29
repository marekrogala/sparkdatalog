package pl.appsilon.marek.sparkdatalog

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

case class Relation(name: String, data: RDD[Fact]) {
  def subtract(other: Relation) = {
    require(name == other.name, "Relation cannot be subtracted from a relation with a different name")
    copy(data = data.subtract(other.data))
  }

  def isEmpty: Boolean = data.count() == 0

  def combine(aggregation: Option[Aggregation]): Relation = {
    val combinedData = aggregation.map { aggregation =>
      data.map(aggregation.partition).reduceByKey(aggregation.operator).map(aggregation.merge)
    } getOrElse data.distinct()
    copy(data = combinedData)
  }

  def union(relation: Relation, aggregation: Option[Aggregation]): Relation =
    copy(data = data.union(relation.data)).combine(aggregation)

  def + (relation: Relation): Relation = copy(data = data.union(relation.data))

  override def toString = name + ": " + data.collect().map("(" + _.toString() + ")").mkString(", ")
  
}

object Relation {
  def unary(name: String, data: RDD[Int]) = Relation(name, data.map(item => Array(item)))
  def binary(name: String, data: RDD[(Int, Int)]) = Relation(name, data.map(item => Array(item._1, item._2)))
  def ternary(name: String, data: RDD[(Int, Int, Int)]) = Relation(name, data.map(item => Array(item._1, item._2, item._3)))
  def ofArity4(name: String, data: RDD[(Int, Int, Int, Int)]) = Relation(name, data.map(item => Array(item._1, item._2, item._3, item._4)))
  def ofArity5(name: String, data: RDD[(Int, Int, Int, Int, Int)]) = Relation(name, data.map(item => Array(item._1, item._2, item._3, item._4, item._5)))
  // TODO: Implement for the rest of tuple arities.
}
