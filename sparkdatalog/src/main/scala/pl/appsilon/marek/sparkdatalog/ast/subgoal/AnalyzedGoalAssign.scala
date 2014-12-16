package pl.appsilon.marek.sparkdatalog.ast.subgoal

import org.apache.spark.rdd.RDD
import pl.appsilon.marek
import pl.appsilon.marek.sparkdatalog.{Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.exp.AnalyzedExp
import pl.appsilon.marek.sparkdatalog.eval.RelationInstance

case class AnalyzedGoalAssign(lValueVariable: Int, exp: AnalyzedExp) extends AnalyzedSubgoal {

  override def evaluateStatic(valuation: Valuation): Option[Valuation] = {
    val value = exp.evaluate(valuation)
    valuation(lValueVariable) match {
      case marek.sparkdatalog.valuationNone =>
        valuation(lValueVariable) = value
        Some(valuation)
      case `value` => Some(valuation)
      case _ => None
    }
  }

  override def solveOn(valuation: Valuation, relations: Map[String, RelationInstance]): Seq[Valuation] = evaluateStatic(valuation).toSeq

  override def solveOnSet(valuations: Seq[Valuation], relations: Map[String, RelationInstance]): Seq[Valuation] =
    valuations.map(evaluateStatic).flatten

  override def getLocation: Option[Int] = None

  override def solveRDD(valuations: RDD[Valuation], database: Database): Option[RDD[Valuation]] = {
    Some(valuations.flatMap(evaluateStatic))
  }

  override def selectRDD(database: Database): Option[RDD[Valuation]] = ???
}
