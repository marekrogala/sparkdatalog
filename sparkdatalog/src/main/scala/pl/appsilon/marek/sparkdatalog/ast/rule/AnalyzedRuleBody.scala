package pl.appsilon.marek.sparkdatalog.ast.rule

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.SemanticException
import pl.appsilon.marek.sparkdatalog.ast.subgoal.{AnalyzedSubgoal, GoalPredicate, Subgoal, SubgoalsTopologicalSort}
import pl.appsilon.marek.sparkdatalog.eval.{StateShard, StaticEvaluationContext, RelationInstance}

case class AnalyzedRuleBody(subgoals: Seq[AnalyzedSubgoal], initialValuation: Valuation) {

  /** Semantic analysis */

  def isRelational: (Subgoal) => Boolean = {
    case GoalPredicate(_) => true
    case _ => false
  }

  /** Evaluation */

  def processSubgoal(valuations: Seq[Valuation], subgoal: AnalyzedSubgoal, relations: Map[String, RelationInstance]): Seq[Valuation] =
    subgoal.solveOnSet(valuations, relations)


  def findSolutions(context: StaticEvaluationContext, shard: StateShard): Seq[Valuation] = {
    val result = subgoals.foldLeft(Seq(initialValuation))(processSubgoal(_, _, shard.relations))
    //println("findSolutions " + this + "; " + shard + " --> " + result)
    result
  }

}
