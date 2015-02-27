package pl.appsilon.marek.sparkdatalog.ast.exp

import pl.appsilon.marek.sparkdatalog.Valuation
import pl.appsilon.marek.sparkdatalog.ast.binaryoperator.BinaryOperator

case class AnalyzedEBinaryOp(left: AnalyzedExp, right: AnalyzedExp, op: BinaryOperator) extends AnalyzedExp {
  override def evaluate(valuation: Valuation): Int = op(left.evaluate(valuation), right.evaluate(valuation))
}
