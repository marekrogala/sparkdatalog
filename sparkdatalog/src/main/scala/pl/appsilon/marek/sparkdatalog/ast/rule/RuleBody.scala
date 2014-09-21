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
  val hasRelationalSubgoal = sortedSubgoals.exists(isRelational)
  if(!hasRelationalSubgoal) throw new SemanticException("Rule must contain at least one relational subgoal.")
  val firstRelationalSubgoal = sortedSubgoals.indexWhere(isRelational)
  val boundVariables = sortedSubgoals.scanLeft(Set[String]())((acc, subgoal) => acc ++ subgoal.getOutVariables).dropRight(1)
  val (constantSubgoals, dynamicSubgoals) = sortedSubgoals.zip(boundVariables).splitAt(firstRelationalSubgoal)
  val constantValuations = constantSubgoals.foldLeft(Set(Valuation()))((valuations, subgoal) => valuations.flatMap(subgoal._1.evaluateStatic))

  def isRelational: (Subgoal) => Boolean = {
    case GoalPredicate(_) => true
    case _ => false
  }

  /** Evaluation */

  def processSubgoal(valuations: Seq[Valuation], subgoal: Subgoal, relations: Map[String, RelationInstance]): Seq[Valuation] =
    subgoal.solveOnSet(valuations, relations)


  def findSolutions(context: StaticEvaluationContext, shard: StateShard): Seq[Valuation] = {
    dynamicSubgoals.map(_._1).foldLeft(constantValuations.toSeq)(processSubgoal(_, _, shard.relations))
  }



  /** Spark Joins method */


  def findSolutionsSpark(context: StaticEvaluationContext, fullDatabase: Database, deltaDatabase: Database): Option[RDD[Valuation]] = {
    val (firstSubgoalEvaluator, restSubgoalsEvaluators) = prepareRuleEvaluators
    semiNaiveEvaluation(fullDatabase, deltaDatabase, firstSubgoalEvaluator, restSubgoalsEvaluators)
  }

  def printORV(s: String, option: Option[RDD[Valuation]]) = println("\t "+ s+" = " + option.map(_.collect().mkString(", ")).toString)

  def semiNaiveEvaluation(
      fullDatabase: Database,
      deltaDatabase: Database,
      firstSubgoalEvaluator: (Database) => Option[RDD[Valuation]],
      restSubgoalsEvaluators: List[(RDD[Valuation], Database) => Option[RDD[Valuation]]]): Option[RDD[Valuation]] = {
    val initialNoDelta = firstSubgoalEvaluator(fullDatabase)
    val initialWithDelta = firstSubgoalEvaluator(deltaDatabase)

    val (_, finalWithDeltaValuations) = restSubgoalsEvaluators.foldLeft((initialNoDelta, initialWithDelta))({
      case ((noDeltaValuationsOption, deltaValuationsOption), evaluator) =>
        val newNoDelta = noDeltaValuationsOption.flatMap { valuations => evaluator(valuations, fullDatabase) }
        val newlyWithDelta = noDeltaValuationsOption.flatMap { valuations => evaluator(valuations, deltaDatabase) }
        val oldlyWithDelta = deltaValuationsOption.flatMap { valuations => evaluator(valuations, fullDatabase) }
        val newWithDelta = (newlyWithDelta, oldlyWithDelta) match {
          case (Some(v1), Some(v2)) => Some(v1.union(v2))
          case (Some(v1), None) => Some(v1)
          case (None, Some(v2)) => Some(v2)
          case (None, None) => None
        }
        (newNoDelta, newWithDelta)
    })
    finalWithDeltaValuations
  }

  private def prepareRuleEvaluators = {
    val (firstSubgoal, initiallyBoundVariables) :: restSubgoals = dynamicSubgoals
    val firstSubgoalEvaluator = firstSubgoal.select(constantValuations, initiallyBoundVariables, _: Database)
    val restSubgoalsEvaluators = restSubgoals.map({ case (subgoal, boundVariablesForSubgoal) => subgoal.join(_: RDD[Valuation], boundVariablesForSubgoal, _: Database)})
    (firstSubgoalEvaluator, restSubgoalsEvaluators)
  }
}
