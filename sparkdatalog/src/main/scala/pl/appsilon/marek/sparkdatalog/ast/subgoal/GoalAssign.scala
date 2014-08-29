package pl.appsilon.marek.sparkdatalog.ast.subgoal

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.exp.Exp

case class GoalAssign(lValueVariable: String, exp: Exp) extends Subgoal {

  override def getInVariables: Set[String] = exp.getFreeVariables

  override def getOutVariables: Set[String] = Set(lValueVariable)

  override def evaluateStatic(valuation: Valuation): Set[Valuation] = {
    val value = exp.evaluate(valuation)
    valuation.get(lValueVariable) match {
      case None => Set(valuation + (lValueVariable -> value))
      case Some(`value`) => Set(valuation)
      case other => Set()
    }
  }

  override def join(valuations: RDD[Valuation], boundVariables: Set[String], database: Database): Option[RDD[Valuation]] =
    Some(valuations.flatMap(evaluateStatic))

  override def select(initialValuations: Set[Valuation], boundVariables: Set[String], database: Database): Option[RDD[Valuation]] = ???
}
