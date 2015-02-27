package pl.appsilon.marek.sparkdatalog.ast.exp

import pl.appsilon.marek.sparkdatalog.Valuation
import pl.appsilon.marek.sparkdatalog.ast.value.{AnalyzedValue, Value}

case class AnalyzedEValue(value: AnalyzedValue) extends AnalyzedExp {
  override def evaluate(valuation: Valuation): Int = value.evaluate(valuation)
}
