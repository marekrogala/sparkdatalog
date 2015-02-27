package pl.appsilon.marek.sparkdatalog.ast.subgoal

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{DatabaseRepr, Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.comparisonoperator.ComparisonOperator
import pl.appsilon.marek.sparkdatalog.ast.exp.AnalyzedExp
import pl.appsilon.marek.sparkdatalog.eval.RelationInstance

case class AnalyzedGoalComparison(left: AnalyzedExp, right: AnalyzedExp, operator: ComparisonOperator) extends AnalyzedSubgoal {
  override def evaluateStatic(valuation: Valuation): Option[Valuation] =
    if(decideStatic(valuation))
      Some(valuation)
    else
      None

  def decideStatic(valuation: Valuation): Boolean = {
    operator.decide(left.evaluate(valuation) - right.evaluate(valuation))
  }

  override def solveOn(valuation: Valuation, relations: Map[String, RelationInstance]): Seq[Valuation] = evaluateStatic(valuation).toSeq

  override def solveOnSet(valuations: Seq[Valuation], relations: Map[String, RelationInstance]): Seq[Valuation] =
    valuations.filter(decideStatic)

  override def getLocation: Option[Int] = None

  override def solveRDD(valuations: RDD[Valuation], database: DatabaseRepr): Option[RDD[Valuation]] = {
    Some(valuations.filter(decideStatic))
  }

  override def selectRDD(database: DatabaseRepr): Option[RDD[Valuation]] = ???
}
