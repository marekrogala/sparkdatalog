package pl.appsilon.marek

import pl.appsilon.marek.sparkdatalog.spark.ArrayHashPartitioner

import scala.collection.mutable

package object sparkdatalog {
  type Valuation = mutable.WrappedArray[Int]
  val valuationNone = Int.MinValue
  type Fact = Seq[Int]

  val numPartitions = 8

  def keyForFact: (sparkdatalog.Fact) => Long = _(0)%2
}
