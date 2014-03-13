package s2g.ast

import s2g.eval.PartialSolution

case class EBinaryOp(left: Exp, right: Exp, op: BinaryOperator) extends Exp {
  override def evaluate(context: PartialSolution): ValueLiteral = op(left.evaluate(context), right.evaluate(context))
}
