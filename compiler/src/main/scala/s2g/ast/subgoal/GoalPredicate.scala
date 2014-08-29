package s2g.ast.subgoal

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import s2g.ast.predicate.Predicate
import s2g.eval.{Context, PartialSolution, TableStates}
import s2g.spark.{Database, Valuation}

case class GoalPredicate(predicate: Predicate) extends Subgoal {
  override def solveOn(context: Context, tableStates: TableStates): Set[PartialSolution] = {
    predicate.fetchMatchingInstances(context.partialSolution, tableStates, context.environment)
  }

  override def getOutputs(context: Context): Set[String] = predicate.getOutputs(context)

  override def getInputs(context: Context): Set[String] = predicate.getInputs(context)

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
      //println("join " + currentValuationsWithKey.collect().mkString("; ") + " with "  + otherValuationsWithKey.collect().mkString("; ") + ", bound="+boundVariables.toString())
      //println("Joined valuations: \t " + joinedValuations.collect().mkString("; "))
      val mergedValuations = joinedValuations.map(kvw => kvw._1 ++ kvw._2._1 ++ kvw._2._2)
      mergedValuations
    }
  }

  def selectAll(database: Database): Option[RDD[Valuation]] = {
    println("selectAll " + predicate.tableName + " from database " + database.toString)
    database.relations.get(predicate.tableName).map(predicate.evaluate)
  }

  override def select(otherValuations: Set[Valuation], boundVariables: Set[String], database: Database): Option[RDD[Valuation]] = {
    val all = selectAll(database)
    all map { currentValuations =>
      //println("selecting " + currentValuations.collect().mkString("; "))
      val joinByVariables = boundVariables.intersect(predicate.getVariables)
      val currentValuationsWithKey = extractBoundVariables(currentValuations, joinByVariables)
      val otherValuationsMap = extractBoundVariables(otherValuations, joinByVariables)
      val mergedValuations = currentValuationsWithKey.groupByKey().flatMap({ keyValue =>
        val (boundValuation, unboundValuations) = keyValue
        unboundValuations.flatMap { unboundValuation =>
          otherValuationsMap.get(boundValuation).map(_ ++ boundValuation ++ unboundValuation)
        }
      })
      println("   --> " + mergedValuations.collect().mkString("; "))
      mergedValuations
    }
  }
}
