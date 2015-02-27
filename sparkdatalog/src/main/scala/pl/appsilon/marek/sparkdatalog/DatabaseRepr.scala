package pl.appsilon.marek.sparkdatalog

import org.apache.spark.HashPartitioner
import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.eval.SparkDatalog

case class DatabaseRepr(relations: Map[String, RelationRepr]) {
  def toDatabase: Database = Database(relations.mapValues(_.toRelation))

  def coalesce(numPartitions: Int): DatabaseRepr = copy(relations = relations.mapValues(_.coalesce(numPartitions)))

  def restrictTo(relationsSubset: Set[String]): DatabaseRepr = DatabaseRepr(relations.filterKeys(relationsSubset))

  def checkpoint(): Unit = relations.foreach(_._2.data.checkpoint())

  def subtract(other: DatabaseRepr, restrictToRelations: Set[String]) = {
    val myRelations = restrictTo(restrictToRelations).relations
    val otherRelations = other.restrictTo(restrictToRelations).relations
    val relationsDifference = for ((name, relation) <- myRelations) yield {
      name -> otherRelations.get(name).map(relation.subtract).getOrElse(relation)
    }
    copy(relationsDifference)
  }

  def isEmpty = relations.forall(_._2.isEmpty)

  def collect(): Map[String, Set[FactW]] = {
    relations.mapValues(relation => relation.data.collect().toSet)
  }

  def mergeIn(relation: RelationRepr, aggregation: Option[Aggregation]): DatabaseRepr = {
    val newInstance = (relations.get(relation.name) match {
      case None => relation
      case Some(previousInstance) => previousInstance.union(relation)
    }).combined
    new DatabaseRepr(relations + (newInstance.name -> newInstance))
  }

  def mergeIn(relations: Iterable[RelationRepr], aggregations: Map[String, Aggregation]): DatabaseRepr =
    relations.groupBy(_.name).foldLeft(this)({ case (db, rel) =>
      val (name, rels) = rel
      val mergedRels = rels.reduce(_.union(_))
      db.mergeIn(mergedRels, aggregations.get(name)) })

  def apply(relationName: String): RDD[FactW] = relations(relationName).data

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

object DatabaseRepr {
  def empty = new DatabaseRepr(Map())

  def apply(from: Database, aggregations: Map[String, Aggregation]) =
    new DatabaseRepr(from.relations.map(relation =>
      relation._1 -> RelationRepr(relation._2, aggregations.get(relation._1)).partitionByAndPersist(new HashPartitioner(8))))
}
