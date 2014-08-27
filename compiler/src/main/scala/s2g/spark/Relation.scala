package s2g.spark

import org.apache.spark.rdd.RDD

case class Relation(name: String, data: RDD[Seq[Int]]) {
}

object Relation {
  def fromTuple2(name: String, data: RDD[(Int, Int)]) = Relation(name, data.map(item => Seq(item._1, item._2)))
}
