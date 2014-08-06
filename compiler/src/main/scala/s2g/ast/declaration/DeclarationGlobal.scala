package s2g.ast.declaration

import s2g.eval.LanguageError

case class DeclarationGlobal(name: String, columns: Seq[ColumnDeclaration]) extends Declaration {
  override def validate(): Unit = {
    if(columns.count(_.aggregate.isDefined) > 1){
      throw new LanguageError("Multiple aggregate columns defined for table " + name)
    }
  }
}
