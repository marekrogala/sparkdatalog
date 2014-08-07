package s2g.ast.rule

import s2g.eval.EvaluationState

case class Rule(head: Head, rules: Seq[RuleBody]) {
  def apply(state: EvaluationState, outputState: EvaluationState) = rules.foldLeft(outputState){ case (acc, rule) =>
    val solutions = rule.findSolutions(state)
    val result = head.emitSolutions(acc, solutions)
    result
  }

}
