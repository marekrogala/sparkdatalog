package s2g.ast.subgoal

import s2g.eval.{Context, EvaluationState, PartialSolution}

trait Subgoal {
  def solveOn(context: Context, evaluationState: EvaluationState): Set[PartialSolution]
  def getInputs(context: Context): Seq[String]
  def getOutputs(context: Context): Seq[String]
}
