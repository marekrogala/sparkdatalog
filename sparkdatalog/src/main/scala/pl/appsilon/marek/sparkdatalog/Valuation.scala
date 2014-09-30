package pl.appsilon.marek.sparkdatalog

import scala.collection.mutable

object Valuation {
  def apply(length: Int): Valuation = mutable.ArraySeq.fill(length)(None)
}
