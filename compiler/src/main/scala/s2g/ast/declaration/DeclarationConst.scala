package s2g.ast.declaration

import s2g.ast.exp.Exp
import s2g.ast.types.Type

case class DeclarationConst(typ: Type, name: String, exp: Exp) extends Declaration {
  override def validate(): Unit = {}
}
