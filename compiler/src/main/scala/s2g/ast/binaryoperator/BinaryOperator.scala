package s2g.ast.binaryoperator

import s2g.ast.value.ValueLiteral

trait BinaryOperator {
   def apply(left: ValueLiteral, right: ValueLiteral): ValueLiteral
 }
