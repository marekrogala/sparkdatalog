package pl.appsilon.marek.sparkdatalog.ast.subgoal

import pl.appsilon.marek.sparkdatalog.ast.comparisonoperator.ComparisonOperator
import pl.appsilon.marek.sparkdatalog.ast.exp.Exp

case class GoalComparison(left: Exp, right: Exp, operator: ComparisonOperator) extends Subgoal {
  override def getInVariables: Set[String] = left.getFreeVariables ++ right.getFreeVariables
  override def getOutVariables: Set[String] = Set()

  override def analyze(variableIds: Map[String, Int], boundVariables: Set[Int]): AnalyzedSubgoal =
    AnalyzedGoalComparison(left.analyze(variableIds), right.analyze(variableIds), operator)

  override def isRelational: Boolean = false

  override def toString = left.toString + operator.toString + right.toString

  override val evaluationPriority: Int = 1
}
