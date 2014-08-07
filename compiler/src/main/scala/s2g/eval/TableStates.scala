package s2g.eval

import s2g.ast.value.ValueLiteral

class TableStates private(private val tables: Map[String, TableState]) {
  def isEmpty: Boolean = tables.forall({ case (_, state) => state.isEmpty })

  def ++(other: TableStates) = {
    val merged = other.tables.foldLeft(tables)({
      case (acc, (tableName, tableState)) =>
        val newTableState = acc.get(tableName) map (_ ++ tableState) getOrElse tableState
        val newEntry = tableName -> newTableState
        acc + newEntry
    })
    new TableStates(merged)
  }

  def diff(other: TableStates) = {
    val delta = tables.foldLeft(Map[String, TableState]())({
      case (acc, (tableName, tableState)) =>
        val tableDelta = other.tables.get(tableName) map tableState.diff getOrElse tableState
        acc + (tableName -> tableDelta)
    })
    new TableStates(delta)
  }

  def find(tableName: String, pattern: Pattern): Set[PartialSolution] =
    tables.get(tableName).map(_.findMatching(pattern)).getOrElse(Set())

  def putInstance(
      tableName: String,
      instance: Instance,
      aggregate: Option[(Int, (ValueLiteral, ValueLiteral) => ValueLiteral)]): TableStates = {
    val tableState = tables.getOrElse(tableName, new TableState())
    val newTableState = tableState.add(instance).aggregate(aggregate)
    new TableStates(tables + (tableName -> newTableState))
  }

  def contains(tableName: String, instance: Instance): Boolean =
    tables.get(tableName) exists (_.contains(instance))

  override def toString: String = (tables map (entry => entry._2.toStringInstances(entry._1))).mkString("\n")

  def this() = this(Map())
}
