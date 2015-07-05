package pl.appsilon.marek.sparkdatalog.eval.nonsharded

import org.apache.spark.SparkContext
import pl.appsilon.marek.sparkdatalog.DatabaseRepr

case class NonshardedState(database: DatabaseRepr, previousDatabase: DatabaseRepr, idb: Set[String] = Set(), delta: DatabaseRepr = DatabaseRepr.empty) {
  val sc: SparkContext = database.relations.head._2.data.context

  def checkpoint() = {
    database.restrictTo(idb).checkpoint()
    previousDatabase.restrictTo(idb).checkpoint()
    delta.restrictTo(idb).checkpoint()
  }

  def deltaEmpty: Boolean = delta.isEmpty

  def cache(): this.type = {
    database.restrictTo(idb).cache()
    previousDatabase.restrictTo(idb).cache()
    delta.restrictTo(idb).cache()
    this
  }

  def unpersist(blocking: Boolean = true): this.type = {
    database.restrictTo(idb).unpersist(blocking)
    previousDatabase.restrictTo(idb).unpersist(blocking)
    delta.restrictTo(idb).unpersist(blocking)
    this
  }

  def unpersistExceptLast(blocking: Boolean = true): this.type = {
    previousDatabase.restrictTo(idb).unpersist(blocking)
    delta.restrictTo(idb).unpersist(blocking)
    this
  }

  def materialize(): this.type = {
    database.restrictTo(idb).materialize()
    previousDatabase.restrictTo(idb).materialize()
    delta.restrictTo(idb).materialize()
    this
  }

  override def toString = {
    "State: " +  database.toString + " Delta: " + delta.toString
  }

  def prepareForIteration(newIdb: Set[String]): NonshardedState = {
    copy(idb = newIdb, delta = database.restrictTo(newIdb))
  }

}

object NonshardedState {
  def fromDatabase(database: DatabaseRepr): NonshardedState = {
    new NonshardedState(database, database)
  }

}
