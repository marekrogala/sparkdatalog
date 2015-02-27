package pl.appsilon.marek.sparkdatalog.ast.subgoal

import scala.collection.mutable

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import pl.appsilon.marek
import pl.appsilon.marek.sparkdatalog.{DatabaseRepr, Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.predicate.AnalyzedPredicate
import pl.appsilon.marek.sparkdatalog.eval.RelationInstance
import pl.appsilon.marek.sparkdatalog.eval.join.Join
import pl.appsilon.marek.sparkdatalog.eval.nonsharded.NonshardedState

case class AnalyzedGoalPredicate(
    predicate: AnalyzedPredicate,
    variableIds: Map[String, Int],
    boundVariables: Set[Int]) extends AnalyzedSubgoal {

  private val joinByVariables: mutable.WrappedArray[Int] = boundVariables.intersect(predicate.variables).toArray

  override def evaluateStatic(valuation: Valuation): Option[Valuation] = ???

  def extractBoundVariables(valuations: Seq[Valuation]): Seq[(Valuation, Valuation)] =
    for (valuation <- valuations) yield {
      val key = joinByVariables.map(valuation(_))
      key -> valuation
    }

  def extractBoundVariables(valuations: RDD[Valuation]): RDD[(Valuation, Valuation)] = {
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

    result
  }

  override def getLocation: Option[Int] = {
    predicate.args(0) match {
      case Right(varId) => Some(varId)
      case _ => ???
    }
  }


  def selectRDDFromDatabase(database: DatabaseRepr): Option[RDD[Valuation]] = {
    database.relations.get(predicate.tableName).map(predicate.evaluateRDD)
  }

  var preselectedRelationRDD: Option[Option[RDD[Valuation]]] = _
  var preextractedRelationBoundVariables: Option[Option[RDD[(Valuation, Valuation)]]] = _

  def prepareForIteration(state: NonshardedState) = {
    preselectedRelationRDD = if(!state.idb.contains(predicate.tableName)) {
      val valuations = selectRDDFromDatabase(state.database)
      valuations.foreach(_.cache())
      Some(valuations)
    } else None

    preextractedRelationBoundVariables = preselectedRelationRDD.map(_.map(extractBoundVariables))
  }

  def getRelationExtractedBoundVariables(database: DatabaseRepr) =
    preextractedRelationBoundVariables.getOrElse(selectRDDFromDatabase(database).map(extractBoundVariables))

  override def selectRDD(database: DatabaseRepr): Option[RDD[Valuation]] =
    preselectedRelationRDD.getOrElse(selectRDDFromDatabase(database))

  override def solveRDD(valuations: RDD[Valuation], database: DatabaseRepr): Option[RDD[Valuation]] = {
    val relation = getRelationExtractedBoundVariables(database)
    val result = relation.map({ left => {
      val right = extractBoundVariables(valuations)

      val joined = for ((boundVariables, (currentValuation, otherValuation)) <- left.join(right)) yield
      {
        currentValuation.zip(otherValuation).map({ case (l, r) => if(l != marek.sparkdatalog.valuationNone) l else r})
      }
      joined
    }
    })

    result
  }

}
