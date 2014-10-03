package pl.appsilon.marek.sparkdatalog.ast.rule

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.SemanticException
import pl.appsilon.marek.sparkdatalog.ast.subgoal._
import pl.appsilon.marek.sparkdatalog.eval.{StateShard, StaticEvaluationContext, RelationInstance}

case class AnalyzedRuleBody(subgoals: Seq[AnalyzedSubgoal], initialValuation: Valuation) {

  /** Semantic analysis */
  val firstRelationalSubgoal = subgoals.indexWhere(isRelational)
  val (staticSubgoals, dynamicSubgoals) = subgoals.splitAt(firstRelationalSubgoal)
  val staticallyEvaluated = staticSubgoals.foldLeft(Seq(initialValuation))(processSubgoal(_, _, Map()))

  def isRelational: (AnalyzedSubgoal) => Boolean = {
    case AnalyzedGoalPredicate(_, _, _) => true
    case _ => false
  }

  /** Evaluation */

  def processSubgoal(valuations: Seq[Valuation], subgoal: AnalyzedSubgoal, relations: Map[String, RelationInstance]): Seq[Valuation] =
    subgoal.solveOnSet(valuations, relations)


  def findSolutions(context: StaticEvaluationContext, shard: StateShard): Seq[Valuation] = {
    val head +: tail = dynamicSubgoals
    val firstEvaluated = processSubgoal(staticallyEvaluated, head, shard.delta)
    val result = tail.foldLeft(firstEvaluated)(processSubgoal(_, _, shard.relations))
    val name = head.asInstanceOf[AnalyzedGoalPredicate].predicate.tableName
//    println("find solutions, first head " + name + " delta size = " + shard.delta.get(name).map(_.facts.size).getOrElse(0)
//      + " full size = " + shard.relations.get(name).map(_.facts.size).getOrElse(0))
    //println("findSolutions " + this + "\n\t; " + shard + "\n --> " + result)
    result
  }

}
