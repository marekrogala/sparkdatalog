package s2g.ast.value

import s2g.spark.Valuation

trait Value {
  def evaluate(valuation: Valuation): Int
  def getFreeVariables: Set[String]

}
