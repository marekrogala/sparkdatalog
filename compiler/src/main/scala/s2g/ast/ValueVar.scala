package s2g.ast

import s2g.eval.PartialSolution

case class ValueVar(name: String) extends Value {
  override def evaluate(context: PartialSolution): ValueLiteral = context(name)

  override def toString: String = name

  override def tryToEvaluate(solution: PartialSolution): Value = solution.get(name) match {
    case Some(valueLiteral) => valueLiteral
    case None => this
  }
}
