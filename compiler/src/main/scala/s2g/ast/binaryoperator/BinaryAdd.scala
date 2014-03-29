package s2g.ast.binaryoperator

import s2g.ast.value.ValueLiteral

case class BinaryAdd() extends BinaryOperator {
  def apply(left: ValueLiteral, right: ValueLiteral): ValueLiteral = left.typ.add(left, right)
}
