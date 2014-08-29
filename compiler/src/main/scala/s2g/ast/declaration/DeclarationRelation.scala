package s2g.ast.declaration

import s2g.ast.value.ValueLiteral
import s2g.eval.{SemanticException, LanguageError}

case class DeclarationRelation(name: String, columns: Seq[ColumnDeclaration]) extends Declaration {
  /** Semantic analysis */
  if(columns.count(_.aggregate.isDefined) > 1){
    throw new SemanticException("Multiple aggregate columns defined for relation " + name)
  }

  def aggregateColumnFunction: Option[(Int, (ValueLiteral, ValueLiteral) => ValueLiteral)] =
    aggregatedColumn map (column => (column, columns(column).aggregate.get.toScalaFunction))

  def aggregateColumnAndFunction: Option[(Int, (Int, Int) => Int)] =
    aggregatedColumn map (column => (column, columns(column).aggregate.get.toScalaIntFunction))

  def aggregatedColumn: Option[Int] = columns.indexWhere(_.aggregate.isDefined) match {
    case -1 => None
    case position => Some(position)
  }

}
