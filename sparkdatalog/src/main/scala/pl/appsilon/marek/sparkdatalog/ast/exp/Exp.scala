package pl.appsilon.marek.sparkdatalog.ast.exp

import pl.appsilon.marek.sparkdatalog.Valuation

trait Exp {
  def getFreeVariables: Set[String]
  def analyze(variableIds: Map[String, Int]): AnalyzedExp
}
