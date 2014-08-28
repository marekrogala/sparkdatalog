package s2g.ast.exp

import s2g.ast.binaryoperator.BinaryOperator
import s2g.eval.{Context, PartialSolution}
import s2g.ast.value.ValueLiteral
import s2g.spark.Valuation

case class EBinaryOp(left: Exp, right: Exp, op: BinaryOperator) extends Exp {
  override def evaluate(context: Context): ValueLiteral = op(left.evaluate(context), right.evaluate(context))

  override def tryToEvaluate(context: Context): Exp = EBinaryOp(left.tryToEvaluate(context), right.tryToEvaluate(context), op)

  override def getFreeVariables: Set[String] = left.getFreeVariables ++ right.getFreeVariables

  override def evaluate(valuation: Valuation): Int = op(left.evaluate(valuation), right.evaluate(valuation))
}
