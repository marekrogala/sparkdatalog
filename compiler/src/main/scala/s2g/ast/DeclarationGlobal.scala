package s2g.ast

case class DeclarationGlobal(name: String, columns: Seq[ColumnDeclaration]) extends Declaration {

}
