package s2g.ast.exp

import s2g.eval.PartialSolution
import s2g.ast.value.ValueLiteral

trait Exp {
  def evaluate(context: PartialSolution): ValueLiteral
}
