package pl.appsilon.marek.sparkdatalog.ast.exp

import pl.appsilon.marek.sparkdatalog.Valuation
import pl.appsilon.marek.sparkdatalog.ast.value.Value

case class EValue(value: Value) extends Exp {
  override def getFreeVariables: Set[String] = value.getFreeVariables

  override def analyze(variableIds: Map[String, Int]): AnalyzedExp = AnalyzedEValue(value.analyze(variableIds))
}
