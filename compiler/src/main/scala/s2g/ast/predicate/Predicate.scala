package s2g.ast.predicate

import s2g.eval.{EvaluationState, PartialSolution}

trait Predicate {
  def fetchMatchingInstances(partialSolution: PartialSolution, evaluationState: EvaluationState): Set[PartialSolution]
}
