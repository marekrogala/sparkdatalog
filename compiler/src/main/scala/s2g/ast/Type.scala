package s2g.ast

trait Type {
  def add(left: ValueLiteral, right: ValueLiteral): ValueLiteral
}
