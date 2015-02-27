package pl.appsilon.marek.sparkdatalog.ast.rule

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{FactW, RelationRepr, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.SemanticException
import pl.appsilon.marek.sparkdatalog.eval.StaticEvaluationContext
import pl.appsilon.marek.sparkdatalog.eval.nonsharded.NonshardedState

case class Rule(head: Head, body: RuleBody) {

  /** Semantic analysis */
  val notBoundHeadVariables = head.args.toSet -- body.outVariables
  if (notBoundHeadVariables.nonEmpty)
    throw new SemanticException("Unbound variable(s) in rule head: " +
      notBoundHeadVariables.mkString(", ") +
      " (head has free variables: " + head.args.mkString(", ") +
      "; but positive variables in body are: " + body.outVariables.mkString(", ") + ")")
  val variableIds: Map[String, Int] = body.outVariables.toSeq.zipWithIndex.toMap
  val refferedRelations = body.relationalSubgoals.map(_.predicate.tableName)

  val isRecursive = refferedRelations.contains(head.name)

  var analyzedBodies: Seq[AnalyzedRuleBody] = _
  def analyze(state: NonshardedState) = {
    analyzedBodies = body.analyze(variableIds, state)
  }
  def isRecursiveInStratum = analyzedBodies.exists(_.isRecursive)

  def evaluateOnSpark(context: StaticEvaluationContext, state: NonshardedState): RelationRepr = {
    val solutions: RDD[Valuation] = analyzedBodies.map(_.findSolutionsSpark(context, state)).reduce(_ ++ _)
    head.emitSolutionsSpark(solutions, context, variableIds)
  }

  override def toString: String = {
    head.toString + " :- " + body.toString
  }

}
