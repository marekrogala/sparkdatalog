package s2g.ast.predicate

import s2g.eval.{Context, EvaluationState, Pattern, PartialSolution}
import s2g.ast.value.Value

case class PredicateSingle(tableName: String, args: Seq[Value]) extends Predicate {

  def buildPattern(context: Context): Pattern = Pattern(args map (_.tryToEvaluate(context)))

  override def fetchMatchingInstances(partialSolution: PartialSolution, evaluationState: EvaluationState): Set[PartialSolution] =
    evaluationState.findMatchingInstances(tableName, buildPattern(Context(evaluationState.environment, partialSolution))) map (_ ++ partialSolution)
}
