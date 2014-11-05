package pl.appsilon.marek.sparkdatalog.ast.rule

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog
import pl.appsilon.marek.sparkdatalog.{Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.SemanticException
import pl.appsilon.marek.sparkdatalog.ast.subgoal.{NonrelationalSubgoalsTopologicalSort, GoalPredicate, Subgoal, SubgoalsTopologicalSort}
import pl.appsilon.marek.sparkdatalog.eval.{RelationInstance, StateShard, State, StaticEvaluationContext}

import scala.collection.immutable.IndexedSeq

case class RuleBody(subgoals: Seq[Subgoal]) {

  /** Semantic analysis */

  val hasRelationalSubgoal = subgoals.exists(_.isRelational)
  if(!hasRelationalSubgoal) throw new SemanticException("Rule must contain at least one relational subgoal.")

  val (_, outVariables) = SubgoalsTopologicalSort(subgoals)
  val (rawRelationalSubgoals: Seq[Subgoal], nonRelationalSubgoals: Seq[Subgoal]) = subgoals.partition(_.isRelational)
  val relationalSubgoals = rawRelationalSubgoals.map(_.asInstanceOf[GoalPredicate])

  private def fillWithNonrelational(relationalSubgoalsRotated: Seq[Subgoal]): Seq[Subgoal] =
    NonrelationalSubgoalsTopologicalSort(relationalSubgoalsRotated, nonRelationalSubgoals)


  def analyze(variableIds: Map[String, Int], idb: Set[String]): Seq[AnalyzedRuleBody] = {
    val isRecursiveWithinStratum = idb.intersect(relationalSubgoals.map(_.predicate.tableName).toSet).nonEmpty
    if(isRecursiveWithinStratum) {

      val recursiveSubgoalsRotations: IndexedSeq[Seq[Subgoal]] = relationalSubgoals.indices
        .filter(pos => idb.contains(relationalSubgoals(pos).predicate.tableName))
        .map(pos => {
          val (nose, tail) = relationalSubgoals.splitAt(pos)
          tail ++ nose
        })
      val rotationsWithNonrelationals: IndexedSeq[Seq[Subgoal]] = recursiveSubgoalsRotations.map(fillWithNonrelational)

      for (sortedSubgoals <- rotationsWithNonrelationals) yield {
        val boundVariables = sortedSubgoals.scanLeft(Set[Int]())((acc, subgoal) => acc ++ subgoal.getOutVariables.map(variableIds)).dropRight(1)
        val analyzedSubgoals = sortedSubgoals.zip(boundVariables).map(subgoalWithBinding =>
          subgoalWithBinding._1.analyze(variableIds, subgoalWithBinding._2))
        AnalyzedRuleBody(analyzedSubgoals, Valuation(variableIds.size), isRecursive = true)
      }
    } else {
        val sortedSubgoals = fillWithNonrelational(rawRelationalSubgoals)
        val boundVariables = sortedSubgoals.scanLeft(Set[Int]())((acc, subgoal) => acc ++ subgoal.getOutVariables.map(variableIds)).dropRight(1)
        val analyzedSubgoals = sortedSubgoals.zip(boundVariables).map(subgoalWithBinding =>
          subgoalWithBinding._1.analyze(variableIds, subgoalWithBinding._2))
        Seq(AnalyzedRuleBody(analyzedSubgoals, Valuation(variableIds.size), isRecursive = false))
    }
  }

  override def toString = subgoals.mkString(", ")
}
