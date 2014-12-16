package pl.appsilon.marek.sparkdatalog.ast.subgoal

import pl.appsilon.marek.sparkdatalog.ast.predicate.Predicate
import pl.appsilon.marek.sparkdatalog.ast.value.Value
import pl.appsilon.marek.sparkdatalog.eval.nonsharded.NonshardedState

case class GoalPredicate(predicate: Predicate) extends Subgoal {
  val location: Value = predicate.args.head

  override def getInVariables: Set[String] = Set()
  override def getOutVariables: Set[String] = predicate.getVariables

  override def analyze(variableIds: Map[String, Int], boundVariables: Set[Int], state: NonshardedState): AnalyzedSubgoal = {
    val analyzedGoal = AnalyzedGoalPredicate(predicate.analyze(variableIds), variableIds, boundVariables)
    analyzedGoal.prepareForIteration(state)
    analyzedGoal
  }

  override def isRelational: Boolean = true

  override def toString = predicate.toString

  override val evaluationPriority: Int = 3
}
