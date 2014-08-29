package pl.appsilon.marek.sparkdatalog.ast.value

import pl.appsilon.marek.sparkdatalog.Valuation

trait Value {
  def evaluate(valuation: Valuation): Int
  def getFreeVariables: Set[String]

}
