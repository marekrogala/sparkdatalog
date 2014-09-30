package pl.appsilon.marek.sparkdatalog.ast.value

import pl.appsilon.marek.sparkdatalog.Valuation

trait AnalyzedValue {
  def evaluate(valuation: Valuation): Int
}
