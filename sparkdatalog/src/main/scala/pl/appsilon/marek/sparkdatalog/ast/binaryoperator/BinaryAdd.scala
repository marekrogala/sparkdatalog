package pl.appsilon.marek.sparkdatalog.ast.binaryoperator

import pl.appsilon.marek.sparkdatalog.ast.value.ValueLiteral

case class BinaryAdd() extends BinaryOperator {
  def apply(left: ValueLiteral, right: ValueLiteral): ValueLiteral = left.typ.add(left, right)
}
