package s2g.ast

import s2g.eval.{Pattern, EvaluationState, PartialSolution}

case class PredicateSingle(tableName: String, args: Seq[Value]) extends Predicate {

  def buildPattern(solution: PartialSolution): Pattern = Pattern(args map (_.tryToEvaluate(solution)))

  override def fetchMatchingInstances(partialSolution: PartialSolution, evaluationState: EvaluationState): Set[PartialSolution] =
    evaluationState.findMatchingInstances(tableName, buildPattern(partialSolution)) map (_ ++ partialSolution)
}
