package s2g.ast.subgoal

import s2g.eval.{TableStates, Context, EvaluationState, PartialSolution}
import s2g.ast.predicate.Predicate

case class GoalPredicate(predicate: Predicate) extends Subgoal {
  override def solveOn(context: Context, tableStates: TableStates): Set[PartialSolution] = {
    predicate.fetchMatchingInstances(context.partialSolution, tableStates, context.environment)
  }

  override def getOutputs(context: Context): Set[String] = predicate.getOutputs(context)

  override def getInputs(context: Context): Set[String] = predicate.getInputs(context)
}
