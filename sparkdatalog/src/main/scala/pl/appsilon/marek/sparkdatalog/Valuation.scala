package pl.appsilon.marek.sparkdatalog

object Valuation {
  def apply(length: Int): Valuation = Array.fill(length)(valuationNone)
}
