package s2g.ast.subgoal

import org.apache.spark.rdd.RDD
import s2g.ast.comparisonoperator.ComparisonOperator
import s2g.ast.exp.Exp
import s2g.spark.{Database, Valuation}

case class GoalComparison(left: Exp, right: Exp, operator: ComparisonOperator) extends Subgoal {
  override def getInVariables: Set[String] = left.getFreeVariables ++ right.getFreeVariables
  override def getOutVariables: Set[String] = Set()

  override def evaluateStatic(valuation: Valuation): Set[Valuation] =
    if(decideStatic(valuation))
      Set(valuation)
    else
      Set()

  def decideStatic(valuation: Valuation): Boolean = {
    operator.decide(left.evaluate(valuation) - right.evaluate(valuation))
  }

  override def join(valuations: RDD[Valuation], boundVariables: Set[String], database: Database): Option[RDD[Valuation]] =
    Some(valuations.filter(decideStatic))

  override def select(initialValuations: Set[Valuation], boundVariables: Set[String], database: Database): Option[RDD[Valuation]] = ???
}
