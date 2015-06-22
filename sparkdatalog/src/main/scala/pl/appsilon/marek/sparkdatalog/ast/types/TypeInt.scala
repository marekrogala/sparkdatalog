package pl.appsilon.marek.sparkdatalog.ast.types

import pl.appsilon.marek.sparkdatalog.ast.value.ValueLiteral

case class TypeInt() extends Type {
  def cast(literal: ValueLiteral): Int = literal match {
    case ValueLiteral(TypeInt(), value: Int) => value
  }

  override def add(left: ValueLiteral, right: ValueLiteral): ValueLiteral = ValueLiteral(TypeInt(), cast(left) + cast(right))
  override def mul(left: ValueLiteral, right: ValueLiteral): ValueLiteral = ValueLiteral(TypeInt(), cast(left) * cast(right))
  override def sub(left: ValueLiteral, right: ValueLiteral): ValueLiteral = ValueLiteral(TypeInt(), cast(left) - cast(right))
  override def div(left: ValueLiteral, right: ValueLiteral): ValueLiteral = ValueLiteral(TypeInt(), cast(left) / cast(right))

  override def compare(left: ValueLiteral, right: ValueLiteral): Int = cast(left) - cast(right)
}
