package s2g.ast.value

import s2g.eval.Context

trait Value {
  def tryToEvaluate(context: Context): Value
  def evaluate(context: Context): ValueLiteral
  def getFreeVariables: Seq[String]

}
