package pl.appsilon.marek.sparkdatalog.ast.subgoal

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.eval.RelationInstance
import pl.appsilon.marek.sparkdatalog.{Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.exp.Exp

case class GoalAssign(lValueVariable: String, exp: Exp) extends Subgoal {

  override def getInVariables: Set[String] = exp.getFreeVariables

  override def getOutVariables: Set[String] = Set(lValueVariable)

  override def evaluateStatic(valuation: Valuation): Seq[Valuation] = {
    val value = exp.evaluate(valuation)
    valuation.get(lValueVariable) match {
      case None => Seq(valuation + (lValueVariable -> value))
      case Some(`value`) => Seq(valuation)
      case other => Seq()
    }
  }

  override def join(valuations: RDD[Valuation], boundVariables: Set[String], database: Database): Option[RDD[Valuation]] =
    Some(valuations.flatMap(evaluateStatic))

  override def select(initialValuations: Set[Valuation], boundVariables: Set[String], database: Database): Option[RDD[Valuation]] = ???

  override def solveOn(valuation: Valuation, relations: Map[String, RelationInstance]): Seq[Valuation] = evaluateStatic(valuation)

  override def solveOnSet(valuations: Seq[Valuation], relations: Map[String, RelationInstance]): Seq[Valuation] =
    valuations.map(evaluateStatic).flatten // TODO maybe some more effective way?
}
