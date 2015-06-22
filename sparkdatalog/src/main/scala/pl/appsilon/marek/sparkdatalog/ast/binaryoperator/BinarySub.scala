package pl.appsilon.marek.sparkdatalog.ast.binaryoperator

import pl.appsilon.marek.sparkdatalog.ast.value.ValueLiteral

case class BinarySub() extends BinaryOperator {
  def apply(left: ValueLiteral, right: ValueLiteral): ValueLiteral = left.typ.sub(left, right)
}
