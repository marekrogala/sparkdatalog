package s2g.ast.subgoal

import s2g.eval.{TableStates, Context, EvaluationState, PartialSolution}
import s2g.ast.exp.Exp

case class GoalAssign(toVariable: String, exp: Exp) extends Subgoal {
  override def solveOn(context: Context, tableStates: TableStates): Set[PartialSolution] = {
    val value = exp.evaluate(context)
    context.partialSolution.get(toVariable) match {
      case None => Set(context.partialSolution + (toVariable -> value))
      case Some(`value`) => Set(context.partialSolution)
      case other => Set()
    }
  }

  override def getOutputs(context: Context): Set[String] = Set(toVariable)

  override def getInputs(context: Context): Set[String] = exp.tryToEvaluate(context).getFreeVariables
}
