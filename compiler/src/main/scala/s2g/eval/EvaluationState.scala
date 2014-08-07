package s2g.eval

import s2g.ast.declaration.{DeclarationGlobal, Declaration}

case class EvaluationState private (
      environment: PartialSolution,
      tables: Seq[Declaration],
      oldTables: TableStates,
      accumulatedTables: TableStates) {

  def this(environment: PartialSolution, tables: Seq[Declaration]) = this(environment, tables, new TableStates, new TableStates)

  def wasChangedInLastIteration: Boolean = !deltaTables.isEmpty

  def toNextIteration: EvaluationState = new EvaluationState(environment, tables, accumulatedTables, accumulatedTables)

  override def toString: String =
    "Old: \n" + oldTables.toString + "\n\nDelta:\n" + deltaTables.toString + "\n\n"

  private def findTable(tableName: String): DeclarationGlobal = tables.find(_.name == tableName) match {
    case Some(decl@DeclarationGlobal(_, _)) => decl
    case Some(_) => throw new LanguageError("Cannot use " + tableName + " as a predicate")
    case None => throw new LanguageError("Undeclared table " + tableName)
  }

  private def contains(tableName: String, instance: Instance) =
    deltaTables.contains(tableName, instance) || oldTables.contains(tableName, instance)
  
  def putInstance(tableName: String, instance: Instance): EvaluationState = {
    val aggregate = findTable(tableName).aggregateColumnFunction
    this.copy(accumulatedTables = accumulatedTables.putInstance(tableName, instance, aggregate))
  }

  def deltaTables: TableStates = accumulatedTables diff oldTables
  
}
