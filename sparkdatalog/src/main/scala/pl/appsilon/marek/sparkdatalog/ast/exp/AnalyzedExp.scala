package pl.appsilon.marek.sparkdatalog.ast.exp

import pl.appsilon.marek.sparkdatalog.Valuation

trait AnalyzedExp {
  def evaluate(valuation: Valuation): Int
}
