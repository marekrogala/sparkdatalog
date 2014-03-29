package s2g.ast.types

import s2g.ast.value.ValueLiteral

case class TypeInt() extends Type {
  def cast(literal: ValueLiteral): Int = literal match {
    case ValueLiteral(TypeInt(), value: Int) => value
  }

  override def add(left: ValueLiteral, right: ValueLiteral): ValueLiteral = ValueLiteral(TypeInt(), cast(left) + cast(right))

  override def compare(left: ValueLiteral, right: ValueLiteral): Int = cast(left) - cast(right)
}
