package pl.appsilon.marek.sparkdatalog.ast.declaration

import pl.appsilon.marek.sparkdatalog.ast.exp.Exp
import pl.appsilon.marek.sparkdatalog.ast.types.Type

case class DeclarationConst(typ: Type, name: String, exp: Exp) extends Declaration
