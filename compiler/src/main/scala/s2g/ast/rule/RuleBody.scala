package s2g.ast.rule

import s2g.ast.subgoal.{GoalComparison, GoalAssign, Subgoal}
import s2g.eval.{PartialSolution, EvaluationState}

case class RuleBody(subgoals: Seq[Subgoal]) {
  def findSolutions(state: EvaluationState): Set[PartialSolution] = {
    val (generative, arithmetic) = subgoals.partition({
      case GoalComparison(_, _ ,_) => false
      case _ => true
    })

    val generatedSolutions = generative.foldLeft(Set(PartialSolution()))(processSubgoal(_, _, state))

    // TODO: Solve remaining expressions in solutions.

    val filteredSolutions = arithmetic.foldLeft(generatedSolutions)(processSubgoal(_, _, state))

    filteredSolutions
  }

  private def processSubgoal(solutions: Set[PartialSolution], subgoal: Subgoal, state: EvaluationState)
      : Set[PartialSolution] = solutions.map(subgoal.solveOn(_, state)).flatten
}
