package s2g.eval

class EvaluationState(val environment: PartialSolution) {

  var tables: Map[String, TableState] = Map()
  var wasChangedInLastIteration: Boolean = false

  def beginIteration(): Unit = {
    wasChangedInLastIteration = false
  }

  override def toString: String = (tables map (entry => entry._2.toStringInstances(entry._1))).mkString("\n\n")

  def putInstance(tableName: String, instance: Instance): Unit = {
    val tableState = tables.getOrElse(tableName, new TableState())
    if (!tableState.contains(instance)) {
      tables += tableName -> tableState.add(instance)
      wasChangedInLastIteration = true
    }
  }
  
  def findMatchingInstances(tableName: String, pattern: Pattern): Set[PartialSolution] = {
    tables.get(tableName) match {
      case Some(tableState) => tableState.findMatching(pattern)
      case None => Set()
    }
  }
}
