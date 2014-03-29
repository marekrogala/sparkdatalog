package s2g.ast.subgoal

import s2g.eval.{EvaluationState, PartialSolution}
import s2g.ast.predicate.Predicate

case class GoalPredicate(predicate: Predicate) extends Subgoal {
  override def solveOn(partialSolution: PartialSolution, evaluationState: EvaluationState): Set[PartialSolution] = {
    predicate.fetchMatchingInstances(partialSolution, evaluationState)
  }
}
