package pl.appsilon.marek.sparkdatalog.ast.subgoal

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.eval.RelationInstance
import pl.appsilon.marek.sparkdatalog.{Database, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.comparisonoperator.ComparisonOperator
import pl.appsilon.marek.sparkdatalog.ast.exp.Exp

case class GoalComparison(left: Exp, right: Exp, operator: ComparisonOperator) extends Subgoal {
  override def getInVariables: Set[String] = left.getFreeVariables ++ right.getFreeVariables
  override def getOutVariables: Set[String] = Set()

  override def evaluateStatic(valuation: Valuation): Seq[Valuation] =
    if(decideStatic(valuation))
      Seq(valuation)
    else
      Seq()

  def decideStatic(valuation: Valuation): Boolean = {
    operator.decide(left.evaluate(valuation) - right.evaluate(valuation))
  }

  override def join(valuations: RDD[Valuation], boundVariables: Set[String], database: Database): Option[RDD[Valuation]] =
    Some(valuations.filter(decideStatic))

  override def select(initialValuations: Set[Valuation], boundVariables: Set[String], database: Database): Option[RDD[Valuation]] = ???

  override def solveOn(valuation: Valuation, relations: Map[String, RelationInstance]): Seq[Valuation] = evaluateStatic(valuation)

  override def solveOnSet(valuations: Seq[Valuation], relations: Map[String, RelationInstance]): Seq[Valuation] =
    valuations.filter(decideStatic)

  override def getLocation: Option[String] = None
}
