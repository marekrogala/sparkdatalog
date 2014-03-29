package s2g.ast.value

import s2g.eval.PartialSolution

trait Value {
  def tryToEvaluate(solution: PartialSolution): Value
  def evaluate(context: PartialSolution): ValueLiteral

}
