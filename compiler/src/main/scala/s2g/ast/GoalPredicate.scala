package s2g.ast

import s2g.eval.{EvaluationState, PartialSolution}

case class GoalPredicate(predicate: Predicate) extends Subgoal {
  override def solveOn(partialSolution: PartialSolution, evaluationState: EvaluationState): Set[PartialSolution] = {
    predicate.fetchMatchingInstances(partialSolution, evaluationState)
  }
}
