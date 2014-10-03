package pl.appsilon.marek

import scala.collection.mutable

package object sparkdatalog {
  type Valuation = mutable.ArraySeq[Option[Int]]
  type Fact = Seq[Int]

  def keyForFact: (sparkdatalog.Fact) => Long = _(0)%32
}
