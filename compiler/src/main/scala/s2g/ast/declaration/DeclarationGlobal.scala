package s2g.ast.declaration

import s2g.ast.value.ValueLiteral
import s2g.eval.LanguageError

case class DeclarationGlobal(name: String, columns: Seq[ColumnDeclaration]) extends Declaration {
  def aggregateColumnFunction: Option[(Int, (ValueLiteral, ValueLiteral) => ValueLiteral)] =
    aggregatedColumn map (column => (column, columns(column).aggregate.get.toScalaFunction))

  def aggregatedColumn: Option[Int] = columns.indexWhere(_.aggregate.isDefined) match {
    case -1 => None
    case position => Some(position)
  }

  override def validate(): Unit = {
    if(columns.count(_.aggregate.isDefined) > 1){
      throw new LanguageError("Multiple aggregate columns defined for table " + name)
    }
  }
}
