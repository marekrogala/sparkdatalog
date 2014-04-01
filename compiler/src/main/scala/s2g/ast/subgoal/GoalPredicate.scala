package s2g.ast.subgoal

import s2g.eval.{Context, EvaluationState, PartialSolution}
import s2g.ast.predicate.Predicate

case class GoalPredicate(predicate: Predicate) extends Subgoal {
  override def solveOn(context: Context, evaluationState: EvaluationState): Set[PartialSolution] = {
    predicate.fetchMatchingInstances(context.partialSolution, evaluationState)
  }
}
