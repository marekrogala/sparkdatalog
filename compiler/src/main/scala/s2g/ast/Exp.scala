package s2g.ast

import s2g.eval.PartialSolution

trait Exp {
  def evaluate(context: PartialSolution): ValueLiteral
}
