package s2g.ast.value

import s2g.spark.Valuation

case class ValueVar(name: String) extends Value {
  override def toString: String = name

  override def getFreeVariables: Set[String] = Set(name)

  override def evaluate(valuation: Valuation): Int = valuation(name)
}
