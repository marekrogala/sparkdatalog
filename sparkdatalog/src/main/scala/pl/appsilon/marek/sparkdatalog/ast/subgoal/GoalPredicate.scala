package pl.appsilon.marek.sparkdatalog.ast.subgoal

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.ast.value.{Value, ValueVar}
import pl.appsilon.marek.sparkdatalog.eval.RelationInstance
import pl.appsilon.marek.sparkdatalog.{Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.predicate.Predicate

case class GoalPredicate(predicate: Predicate) extends Subgoal {
  val location: Value = predicate.args.head

  override def getInVariables: Set[String] = Set()
  override def getOutVariables: Set[String] = predicate.getVariables

  override def analyze(variableIds: Map[String, Int], boundVariables: Set[Int]): AnalyzedSubgoal =
    AnalyzedGoalPredicate(predicate.analyze(variableIds), variableIds, boundVariables)

  override def isRelational: Boolean = true

  override def toString = predicate.toString

  override val evaluationPriority: Int = 3
}
