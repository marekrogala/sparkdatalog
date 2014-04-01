package s2g.ast.rule

import s2g.ast.subgoal.{SubgoalsTopologicalSort, GoalComparison, GoalAssign, Subgoal}
import s2g.eval.{Context, PartialSolution, EvaluationState}

case class RuleBody(subgoals: Seq[Subgoal]) {
  def findSolutions(state: EvaluationState): Set[PartialSolution] = {
    val sortedSubgoals = SubgoalsTopologicalSort(subgoals, state.environment)

    sortedSubgoals.foldLeft(Set(PartialSolution()))(processSubgoal(_, _, state))

    // TODO: Solve remaining expressions in solutions. ??

  }

  private def processSubgoal(solutions: Set[PartialSolution], subgoal: Subgoal, state: EvaluationState)
      : Set[PartialSolution] = solutions.map {
    solution => subgoal.solveOn(Context(state.environment, solution), state) }.flatten
}
