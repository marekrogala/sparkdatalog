package pl.appsilon.marek.sparkdatalog.ast.subgoal

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.predicate.Predicate

case class GoalPredicate(predicate: Predicate) extends Subgoal {

  override def getInVariables: Set[String] = Set()
  override def getOutVariables: Set[String] = predicate.getVariables

  override def evaluateStatic(valuation: Valuation): Set[Valuation] = ???

  def extractBoundVariables(valuations: RDD[Valuation], boundVariables: Set[String]): RDD[(Valuation, Valuation)] =
    valuations.map(_.partition { keyValue => boundVariables.contains(keyValue._1)})
  def extractBoundVariables(valuations: Set[Valuation], boundVariables: Set[String]): Map[Valuation, Valuation] =
    Map() ++ valuations.map(_.partition { keyValue => boundVariables.contains(keyValue._1)})

  override def join(otherValuations: RDD[Valuation], boundVariables: Set[String], database: Database): Option[RDD[Valuation]] = {
    selectAll(database) map { currentValuations =>
      val joinByVariables = boundVariables.intersect(predicate.getVariables)
      val currentValuationsWithKey = extractBoundVariables(currentValuations, joinByVariables)
      val otherValuationsWithKey = extractBoundVariables(otherValuations, joinByVariables)
      val joinedValuations = currentValuationsWithKey.join(otherValuationsWithKey)
      val mergedValuations = joinedValuations.map(kvw => kvw._1 ++ kvw._2._1 ++ kvw._2._2)
      mergedValuations
    }
  }

  def selectAll(database: Database): Option[RDD[Valuation]] = {
    database.relations.get(predicate.tableName).map(predicate.evaluate)
  }

  override def select(otherValuations: Set[Valuation], boundVariables: Set[String], database: Database): Option[RDD[Valuation]] = {
    selectAll(database) map { currentValuations =>
      val joinByVariables = boundVariables.intersect(predicate.getVariables)
      val currentValuationsWithKey = extractBoundVariables(currentValuations, joinByVariables)
      val otherValuationsMap = extractBoundVariables(otherValuations, joinByVariables)
      val mergedValuations = currentValuationsWithKey.groupByKey().flatMap({ keyValue =>
        val (boundValuation, unboundValuations) = keyValue
        unboundValuations.flatMap { unboundValuation =>
          otherValuationsMap.get(boundValuation).map(_ ++ boundValuation ++ unboundValuation)
        }
      })
      mergedValuations
    }
  }
}
