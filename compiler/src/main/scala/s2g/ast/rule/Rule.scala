package s2g.ast.rule

import s2g.eval.{Fact, EvaluationState}

case class Rule(head: Head, rules: Set[RuleBody]) {
  def apply(state: EvaluationState): Set[Fact] =
    rules.map { rule =>
      val solutions = rule.findSolutions(state)
      val result = head.emitSolutions(solutions)
      result
    }.flatten

  def hasRelationalSubgoal: Boolean = rules.exists(ruleBody => ruleBody.hasRelationalSubgoal)

}
