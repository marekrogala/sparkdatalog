package pl.appsilon.marek.sparkdatalog.ast.declaration

import pl.appsilon.marek.sparkdatalog.ast.SemanticException
import pl.appsilon.marek.sparkdatalog.ast.value.ValueLiteral

case class DeclarationRelation(name: String, columns: Seq[ColumnDeclaration]) extends Declaration {
  /** Semantic analysis */
  if(columns.count(_.aggregate.isDefined) > 1){
    throw new SemanticException("Multiple aggregate columns defined for relation " + name)
  }

  def aggregateColumnAndFunction: Option[(Int, (Int, Int) => Int)] =
    aggregatedColumn map (column => (column, columns(column).aggregate.get.toScalaIntFunction))

  def aggregatedColumn: Option[Int] = columns.indexWhere(_.aggregate.isDefined) match {
    case -1 => None
    case position => Some(position)
  }

}
