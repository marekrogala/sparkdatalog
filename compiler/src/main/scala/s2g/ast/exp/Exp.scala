package s2g.ast.exp

import s2g.eval.{Context, PartialSolution}
import s2g.ast.value.ValueLiteral

trait Exp {

  def evaluate(context: Context): ValueLiteral
  def tryToEvaluate(context: Context): Exp
  def getFreeVariables: Seq[String]
}
