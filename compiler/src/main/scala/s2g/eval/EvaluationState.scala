package s2g.eval

class EvaluationState private (
      val environment: PartialSolution,
      val oldTables: TableStates,
      val deltaTables: TableStates) {

  def this(environment: PartialSolution) = this(environment, new TableStates, new TableStates)

  def wasChangedInLastIteration: Boolean = !deltaTables.isEmpty

  def toNextIteration: EvaluationState = new EvaluationState(environment, accumulatedTables, new TableStates)

  override def toString: String =
    "Accumulated: \n" + oldTables.toString + "\n\nDelta:\n" + deltaTables.toString + "\n\n"
  
  private def contains(tableName: String, instance: Instance) =
    deltaTables.contains(tableName, instance) || oldTables.contains(tableName, instance)
  
  def putInstance(tableName: String, instance: Instance): EvaluationState =
    if (!contains(tableName, instance)) {
      new EvaluationState(environment, oldTables, deltaTables.putInstance(tableName, instance))
    } else {
      this
    }

  def accumulatedTables: TableStates = oldTables ++ deltaTables
  
}
