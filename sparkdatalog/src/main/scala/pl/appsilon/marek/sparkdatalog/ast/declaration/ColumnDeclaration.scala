package pl.appsilon.marek.sparkdatalog.ast.declaration

import pl.appsilon.marek.sparkdatalog.ast.types.Type

case class ColumnDeclaration(typ: Type, name: String, aggregate: Option[Function]) {

}
