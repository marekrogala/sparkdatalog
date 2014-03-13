package s2g.ast

import s2g.eval.PartialSolution

case class EValue(value: Value) extends Exp{
  override def evaluate(context: PartialSolution): ValueLiteral = value.evaluate(context)
}
