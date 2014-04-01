package s2g.ast.value

import s2g.eval.{Context, PartialSolution}
import s2g.ast.types.Type

case class ValueLiteral(typ: Type, value: AnyVal) extends Value {
  override def evaluate(context: Context): ValueLiteral = this

  override def toString: String = value.toString

  override def tryToEvaluate(context: Context): Value = this

  override def getFreeVariables: Seq[String] = Seq()
}
