package s2g.ast

import s2g.eval.PartialSolution

case class ValueLiteral(typ: Type, value: AnyVal) extends Value {
  override def evaluate(context: PartialSolution): ValueLiteral = this

  override def toString: String = value.toString

  override def tryToEvaluate(solution: PartialSolution): Value = this
}
