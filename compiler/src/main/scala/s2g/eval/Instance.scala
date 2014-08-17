package s2g.eval

import s2g.ast.value.ValueLiteral

case class Instance(values: Seq[ValueLiteral]) {
  override def toString: String = "(" + values.map(_.toString).mkString(", ") + ")"
}
