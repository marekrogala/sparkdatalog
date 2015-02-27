package pl.appsilon.marek.sparkdatalog.ast.binaryoperator

import pl.appsilon.marek.sparkdatalog.ast
import pl.appsilon.marek.sparkdatalog.ast.value.ValueLiteral

trait BinaryOperator {
  def apply(left: ValueLiteral, right: ValueLiteral): ValueLiteral
  def apply(left: Int, right: Int): Int =
    apply(ValueLiteral(new ast.types.TypeInt(), left), ValueLiteral(new ast.types.TypeInt(), right))
      .value.asInstanceOf[Int]
 }
