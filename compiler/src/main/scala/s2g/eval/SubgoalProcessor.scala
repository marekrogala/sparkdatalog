package s2g.eval

import s2g.ast.subgoal.Subgoal

class SubgoalProcessor(environment: PartialSolution) {

  def processSubgoal(solutions: Set[PartialSolution], subgoal: Subgoal, tableStates: TableStates)
      : Set[PartialSolution] = solutions.map {
    solution => subgoal.solveOn(Context(environment, solution), tableStates) }.flatten
}
