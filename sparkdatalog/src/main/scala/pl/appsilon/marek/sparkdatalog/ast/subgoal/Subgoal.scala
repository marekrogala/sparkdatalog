package pl.appsilon.marek.sparkdatalog.ast.subgoal

import pl.appsilon.marek.sparkdatalog.eval.nonsharded.NonshardedState

trait Subgoal {
  val evaluationPriority: Int

  def getInVariables: Set[String]
  def getOutVariables: Set[String]
  def isRelational: Boolean

  def analyze(variableIds: Map[String, Int], boundVariables: Set[Int], state: NonshardedState): AnalyzedSubgoal
}
