package s2g.ast.exp

import s2g.ast.value.Value
import s2g.eval.PartialSolution
import s2g.ast.value.{ValueLiteral, Value}

case class EValue(value: Value) extends Exp{
  override def evaluate(context: PartialSolution): ValueLiteral = value.evaluate(context)
}
