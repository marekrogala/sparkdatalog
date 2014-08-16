package s2g.ast.declaration

import s2g.ast.value.ValueLiteral
import s2g.eval.SemanticException

case class Function(name: String) {
  val literalMax: (ValueLiteral, ValueLiteral) => ValueLiteral =
    (a: ValueLiteral, b: ValueLiteral) => if(a.typ.compare(a, b) < 0){
      a
    } else {
      b
    }

  val literalMin: (ValueLiteral, ValueLiteral) => ValueLiteral =
    (a: ValueLiteral, b: ValueLiteral) => if(a.typ.compare(a, b) < 0){
      a
    } else {
      b
    }

  def toScalaFunction: (ValueLiteral, ValueLiteral) => ValueLiteral = {
    name match {
      case "Max" => literalMax
      case "Min" => literalMin
      case _ => throw new SemanticException("Unknown aggregate function '%s', available functions are 'Min' and 'Max'".format(name))
    }
  }

}
