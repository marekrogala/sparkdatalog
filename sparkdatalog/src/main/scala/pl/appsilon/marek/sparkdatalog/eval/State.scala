package pl.appsilon.marek.sparkdatalog.eval

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog
import pl.appsilon.marek.sparkdatalog.{Database, Relation}
import pl.appsilon.marek.sparkdatalog.spark.OuterJoinableRDD._

case class State(shards: RDD[(Long, StateShard)]) {
  def deltaEmpty: Boolean = shards.map(_._2.deltaEmpty).collect().forall(identity)

  private def subtract(left: RDD[(Long, StateShard)], right: RDD[(Long, StateShard)]) = {
    left.leftOuterJoin(right).map {
      case (key, (leftShard, rightShardOption)) => key -> leftShard.delted(rightShardOption)
    }
  }

  def step(newShards: RDD[(Long, StateShard)]) = State(subtract(newShards, shards))

  def toDatabase(relationNames: Seq[String]): Database = {
    val relations = relationNames map { relationName =>
      relationName -> Relation(relationName, shards.flatMap(_._2.relations.get(relationName).map(_.facts.toSeq).getOrElse(Seq())))
    }
    new Database(relations.toMap)
  }

  def cache(): this.type = {
    shards.cache()
    this
  }

  def unpersist(blocking: Boolean = true): this.type = {
    shards.unpersist(blocking)
    this
  }

  def materialize(): this.type = {
    shards.count()
    this
  }

  override def toString = {
    shards.collect().mkString(", ")
  }

}

object State {
  def relationToKeyValue(relation: Relation): RDD[(Long, StateShard)] =
    relation.data.groupBy(sparkdatalog.keyForFact).map({
      case (key, value) => (key, StateShard(Map(relation.name -> RelationInstance(relation.name, value.toSeq))))
    })

  def fromDatabase(database: Database): State = {
    val rddRelations: Iterable[Relation] = database.relations.values

    val relations = rddRelations.tail.foldLeft(relationToKeyValue(rddRelations.head))({
      case (acc, relation) =>
        acc.fullOuterJoin(relationToKeyValue(relation)).map({
          case (key, (Some(left), Some(right))) => (key, left ++ right)
          case (key, (None, Some(right))) => (key, right)
          case (key, (Some(left), None)) => (key, left)
        })
    })
    new State(relations)
  }

}
