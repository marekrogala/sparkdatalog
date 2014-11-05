package pl.appsilon.marek.sparkdatalog

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.eval.{StateShard, LocalDatalog, SparkDatalog}

case class Database(relations: Map[String, Relation]) {
  def coalesce(numPartitions: Int): Database = copy(relations = relations.mapValues(_.coalesce(numPartitions)))

  def restrictTo(relationsSubset: Set[String]): Database = Database(relations.filterKeys(relationsSubset))

  def checkpoint(): Unit = relations.foreach(_._2.data.checkpoint())

  def subtract(other: Database, restrictToRelations: Set[String]) = {
    val myRelations = restrictTo(restrictToRelations).relations
    val otherRelations = other.restrictTo(restrictToRelations).relations
    val relationsDifference = for ((name, relation) <- myRelations) yield {
      name -> otherRelations.get(name).map(relation.subtract).getOrElse(relation)
    }
    Database(relationsDifference)
  }

  def isEmpty = relations.forall(_._2.isEmpty)

  def collect(): Map[String, Set[Fact]] = {
    relations.mapValues(relation => relation.data.collect().toSet)
  }

  def mergeIn(relation: Relation, aggregation: Option[Aggregation]): Database = {
    val newInstance = (relations.get(relation.name) match {
      case None => relation
      case Some(previousInstance) => previousInstance.union(relation)
    }).combine(aggregation)
    new Database(relations + (newInstance.name -> newInstance))
  }

  def mergeIn(relations: Iterable[Relation], aggregations: Map[String, Aggregation]): Database =
    relations.groupBy(_.name).foldLeft(this)({ case (db, rel) =>
      val (name, rels) = rel
      println("merging " + name + " into db")
      val mergedRels = rels.reduce(_.union(_))
      db.mergeIn(mergedRels, aggregations.get(name)) })

  def datalog(datalogQuery: String): Database = {
    SparkDatalog.datalog(this, datalogQuery)
  }

  def datalogLocally(datalogQuery: String): Seq[(Long, StateShard)] = {
    LocalDatalog.datalog(this, datalogQuery)
  }

  def apply(relationName: String): RDD[Fact] = relations(relationName).data

  def cache(): this.type = {
    relations.foreach(_._2.data.cache())
    this
  }

  def unpersist(blocking: Boolean = true): this.type = {
    relations.foreach(_._2.data.unpersist(blocking))
    this
  }

  def materialize(): this.type = {
    relations.foreach(_._2.data.count())
    this
  }

}

object Database {
  def empty: Database = new Database(Map())

  def apply(relations: Relation*) =
    new Database(Map() ++ relations.map(relation => (relation.name, relation)))
}
