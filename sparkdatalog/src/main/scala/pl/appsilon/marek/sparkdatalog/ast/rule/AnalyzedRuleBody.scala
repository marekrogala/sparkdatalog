package pl.appsilon.marek.sparkdatalog.ast.rule

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{Database, DatabaseRepr, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.subgoal._
import pl.appsilon.marek.sparkdatalog.eval.{RelationInstance, StaticEvaluationContext}
import pl.appsilon.marek.sparkdatalog.eval.nonsharded.NonshardedState

case class AnalyzedRuleBody(subgoals: Seq[(AnalyzedSubgoal, Boolean)], initialValuation: Valuation, isRecursive: Boolean) {

  val firstRelationalSubgoal = subgoals.map(_._1).indexWhere(isRelational)
  val (staticSubgoals, dynamicSubgoals) = subgoals.splitAt(firstRelationalSubgoal)
  val staticallyEvaluated = if(staticSubgoals.nonEmpty) {
    Some(staticSubgoals.map(_._1).foldLeft(Seq(initialValuation))(processSubgoal(_, _, Map())))
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
    println("findSolutionsSpark", state.toString)
    println("subgoals: ",
      (staticSubgoals.map(_.toString()).map(_ + " (static)") ++ Seq(head._1.toString + (if(isRecursive) { " (delta)" } else { " (full)" })) ++ dynamicSubgoals.map({case (s, i) => s.toString + (if (i) { " (prev)"} else { " (pfull)"} )})).mkString(", ")
    )

    val staticParallelized: Option[RDD[Valuation]] = staticallyEvaluated.map(state.sc.parallelize(_))
    val firstEvaluated = processFirstSubgoalSpark(staticParallelized, head._1, if(isRecursive) { state.delta } else { state.database })
    val result = tail.foldLeft(firstEvaluated)({ case (acc, (subgoal, isRotated)) =>
      processSubgoalSpark(acc, subgoal, state.previousDatabase)})
    result.getOrElse(state.sc.parallelize(Seq()))
  }
}
