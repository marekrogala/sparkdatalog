package s2g.spark

import s2g.SparkDatalog

case class Database(relations: Map[String, Relation]) {
  def -(other: Database) = {
    val relationsDifference = for ((name, relation) <- relations) yield {
      name -> other.relations.get(name).map(relation - _).getOrElse(relation)
    }
    Database(relationsDifference)
  }

  def empty = relations.forall(_._2.empty)

  def collect(): Map[String, Set[Seq[Int]]] = {
    relations.mapValues(relation => relation.data.collect().toSet)
  }

  def +(other: Database) = {
    val mergedRelations = (relations.keySet ++ other.relations.keySet).map(key => {
      val mergedRelation = (relations.get(key), other.relations.get(key)) match {
        case (Some(r1), Some(r2)) => r1 + r2
        case (Some(r1), None) => r1
        case (None, Some(r2)) => r2
        case _ => ???
      }
    key -> mergedRelation
    }).toMap
    Database(mergedRelations)
  }

  def +(relation: Relation): Database = {
    val newInstance = relations.get(relation.name) match {
      case None => relation
      case Some(previousInstance) => previousInstance + relation
    }
    new Database(relations + (newInstance.name -> newInstance))
  }

  def ++(relations: Iterable[Relation]): Database = relations.foldLeft(this)(_ + _)

  def datalog(datalogQuery: String) = {
    SparkDatalog.datalog(this, datalogQuery)
  }
}

object Database {
  def apply(relations: Seq[Relation] = Seq()) =
    new Database(Map() ++ relations.map(relation => (relation.name, relation)))
}
