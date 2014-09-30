package pl.appsilon.marek.sparkdatalog.ast.value

import pl.appsilon.marek.sparkdatalog.Valuation
import pl.appsilon.marek.sparkdatalog.ast.types.Type

case class AnalyzedValueLiteral(typ: Type, value: AnyVal) extends AnalyzedValue {
  override def toString: String = value.toString

  override def evaluate(valuation: Valuation): Int = value.asInstanceOf[Int]
}
