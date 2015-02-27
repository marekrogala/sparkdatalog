package pl.appsilon.marek.sparkdatalog.ast.value

import pl.appsilon.marek.sparkdatalog.Valuation
import pl.appsilon.marek.sparkdatalog.ast.types.Type

case class ValueLiteral(typ: Type, value: AnyVal) extends Value {
  override def toString: String = value.toString
  override def getFreeVariables: Set[String] = Set()
  override def analyze(variableIds: Map[String, Int]): AnalyzedValue = AnalyzedValueLiteral(typ, value)
}
