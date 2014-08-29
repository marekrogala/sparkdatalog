package s2g.ast.rule

import s2g.eval.{SemanticException, Fact, EvaluationState}
import s2g.spark.{Relation, Database, StaticEvaluationContext}

case class Rule(head: Head, rules: Set[RuleBody]) {

  /** Semantic analysis */
  rules.foreach(body => {
    val notBoundHeadVariables = head.args.toSet -- body.outVariables
    if (notBoundHeadVariables.nonEmpty)
      throw new SemanticException("Unbound variable(s) in rule head: " +
        notBoundHeadVariables.mkString(", ") +
        " (head has free variables: " + head.args.mkString(", ") +
        "; but positive variables in body are: " + body.outVariables.mkString(", ") + ")")
  })

  /** Evaluation */
  def evaluateOnSpark(context: StaticEvaluationContext, fullDatabase: Database, deltaDatabase: Database): Option[Relation] = {
    val generatedRelations = rules.flatMap(_.findSolutionsSpark(context, fullDatabase, deltaDatabase).map(head.emitSolutionsSpark))
    generatedRelations.reduceOption(_ + _)
  }

  def apply(state: EvaluationState): Set[Fact] =
    rules.map { rule =>
      val solutions = rule.findSolutions(state)
      val result = head.emitSolutions(solutions)
      result
    }.flatten

}
