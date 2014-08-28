package s2g.ast.rule

import s2g.eval.{SemanticException, Fact, EvaluationState}
import s2g.spark.{Relation, Database, StaticEvaluationContext}

case class Rule(head: Head, rules: Set[RuleBody]) {

  def evaluateOnSpark(context: StaticEvaluationContext, fullDatabase: Database, deltaDatabase: Database): Relation = {
    rules.foreach(body => {
      val notBoundHeadVariables = head.args.toSet -- body.outVariables
      if (notBoundHeadVariables.nonEmpty)
        throw new SemanticException("Unbound variable(s) in rule head: " +
          notBoundHeadVariables.mkString(", ") +
          " (head has free variables: " + head.args.mkString(", ") +
          "; but positive variables in body are: " + body.outVariables.mkString(", ") + ")")
    })

    val generatedRelations = rules.map { rule =>
      val solutions = rule.findSolutionsSpark(context, fullDatabase, deltaDatabase)
      val result = head.emitSolutionsSpark(solutions)
      result
    }

    generatedRelations.reduce(_ + _)
  }

  def apply(state: EvaluationState): Set[Fact] =
    rules.map { rule =>
      val solutions = rule.findSolutions(state)
      val result = head.emitSolutions(solutions)
      result
    }.flatten

  def hasRelationalSubgoal: Boolean = rules.exists(ruleBody => ruleBody.hasRelationalSubgoal)

}
