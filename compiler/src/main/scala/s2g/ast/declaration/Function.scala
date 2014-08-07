package s2g.ast.declaration

import s2g.ast.value.ValueLiteral

case class Function(name: String) {
  val literalMax: (ValueLiteral, ValueLiteral) => ValueLiteral =
    (a: ValueLiteral, b: ValueLiteral) => if(a.typ.compare(a, b) < 0){
      a
    } else {
      b
    }

  def toScalaFunction: (ValueLiteral, ValueLiteral) => ValueLiteral = literalMax
}
