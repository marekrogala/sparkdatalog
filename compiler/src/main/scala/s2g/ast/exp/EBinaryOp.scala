package s2g.ast.exp

import s2g.ast.binaryoperator.BinaryOperator
import s2g.eval.PartialSolution
import s2g.ast.value.ValueLiteral

case class EBinaryOp(left: Exp, right: Exp, op: BinaryOperator) extends Exp {
  override def evaluate(context: PartialSolution): ValueLiteral = op(left.evaluate(context), right.evaluate(context))
}
