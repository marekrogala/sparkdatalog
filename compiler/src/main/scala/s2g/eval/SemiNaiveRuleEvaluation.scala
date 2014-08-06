package s2g.eval

import s2g.ast.subgoal.Subgoal

class SemiNaiveRuleEvaluation(subgoalProcessor: SubgoalProcessor, state: EvaluationState) {
  def evaluate(sortedSubgoals: Seq[Subgoal]): Set[PartialSolution] = {
    // TODO: Consider passing tables only to GoalPredicates



    val tablesForSubgoalsConfigurations: Seq[Seq[TableStates]] =
      sortedSubgoals.indices map { deltaPosition => sortedSubgoals.indices map {
        case `deltaPosition` => state.deltaTables
        case _ => state.accumulatedTables
      }
    }

    val generatedSolutions = for(tablesConfiguration <- tablesForSubgoalsConfigurations) yield {
      val subgoalsWithTables = sortedSubgoals.zip(tablesConfiguration)
      subgoalsWithTables.foldLeft(Set(PartialSolution())) {
        case (acc, (subgoal, tableStates)) => subgoalProcessor.processSubgoal(acc, subgoal, tableStates)
      }
    }

    generatedSolutions.flatten.toSet
  }
}
