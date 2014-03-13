package s2g.ast

case class TypeInt() extends Type {
  def cast(literal: ValueLiteral): Int = literal match {
    case ValueLiteral(TypeInt(), value: Int) => value
  }

  override def add(left: ValueLiteral, right: ValueLiteral): ValueLiteral = ValueLiteral(TypeInt(), cast(left) + cast(right))
}
