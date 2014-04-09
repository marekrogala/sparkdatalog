package s2g.eval

import s2g.ast.subgoal.Subgoal

class NaiveRuleEvaluation(subgoalProcessor: SubgoalProcessor, state: EvaluationState) {
  def evaluate(sortedSubgoals: Seq[Subgoal]): Set[PartialSolution] =
    sortedSubgoals.foldLeft(Set(PartialSolution()))(subgoalProcessor.processSubgoal(_, _, state.accumulatedTables))
}
