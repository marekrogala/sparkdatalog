package pl.appsilon.marek.sparkdatalog.ast.types

import pl.appsilon.marek.sparkdatalog.ast.value.ValueLiteral

case class TypeDouble() extends Type {
  override def add(left: ValueLiteral, right: ValueLiteral): ValueLiteral = ???

  override def compare(literal: ValueLiteral, literal1: ValueLiteral): Int = ???
}
