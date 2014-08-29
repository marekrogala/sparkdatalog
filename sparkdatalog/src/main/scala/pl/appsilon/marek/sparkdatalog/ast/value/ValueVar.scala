package pl.appsilon.marek.sparkdatalog.ast.value

import pl.appsilon.marek.sparkdatalog.Valuation

case class ValueVar(name: String) extends Value {
  override def toString: String = name

  override def getFreeVariables: Set[String] = Set(name)

  override def evaluate(valuation: Valuation): Int = valuation(name)
}
