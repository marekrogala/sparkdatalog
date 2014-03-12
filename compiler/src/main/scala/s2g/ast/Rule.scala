package s2g.ast

import s2g.eval.EvaluationState

case class Rule(head: Head, rules: Seq[RuleBody]) {
  def apply(state: EvaluationState) = {}

}
