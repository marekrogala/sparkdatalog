package pl.appsilon.marek.sparkdatalog.ast.rule

import scala.collection.immutable.IndexedSeq

import pl.appsilon.marek.sparkdatalog.Valuation
import pl.appsilon.marek.sparkdatalog.ast.SemanticException
import pl.appsilon.marek.sparkdatalog.ast.subgoal.{GoalPredicate, NonrelationalSubgoalsTopologicalSort, Subgoal, SubgoalsTopologicalSort}
import pl.appsilon.marek.sparkdatalog.eval.nonsharded.NonshardedState

case class RuleBody(subgoals: Seq[Subgoal]) {

  /** Semantic analysis */

  val hasRelationalSubgoal = subgoals.exists(_.isRelational)
  if(!hasRelationalSubgoal) throw new SemanticException("Rule must contain at least one relational subgoal.")

  val (_, outVariables) = SubgoalsTopologicalSort(subgoals)
  val (rawRelationalSubgoals: Seq[Subgoal], nonRelationalSubgoals: Seq[Subgoal]) = subgoals.partition(_.isRelational)
  val relationalSubgoals = rawRelationalSubgoals.map(_.asInstanceOf[GoalPredicate])

  private def fillWithNonrelational(relationalSubgoalsRotated: Seq[(Subgoal, Boolean)]): Seq[(Subgoal, Boolean)] =
    NonrelationalSubgoalsTopologicalSort(relationalSubgoalsRotated, nonRelationalSubgoals.map((_, false)))


  def analyze(variableIds: Map[String, Int], state: NonshardedState): Seq[AnalyzedRuleBody] = {
    val isRecursiveWithinStratum = state.idb.intersect(relationalSubgoals.map(_.predicate.tableName).toSet).nonEmpty
    if(isRecursiveWithinStratum) {

      val recursiveSubgoalsRotations: IndexedSeq[Seq[(Subgoal, Boolean)]] = relationalSubgoals.indices
        .filter(pos => state.idb.contains(relationalSubgoals(pos).predicate.tableName))
        .map(pos => {
          val (nose, tail) = relationalSubgoals.splitAt(pos)
          tail.map((_, true)) ++ nose.map((_, false))
        })
      val rotationsWithNonrelationals: IndexedSeq[Seq[(Subgoal, Boolean)]] = recursiveSubgoalsRotations.map(fillWithNonrelational)

      for (sortedSubgoals <- rotationsWithNonrelationals) yield {
        val boundVariables = sortedSubgoals.scanLeft(Set[Int]())((acc, subgoal) => acc ++ subgoal._1.getOutVariables.map(variableIds)).dropRight(1)
        val analyzedSubgoals = sortedSubgoals.zip(boundVariables).map(subgoalWithBinding =>
          (subgoalWithBinding._1._1.analyze(variableIds, subgoalWithBinding._2, state), subgoalWithBinding._1._2))
        AnalyzedRuleBody(analyzedSubgoals, Valuation(variableIds.size), isRecursive = true)
      }
    } else {
        val sortedSubgoals = fillWithNonrelational(rawRelationalSubgoals.map((_, false)))
        val boundVariables = sortedSubgoals.scanLeft(Set[Int]())((acc, subgoal) => acc ++ subgoal._1.getOutVariables.map(variableIds)).dropRight(1)
        val analyzedSubgoals = sortedSubgoals.zip(boundVariables).map(subgoalWithBinding =>
          (subgoalWithBinding._1._1.analyze(variableIds, subgoalWithBinding._2, state), subgoalWithBinding._1._2))
        Seq(AnalyzedRuleBody(analyzedSubgoals, Valuation(variableIds.size), isRecursive = false))
    }
  }

  override def toString = subgoals.mkString(", ")
}
