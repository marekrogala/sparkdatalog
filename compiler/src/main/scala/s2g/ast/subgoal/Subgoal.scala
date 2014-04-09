package s2g.ast.subgoal

import s2g.eval.{TableStates, Context, PartialSolution}

trait Subgoal {
  def solveOn(context: Context, tableStates: TableStates): Set[PartialSolution]
  def getInputs(context: Context): Set[String]
  def getOutputs(context: Context): Set[String]
}
