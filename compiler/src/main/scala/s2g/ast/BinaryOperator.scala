package s2g.ast

trait BinaryOperator {
  def apply(left: ValueLiteral, right: ValueLiteral): ValueLiteral
}
