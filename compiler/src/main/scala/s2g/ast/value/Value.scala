package s2g.ast.value

import s2g.eval.Context
import s2g.spark.Valuation

trait Value {
  def evaluate(valuation: Valuation): Int

  def tryToEvaluate(context: Context): Value
  def evaluate(context: Context): ValueLiteral
  def getFreeVariables: Set[String]

}
