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

  private def mergeInRelation(relation: RelationRepr): (RelationRepr, RelationRepr) = {
    relations.get(relation.name) match {
      case None =>
        val combined = relation.combined
        (combined, combined)
      case Some(previousInstance) => previousInstance.unionDelta(relation)
    }
  }

  def mergeIn(newRelations: Iterable[RelationRepr]): (DatabaseRepr, DatabaseRepr) = {
    val (fullRelations, deltaRelations) = newRelations.groupBy(_.name).toSeq.map({ case (name, rels) =>
      val mergedRels = rels.reduce(_.union(_))
      val (fullRel, deltaRel) = mergeInRelation(mergedRels)
      (name -> fullRel, name -> deltaRel)
    }).unzip
    (DatabaseRepr(relations ++ fullRelations), DatabaseRepr(Map() ++ deltaRelations))
  }

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
