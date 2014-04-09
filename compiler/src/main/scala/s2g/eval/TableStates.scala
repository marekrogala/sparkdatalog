package s2g.eval

class TableStates(private val tables: Map[String, TableState]) {
  def ++(other: TableStates) = {
    val merged = other.tables.foldLeft(tables)({
      case (acc, (tableName, tableState)) => {
        val newTableState = acc.get(tableName) map (_ ++ tableState) getOrElse tableState
        val newEntry = tableName -> newTableState
        acc + newEntry
      }
    })
    new TableStates(merged)
  }

  def find(tableName: String, pattern: Pattern): Set[PartialSolution] =
    tables.get(tableName).map(_.findMatching(pattern)).getOrElse(Set())

  def putInstance(tableName: String, instance: Instance): TableStates = {
    val tableState = tables.getOrElse(tableName, new TableState())
    new TableStates(tables + (tableName -> tableState.add(instance)))
  }

  def contains(tableName: String, instance: Instance): Boolean =
    tables.get(tableName) exists (_.contains(instance))

  override def toString: String = (tables map (entry => entry._2.toStringInstances(entry._1))).mkString("\n")

  def this() = this(Map())
}
