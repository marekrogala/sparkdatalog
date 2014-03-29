package s2g.ast.types

import s2g.ast.value.ValueLiteral

trait Type {
  def compare(literal: ValueLiteral, literal1: ValueLiteral): Int

  def add(left: ValueLiteral, right: ValueLiteral): ValueLiteral
}
