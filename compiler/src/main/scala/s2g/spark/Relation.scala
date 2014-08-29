package s2g.spark

import org.apache.spark.rdd.RDD

case class Relation(name: String, data: RDD[Seq[Int]]) {
  def combine: Relation = copy(data = data.distinct()) // TODO: Here will be aggregation.

  def +(relation: Relation): Relation = Relation(name, data.union(relation.data)).combine
  override def toString = data.collect().mkString("; ")
}

object Relation {
  def fromTuple2(name: String, data: RDD[(Int, Int)]) = Relation(name, data.map(item => Seq(item._1, item._2)))
  def fromTuple3(name: String, data: RDD[(Int, Int, Int)]) = Relation(name, data.map(item => Seq(item._1, item._2, item._3)))
}
