package s2g.ast.rule

import s2g.eval.{EvaluationState, Instance, PartialSolution}

case class HeadSingle(name: String, args: Seq[String]) extends Head {

  def bindArguments(solution: PartialSolution): Instance = Instance(args map (solution(_)))

  override def emitSolutions(state: EvaluationState, solutions: Set[PartialSolution]): Unit = {
    for (solution <- solutions) {
      state.putInstance(name, bindArguments(solution))
    }
  }
}
