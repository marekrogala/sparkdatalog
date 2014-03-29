package s2g.ast.rule

import s2g.eval.{PartialSolution, EvaluationState}

trait Head {
  def emitSolutions(state: EvaluationState, solutions: Set[PartialSolution])
}
