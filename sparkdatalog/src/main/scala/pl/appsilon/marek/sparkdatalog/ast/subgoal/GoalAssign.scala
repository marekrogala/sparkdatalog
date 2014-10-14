package pl.appsilon.marek.sparkdatalog.ast.subgoal

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.eval.RelationInstance
import pl.appsilon.marek.sparkdatalog.{Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.exp.Exp

case class GoalAssign(lValueVariable: String, exp: Exp) extends Subgoal {

  override def getInVariables: Set[String] = exp.getFreeVariables

  override def getOutVariables: Set[String] = Set(lValueVariable)

  override def analyze(variableIds: Map[String, Int], boundVariables: Set[Int]): AnalyzedSubgoal =
    AnalyzedGoalAssign(variableIds(lValueVariable), exp.analyze(variableIds))

  override def isRelational: Boolean = false

  override def toString = lValueVariable.toString + " = " + exp.toString
}
