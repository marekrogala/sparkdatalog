package pl.appsilon.marek.sparkdatalog.ast.rule

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{Relation, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.SemanticException
import pl.appsilon.marek.sparkdatalog.ast.subgoal.GoalPredicate
import pl.appsilon.marek.sparkdatalog.eval.nonsharded.NonshardedState
import pl.appsilon.marek.sparkdatalog.eval.{RelationInstance, StateShard, StaticEvaluationContext}

case class Rule(head: Head, body: RuleBody) {

  /** Semantic analysis */
  val notBoundHeadVariables = head.args.toSet -- body.outVariables
  if (notBoundHeadVariables.nonEmpty)
    throw new SemanticException("Unbound variable(s) in rule head: " +
      notBoundHeadVariables.mkString(", ") +
      " (head has free variables: " + head.args.mkString(", ") +
      "; but positive variables in body are: " + body.outVariables.mkString(", ") + ")")
  val variableIds: Map[String, Int] = body.outVariables.toSeq.zipWithIndex.toMap
  val analyzedBodies = body.analyze(variableIds)
  val refferedRelations = body.relationalSubgoals.map(_.predicate.tableName)

  val isRecursive = refferedRelations.contains(head.name)

  def evaluate(context: StaticEvaluationContext, shard: StateShard): Seq[(Long, RelationInstance)] = {
    val solutions: Seq[Valuation] = analyzedBodies.flatMap(_.findSolutions(context, shard))

    val generatedRelations = head.emitSolutions(solutions, variableIds)
    //println("evaluate shard = " + " \n\n\t -> " + generatedRelations)
    generatedRelations.toKeyValue
  }

  def evaluateOnSpark(context: StaticEvaluationContext, state: NonshardedState): Relation = {
    val solutions: RDD[Valuation] = analyzedBodies.map(_.findSolutionsSpark(context, state)).reduce(_ ++ _)
    val generatedRelation = head.emitSolutionsSpark(solutions, variableIds)
    generatedRelation
  }

  override def toString: String = {
    head.toString + " :- " + body.toString
  }

}
