package pl.appsilon.marek.sparkdatalog.ast.types

import pl.appsilon.marek.sparkdatalog.ast.value.ValueLiteral

trait Type {
  def compare(literal: ValueLiteral, literal1: ValueLiteral): Int

  def add(left: ValueLiteral, right: ValueLiteral): ValueLiteral
  def mul(left: ValueLiteral, right: ValueLiteral): ValueLiteral
  def sub(left: ValueLiteral, right: ValueLiteral): ValueLiteral
  def div(left: ValueLiteral, right: ValueLiteral): ValueLiteral
}
