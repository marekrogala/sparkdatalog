package pl.appsilon.marek.sparkdatalog.ast.value

import pl.appsilon.marek.sparkdatalog.Valuation

case class AnalyzedValueVar(varId: Int) extends AnalyzedValue {
  override def toString: String = varId.toString

  override def evaluate(valuation: Valuation): Int = valuation(varId).get
}
