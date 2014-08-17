package s2g.ast.rule

import s2g.eval.{Fact, PartialSolution, EvaluationState}

trait Head {
  def emitSolutions(solutions: Set[PartialSolution]): Set[Fact]
}
