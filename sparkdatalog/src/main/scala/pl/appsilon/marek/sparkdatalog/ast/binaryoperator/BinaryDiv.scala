package pl.appsilon.marek.sparkdatalog.ast.binaryoperator

import pl.appsilon.marek.sparkdatalog.ast.value.ValueLiteral

case class BinaryDiv() extends BinaryOperator {
  def apply(left: ValueLiteral, right: ValueLiteral): ValueLiteral = left.typ.div(left, right)
}
