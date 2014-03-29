package s2g.ast.declaration

case class DeclarationGlobal(name: String, columns: Seq[ColumnDeclaration]) extends Declaration {

}
