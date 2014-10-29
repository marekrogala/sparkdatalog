package pl.appsilon.marek.sparkdatalog.ast.subgoal

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

import scala.collection.mutable

import pl.appsilon.marek.sparkdatalog.{Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.predicate.AnalyzedPredicate
import pl.appsilon.marek.sparkdatalog.eval.RelationInstance
import pl.appsilon.marek.sparkdatalog.eval.join.Join

case class AnalyzedGoalPredicate(predicate: AnalyzedPredicate, variableIds: Map[String, Int], boundVariables: Set[Int]) extends AnalyzedSubgoal {

  private val joinByVariables = mutable.ArraySeq() ++ boundVariables.intersect(predicate.variables)

  override def evaluateStatic(valuation: Valuation): Option[Valuation] = ???

  def extractBoundVariables(valuations: Seq[Valuation]): Seq[(Valuation, Valuation)] =
  // TODO: moze byc jeszcze bardziej efektywne, przejscie jednoczesnie po obu listach
    for (valuation <- valuations) yield {
      val key = joinByVariables.map(valuation(_))
      key -> valuation
    }

  def extractBoundVariables(valuations: RDD[Valuation]): RDD[(Valuation, Valuation)] =
  // TODO: moze byc jeszcze bardziej efektywne, przejscie jednoczesnie po obu listach
    for (valuation <- valuations) yield {
      val key = joinByVariables.map(valuation(_))
      key -> valuation
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
            currentValuation.zip(otherValuation).map({ case (l, r) => l.orElse(r)})
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
    val variablesFromTable: Option[RDD[Valuation]] = database.relations.get(predicate.tableName).map(predicate.evaluateRDD)
    val result = variablesFromTable.map({ currentValuations => {
      val left = extractBoundVariables(currentValuations)
      val right = extractBoundVariables(valuations)

      val joined = for ((boundVariables, (currentValuation, otherValuation)) <- left.join(right)) yield
      {
        currentValuation.zip(otherValuation).map({ case (l, r) => l.orElse(r)})
      }
      joined
    }
    })

    //println("solve " + predicate + " OnSet " + valuations + " relations = " + relations + "\n\t --> " + result)
    result
  }
}
