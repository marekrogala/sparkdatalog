package s2g.ast

import s2g.eval.{PartialSolution, EvaluationState}

case class RuleBody(subgoals: Seq[Subgoal]) {
  def findSolutions(state: EvaluationState): Set[PartialSolution] = {
    // TODO: Rewrite this in functional way.
    var solutions: Set[PartialSolution] = Set(PartialSolution())
    for(subgoal <- subgoals) {
      solutions = solutions map (subgoal.solveOn(_, state)) flatten
    }
    solutions
  }

}
