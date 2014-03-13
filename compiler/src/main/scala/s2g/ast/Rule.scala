package s2g.ast

import s2g.eval.EvaluationState

case class Rule(head: Head, rules: Seq[RuleBody]) {
  def apply(state: EvaluationState): Unit = rules foreach { rule =>
    val solutions = rule.findSolutions(state)
    head.emitSolutions(state, solutions)
  }

}
