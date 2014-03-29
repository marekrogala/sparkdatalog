package s2g.ast.types

import s2g.ast.value.ValueLiteral

case class TypeDouble() extends Type {
  override def add(left: ValueLiteral, right: ValueLiteral): ValueLiteral = ???

  override def compare(literal: ValueLiteral, literal1: ValueLiteral): Int = ???
}
