package pl.appsilon.marek.sparkdatalog.ast.exp

import pl.appsilon.marek.sparkdatalog.Valuation

trait Exp {
  def evaluate(valuation: Valuation): Int
  def getFreeVariables: Set[String]
}
