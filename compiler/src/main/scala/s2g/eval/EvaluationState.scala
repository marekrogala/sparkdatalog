package s2g.eval

import s2g.ast.declaration.Declaration

class EvaluationState private (
      val environment: PartialSolution,
      val tables: Seq[Declaration],
      val oldTables: TableStates,
      val deltaTables: TableStates) {

  def this(environment: PartialSolution, tables: Seq[Declaration]) = this(environment, tables, new TableStates, new TableStates)

  def wasChangedInLastIteration: Boolean = !deltaTables.isEmpty

  def toNextIteration: EvaluationState = new EvaluationState(environment, tables, accumulatedTables, new TableStates)

  override def toString: String =
    "Old: \n" + oldTables.toString + "\n\nDelta:\n" + deltaTables.toString + "\n\n"
  
  private def contains(tableName: String, instance: Instance) =
    deltaTables.contains(tableName, instance) || oldTables.contains(tableName, instance)
  
  def putInstance(tableName: String, instance: Instance): EvaluationState =
    if (!contains(tableName, instance)) {
      new EvaluationState(environment, tables, oldTables, deltaTables.putInstance(tableName, instance))
    } else {
      this
    }

  def accumulatedTables: TableStates = oldTables ++ deltaTables
  
}
