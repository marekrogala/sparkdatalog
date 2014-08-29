package s2g.spark

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

case class Relation(name: String, data: RDD[Seq[Int]]) {
  def -(other: Relation) = {
    require(name == other.name, "Relation cannot be subtracted from a relation with a different name")
    copy(data = data.subtract(other.data))
  }

  def empty: Boolean = data.count() == 0

  def combine: Relation = copy(data = data.distinct()) // TODO: Here will be aggregation.

  def +(relation: Relation): Relation = Relation(name, data.union(relation.data)).combine
  override def toString = data.collect().map(name + "(" + _.toString() + ")").mkString("; ")
}

object Relation {
  def fromUnary(name: String, data: RDD[Int]) = Relation(name, data.map(item => Seq(item)))
  def fromTuple2(name: String, data: RDD[(Int, Int)]) = Relation(name, data.map(item => Seq(item._1, item._2)))
  def fromTuple3(name: String, data: RDD[(Int, Int, Int)]) = Relation(name, data.map(item => Seq(item._1, item._2, item._3)))
}
