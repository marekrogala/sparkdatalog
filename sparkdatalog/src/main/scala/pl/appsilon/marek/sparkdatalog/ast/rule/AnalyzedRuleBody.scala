package pl.appsilon.marek.sparkdatalog.ast.rule

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{Database, DatabaseRepr, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.subgoal._
import pl.appsilon.marek.sparkdatalog.eval.{RelationInstance, StaticEvaluationContext}
import pl.appsilon.marek.sparkdatalog.eval.nonsharded.NonshardedState

case class AnalyzedRuleBody(subgoals: Seq[AnalyzedSubgoal], initialValuation: Valuation, isRecursive: Boolean) {

  /** Semantic analysis */
  val firstRelationalSubgoal = subgoals.indexWhere(isRelational)
  val (staticSubgoals, dynamicSubgoals) = subgoals.splitAt(firstRelationalSubgoal)
  val staticallyEvaluated = if(staticSubgoals.nonEmpty) {
    Some(staticSubgoals.foldLeft(Seq(initialValuation))(processSubgoal(_, _, Map())))
  } else {
    None
  }

  def isRelational: (AnalyzedSubgoal) => Boolean = {
    case AnalyzedGoalPredicate(_, _, _) => true
    case _ => false
  }

  /** Evaluation */

  def processSubgoal(valuations: Seq[Valuation], subgoal: AnalyzedSubgoal, relations: Map[String, RelationInstance]): Seq[Valuation] =
    subgoal.solveOnSet(valuations, relations)

  def processFirstSubgoalSpark(valuations: Option[RDD[Valuation]], subgoal: AnalyzedSubgoal, database: DatabaseRepr): Option[RDD[Valuation]] =
    valuations match {
      case Some(v) => subgoal.solveRDD(v, database)
      case None => subgoal.selectRDD(database)
    }

  def processSubgoalSpark(valuations: Option[RDD[Valuation]], subgoal: AnalyzedSubgoal, database: DatabaseRepr): Option[RDD[Valuation]] =
    valuations.flatMap(subgoal.solveRDD(_, database))
  
  def findSolutionsSpark(context: StaticEvaluationContext, state: NonshardedState): RDD[Valuation] = {
    val head +: tail = dynamicSubgoals
    val staticParallelized: Option[RDD[Valuation]] = staticallyEvaluated.map(state.sc.parallelize(_))
    val firstEvaluated = processFirstSubgoalSpark(staticParallelized, head, if(isRecursive) { state.delta } else { state.database })
    val result = tail.foldLeft(firstEvaluated)(processSubgoalSpark(_, _, state.database))
    result.getOrElse(state.sc.parallelize(Seq()))
  }
}
