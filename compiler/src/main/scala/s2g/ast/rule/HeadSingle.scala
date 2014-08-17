package s2g.ast.rule

import s2g.eval.{Fact, EvaluationState, Instance, PartialSolution}

case class HeadSingle(name: String, args: Seq[String]) extends Head {

  def bindArguments(solution: PartialSolution): Instance = Instance(args map (solution(_)))

  override def emitSolutions(solutions: Set[PartialSolution]): Set[Fact] =
    solutions.map(bindArguments).map(Fact(name, _)).toSet
}
