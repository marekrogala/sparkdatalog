package s2g.eval

class EvaluationState(val environment: PartialSolution) {

  var rTables: Map[String, TableState] = Map()
  var deltaTables: Map[String, TableState] = Map()

  var wasChangedInLastIteration: Boolean = false

  def squashDelta() = {
    for((tableName, tableState) <- deltaTables) {
      rTables += tableName -> (rTables.get(tableName) map (_ ++ tableState) getOrElse tableState)
    }
    deltaTables = Map()
  }

  def beginIteration(): Unit = {
    squashDelta()
    wasChangedInLastIteration = false
  }

  override def toString: String = (rTables map (entry => entry._2.toStringInstances(entry._1))).mkString("\n\n")

  private def inDeltaTable(tableName: String, instance: Instance) =
    deltaTables.get(tableName) exists (_.contains(instance))
  
  private def inResultTable(tableName: String, instance: Instance) =
    rTables.get(tableName) exists (_.contains(instance))
  
  private def inAnyTable(tableName: String, instance: Instance) =
    inDeltaTable(tableName, instance) || inResultTable(tableName, instance)
  
  def putInstance(tableName: String, instance: Instance): Unit = {
    if (!inAnyTable(tableName, instance)) {
      val tableState = rTables.getOrElse(tableName, new TableState())
      rTables += tableName -> tableState.add(instance)
      wasChangedInLastIteration = true
    }
  }

  def findAnyMatchingInstances(tableName: String, pattern: Pattern): Set[PartialSolution] =
    findMatchingInstancesInDelta(tableName, pattern) ++ findMatchingInstancesInR(tableName, pattern)

  def findMatchingInstancesInDelta(tableName: String, pattern: Pattern): Set[PartialSolution] =
    findMatchingInstancesInTable(deltaTables.get(tableName), pattern)

  def findMatchingInstancesInR(tableName: String, pattern: Pattern): Set[PartialSolution] =
    findMatchingInstancesInTable(rTables.get(tableName), pattern)

  def findMatchingInstancesInTable(table: Option[TableState], pattern: Pattern): Set[PartialSolution] = {
    table match {
      case Some(tableState) => tableState.findMatching(pattern)
      case None => Set()
    }
  }

}
