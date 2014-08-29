package pl.appsilon.marek.sparkdatalog.ast.rule

import pl.appsilon.marek.sparkdatalog.{Database, Relation}
import pl.appsilon.marek.sparkdatalog.ast.SemanticException
import pl.appsilon.marek.sparkdatalog.eval.StaticEvaluationContext

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

}
