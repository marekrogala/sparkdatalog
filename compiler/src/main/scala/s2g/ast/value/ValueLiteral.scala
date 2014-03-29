package s2g.ast.value

import s2g.eval.PartialSolution
import s2g.ast.types.Type

case class ValueLiteral(typ: Type, value: AnyVal) extends Value {
  override def evaluate(context: PartialSolution): ValueLiteral = this

  override def toString: String = value.toString

  override def tryToEvaluate(solution: PartialSolution): Value = this
}
