package pl.appsilon.marek

import scala.collection.mutable

package object sparkdatalog {
  type Valuation = mutable.WrappedArray[Int]
  val valuationNone = Int.MinValue
  type Fact = Seq[Int]
  type FactW = (Seq[Int], Int)

  val numPartitions = 8

}
