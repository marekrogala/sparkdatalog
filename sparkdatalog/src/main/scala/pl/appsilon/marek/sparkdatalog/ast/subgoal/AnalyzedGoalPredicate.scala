package pl.appsilon.marek.sparkdatalog.ast.subgoal

import scala.collection.mutable

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import pl.appsilon.marek
import pl.appsilon.marek.sparkdatalog.{Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.predicate.AnalyzedPredicate
import pl.appsilon.marek.sparkdatalog.eval.RelationInstance
import pl.appsilon.marek.sparkdatalog.eval.join.Join

case class AnalyzedGoalPredicate(predicate: AnalyzedPredicate, variableIds: Map[String, Int], boundVariables: Set[Int]) extends AnalyzedSubgoal {

  private val joinByVariables: mutable.WrappedArray[Int] = boundVariables.intersect(predicate.variables).toArray

  override def evaluateStatic(valuation: Valuation): Option[Valuation] = ???

  def extractBoundVariables(valuations: Seq[Valuation]): Seq[(Valuation, Valuation)] =
    for (valuation <- valuations) yield {
      val key = joinByVariables.map(valuation(_))
      key -> valuation
    }

  def extractBoundVariables(valuations: RDD[Valuation]): RDD[(Valuation, Valuation)] = {
    println("MAP in extractBoundVariables")
    val result = for (valuation <- valuations) yield {
      val key = joinByVariables.map(valuation(_))
      key -> valuation
    }
    result
  }

  override def solveOn(valuation: Valuation, relations: Map[String, RelationInstance]): Seq[Valuation] =
    relations.get(predicate.tableName)
      .map(relation => predicate.fetchMatchingInstances(valuation, relation))
      .getOrElse(Seq())

  override def solveOnSet(valuations: Seq[Valuation], relations: Map[String, RelationInstance]): Seq[Valuation] = {
    val variablesFromTable: Option[Seq[Valuation]] = relations.get(predicate.tableName).map(predicate.evaluateLocally)
    val result = variablesFromTable.map({ currentValuations => {
        val left = extractBoundVariables(currentValuations)
        val right = extractBoundVariables(valuations)
  
        val joined = for ((boundVariables, (currentKeyValuations, otherKeyValuations)) <- Join.innerJoin(left, right)) yield
        {
          for (currentValuation <- currentKeyValuations;
               otherValuation <- otherKeyValuations) yield {
            currentValuation.zip(otherValuation).map({ case (l, r) => if(l != marek.sparkdatalog.valuationNone) l else r})
          }
        }
        joined.toSeq.flatten
    }
    }).getOrElse(Seq())

    //println("solve " + predicate + " OnSet " + valuations + " relations = " + relations + "\n\t --> " + result)

    result
  }

  override def getLocation: Option[Int] = {
    predicate.args(0) match {
      case Right(varId) => Some(varId)
      case _ => ???
    }
  }

  override def solveRDD(valuations: RDD[Valuation], database: Database): Option[RDD[Valuation]] = {
    val variablesFromTable: Option[RDD[Valuation]] = selectRDD(database)
    val result = variablesFromTable.map({ currentValuations => {
      val left = extractBoundVariables(currentValuations)
      val right = extractBoundVariables(valuations)

      println("JOIN; MAP zip")
      val joined = for ((boundVariables, (currentValuation, otherValuation)) <- left.join(right)) yield
      {
        currentValuation.zip(otherValuation).map({ case (l, r) => if(l != marek.sparkdatalog.valuationNone) l else r})
      }
      joined
    }
    })

//    println("solve " + predicate + " OnSet " + valuations + " relations = " + database.relations + "\n\t --> " + result.map(_.collect().map(_.mkString("(", ",", ")")).mkString(";")))
    result
  }

  override def selectRDD(database: Database): Option[RDD[Valuation]] = {
    database.relations.get(predicate.tableName).map(predicate.evaluateRDD)
  }
}
