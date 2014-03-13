package s2g.ast

case class BinaryAdd() extends BinaryOperator {
  def apply(left: ValueLiteral, right: ValueLiteral): ValueLiteral = left.typ.add(left, right)
}
