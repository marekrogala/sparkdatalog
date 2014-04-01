package s2g.ast.predicate

import s2g.eval.{Context, EvaluationState, PartialSolution}

trait Predicate {
  def fetchMatchingInstances(partialSolution: PartialSolution, evaluationState: EvaluationState): Set[PartialSolution]
  def getInputs(context: Context): Set[String]
  def getOutputs(context: Context): Set[String]
}
