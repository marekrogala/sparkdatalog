package s2g.ast.exp

import s2g.ast.value.Value
import s2g.spark.Valuation

case class EValue(value: Value) extends Exp {
  override def getFreeVariables: Set[String] = value.getFreeVariables

  override def evaluate(valuation: Valuation): Int = value.evaluate(valuation)
}
