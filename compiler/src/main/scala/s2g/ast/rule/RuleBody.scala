package s2g.ast.rule

import s2g.ast.subgoal.{GoalPredicate, Subgoal, SubgoalsTopologicalSort}
import s2g.eval._

case class RuleBody(subgoals: Seq[Subgoal]) {
  def hasRelationalSubgoal: Boolean = subgoals.exists({
    case GoalPredicate(_) => true
    case _ => false
  })

  def findSolutions(state: EvaluationState): Set[PartialSolution] = {
    val sortedSubgoals = SubgoalsTopologicalSort(subgoals, state.environment)

    val subgoalProcessor = new SubgoalProcessor(state.environment)
    val evaluation = new SemiNaiveRuleEvaluation(subgoalProcessor, state)
    evaluation.evaluate(sortedSubgoals)
  }

}
