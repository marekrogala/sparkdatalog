package s2g.ast.rule

import org.apache.spark.rdd.RDD
import s2g.ast.subgoal.{GoalPredicate, Subgoal, SubgoalsTopologicalSort}
import s2g.eval._
import s2g.spark.{Valuation, Database, StaticEvaluationContext}

case class RuleBody(subgoals: Seq[Subgoal]) {
  val (sortedSubgoals, outVariables) = SubgoalsTopologicalSort(subgoals)

  def evaluateDynamicSubgoals(
      constantValuations: Set[Valuation],
      context: StaticEvaluationContext,
      fullDatabase: Database,
      deltaDatabase: Database,
      subgoals: Seq[(Subgoal, Set[String])]): Option[RDD[Valuation]] = {
    println("\tevaluating dynamic subgoals.  cV=" + constantValuations.toString())
    val firstSubgoal::restSubgoals = subgoals
    val initialValuations = firstSubgoal._1.select(constantValuations, firstSubgoal._2, fullDatabase)
    //println("\t\tinitialValuations = " + initialValuations.get.collect().mkString(", "))
    restSubgoals.foldLeft(initialValuations)((valuationsOption, subgoal) =>
      valuationsOption.flatMap(valuations => subgoal._1.join(valuations, subgoal._2, fullDatabase)))
  }

  def findSolutionsSpark(context: StaticEvaluationContext, fullDatabase: Database, deltaDatabase: Database): Option[RDD[Valuation]] = {
    require(hasRelationalSubgoal, "TODO!! - reguly bez relsubgoals na samym poczatku policzyc") // TODO
    println("RULE BODY ["+subgoals.toString()+"].findSolutionsSpark(fullDatabase="+fullDatabase.toString+")")
    // TODO: maybe extract to analyze phase
    val firstRelationalSubgoal = subgoals.indexWhere(isRelational)
    val boundVariables = subgoals.scanLeft(Set[String]())((acc, subgoal) => acc ++ subgoal.getOutVariables).dropRight(1)
    val (constantSubgoals, dynamicSubgoals) = subgoals.zip(boundVariables).splitAt(firstRelationalSubgoal)

    val constantValuations = constantSubgoals.foldLeft(Set(Valuation()))((valuations, subgoal) => valuations.flatMap(subgoal._1.evaluateStatic))

    val result = evaluateDynamicSubgoals(constantValuations, context, fullDatabase, deltaDatabase, dynamicSubgoals)

    result
  }

  def hasRelationalSubgoal: Boolean = subgoals.exists(isRelational)

  def isRelational: (Subgoal) => Boolean = {
    case GoalPredicate(_) => true
    case _ => false
  }

  def findSolutions(state: EvaluationState): Set[PartialSolution] = {
    val sortedSubgoals = SubgoalsTopologicalSort(subgoals, state.environment)

    val subgoalProcessor = new SubgoalProcessor(state.environment)
    val evaluation = new SemiNaiveRuleEvaluation(subgoalProcessor, state)
    evaluation.evaluate(sortedSubgoals)
  }

}
