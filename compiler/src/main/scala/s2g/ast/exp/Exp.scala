package s2g.ast.exp

import s2g.eval.{Context, PartialSolution}
import s2g.ast.value.ValueLiteral
import s2g.spark.Valuation

trait Exp {
  def evaluate(valuation: Valuation): Int
  def evaluate(context: Context): ValueLiteral
  def tryToEvaluate(context: Context): Exp
  def getFreeVariables: Set[String]
}
