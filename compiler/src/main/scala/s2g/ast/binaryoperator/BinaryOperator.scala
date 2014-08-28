package s2g.ast.binaryoperator

import s2g.ast
import s2g.ast.value.ValueLiteral

trait BinaryOperator {
  def apply(left: ValueLiteral, right: ValueLiteral): ValueLiteral
  def apply(left: Int, right: Int): Int = // TODO cleanup
    apply(ValueLiteral(new ast.types.TypeInt(), left), ValueLiteral(new ast.types.TypeInt(), right))
      .value.asInstanceOf[Int]
 }
