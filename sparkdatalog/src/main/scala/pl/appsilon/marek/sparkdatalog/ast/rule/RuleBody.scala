package pl.appsilon.marek.sparkdatalog.ast.rule

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog
import pl.appsilon.marek.sparkdatalog.{Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.SemanticException
import pl.appsilon.marek.sparkdatalog.ast.subgoal.{GoalPredicate, Subgoal, SubgoalsTopologicalSort}
import pl.appsilon.marek.sparkdatalog.eval.{RelationInstance, StateShard, State, StaticEvaluationContext}

case class RuleBody(subgoals: Seq[Subgoal]) {

  /** Semantic analysis */
  val (sortedSubgoals, outVariables) = SubgoalsTopologicalSort(subgoals)
//  val hasRelationalSubgoal = sortedSubgoals.exists(isRelational)
//  if(!hasRelationalSubgoal) throw new SemanticException("Rule must contain at least one relational subgoal.")
//  val firstRelationalSubgoal = sortedSubgoals.indexWhere(isRelational)

//  def isRelational: (Subgoal) => Boolean = {
//    case GoalPredicate(_) => true
//    case _ => false
//  }

  def analyze(variableIds: Map[String, Int]) = {
    val boundVariables = sortedSubgoals.scanLeft(Set[Int]())((acc, subgoal) => acc ++ subgoal.getOutVariables.map(variableIds)).dropRight(1)
    val analyzedSubgoals = sortedSubgoals.zip(boundVariables).map(subgoalWithBinding =>
      subgoalWithBinding._1.analyze(variableIds, subgoalWithBinding._2))
    AnalyzedRuleBody(analyzedSubgoals, Valuation(variableIds.size))
  }
}
