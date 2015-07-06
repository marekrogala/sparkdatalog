package pl.appsilon.marek.sparkdatalog

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.DataFrame

case class Relation(name: String, data: DataFrame) {
  def coalesce(numPartitions: Int): Relation = copy(data = data.coalesce(numPartitions))

  def isEmpty: Boolean = data.count() == 0

  override def toString = name + ": " + data.collect().map("(" + _.toString() + ")").mkString(", ")
  
}

