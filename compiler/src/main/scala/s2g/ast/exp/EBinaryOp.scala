package s2g.ast.exp

import s2g.ast.binaryoperator.BinaryOperator
import s2g.spark.Valuation

case class EBinaryOp(left: Exp, right: Exp, op: BinaryOperator) extends Exp {
  override def getFreeVariables: Set[String] = left.getFreeVariables ++ right.getFreeVariables

  override def evaluate(valuation: Valuation): Int = op(left.evaluate(valuation), right.evaluate(valuation))
}
