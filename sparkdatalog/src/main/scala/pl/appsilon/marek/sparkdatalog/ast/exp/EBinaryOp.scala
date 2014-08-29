package pl.appsilon.marek.sparkdatalog.ast.exp

import pl.appsilon.marek.sparkdatalog.Valuation
import pl.appsilon.marek.sparkdatalog.ast.binaryoperator.BinaryOperator

case class EBinaryOp(left: Exp, right: Exp, op: BinaryOperator) extends Exp {
  override def getFreeVariables: Set[String] = left.getFreeVariables ++ right.getFreeVariables

  override def evaluate(valuation: Valuation): Int = op(left.evaluate(valuation), right.evaluate(valuation))
}
