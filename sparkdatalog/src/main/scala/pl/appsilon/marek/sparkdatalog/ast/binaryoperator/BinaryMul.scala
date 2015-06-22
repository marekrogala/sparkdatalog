package pl.appsilon.marek.sparkdatalog.ast.binaryoperator

import pl.appsilon.marek.sparkdatalog.ast.value.ValueLiteral

case class BinaryMul() extends BinaryOperator {
  def apply(left: ValueLiteral, right: ValueLiteral): ValueLiteral = left.typ.mul(left, right)
}
