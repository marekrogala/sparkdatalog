package s2g.ast.rule

import s2g.eval.EvaluationState

case class Rule(head: Head, rules: Seq[RuleBody]) {
  def apply(state: EvaluationState) = rules.foldLeft(state){ case (acc, rule) =>
    val solutions = rule.findSolutions(acc)
    head.emitSolutions(acc, solutions)
  }

}
