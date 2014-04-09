package s2g.eval

class EvaluationState(val environment: PartialSolution) {

  var oldTables = new TableStates
  var deltaTables = new TableStates

  var wasChangedInLastIteration: Boolean = false

  def squashDelta() = {
    oldTables = accumulatedTables
    deltaTables = new TableStates
  }

  def beginIteration(): Unit = {
    squashDelta()
    wasChangedInLastIteration = false
  }

  override def toString: String = "Accumulated: \n" + oldTables.toString + "\n\nDelta:\n" + deltaTables.toString + "\n\n"
  
  private def contains(tableName: String, instance: Instance) =
    deltaTables.contains(tableName, instance) || oldTables.contains(tableName, instance)
  
  def putInstance(tableName: String, instance: Instance): Unit = {
    if (!contains(tableName, instance)) {
      deltaTables = deltaTables.putInstance(tableName, instance)
      wasChangedInLastIteration = true
    }
  }

  def accumulatedTables: TableStates = oldTables ++ deltaTables
  
}
