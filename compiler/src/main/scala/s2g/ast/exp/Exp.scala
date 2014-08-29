package s2g.ast.exp

import s2g.spark.Valuation

trait Exp {
  def evaluate(valuation: Valuation): Int
  def getFreeVariables: Set[String]
}
