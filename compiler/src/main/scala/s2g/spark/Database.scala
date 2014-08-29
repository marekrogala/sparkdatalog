package s2g.spark

import s2g.SparkDatalog

case class Database(relations: Map[String, Relation]) {

  def subtract(other: Database) = {
    val relationsDifference = for ((name, relation) <- relations) yield {
      name -> other.relations.get(name).map(relation.subtract).getOrElse(relation)
    }
    Database(relationsDifference)
  }

  def empty = relations.forall(_._2.empty)

  def collect(): Map[String, Set[Seq[Int]]] = {
    relations.mapValues(relation => relation.data.collect().toSet)
  }

  def union(other: Database, aggregations: Map[String, Aggregation]) =
    this.mergeIn(other.relations.values, aggregations)

  def mergeIn(relation: Relation, aggregation: Option[Aggregation]): Database = {
    val newInstance = relations.get(relation.name) match {
      case None => relation
      case Some(previousInstance) => previousInstance.union(relation, aggregation)
    }
    new Database(relations + (newInstance.name -> newInstance))
  }

  def mergeIn(relations: Iterable[Relation], aggregations: Map[String, Aggregation]): Database =
    relations.foldLeft(this)({ case (db, rel) => db.mergeIn(rel, aggregations.get(rel.name)) })

  def datalog(datalogQuery: String) = {
    SparkDatalog.datalog(this, datalogQuery)
  }
}

object Database {
  def apply(relations: Iterable[Relation] = Seq()) =
    new Database(Map() ++ relations.map(relation => (relation.name, relation)))
}
