package s2g.ast.exp

import s2g.ast.value.Value
import s2g.eval.{Context, PartialSolution}
import s2g.ast.value.{ValueLiteral, Value}
import s2g.spark.Valuation

case class EValue(value: Value) extends Exp{
  override def evaluate(context: Context): ValueLiteral = value.evaluate(context)

  override def tryToEvaluate(context: Context): Exp = EValue(value.tryToEvaluate(context))

  override def getFreeVariables: Set[String] = value.getFreeVariables

  override def evaluate(valuation: Valuation): Int = value.evaluate(valuation)
}
