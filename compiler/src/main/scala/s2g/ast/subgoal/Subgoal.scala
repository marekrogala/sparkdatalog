package s2g.ast.subgoal

import s2g.eval.{Context, EvaluationState, PartialSolution}

trait Subgoal {
  def solveOn(context: Context, evaluationState: EvaluationState): Set[PartialSolution]
  def getInputs(context: Context): Set[String]
  def getOutputs(context: Context): Set[String]
}
