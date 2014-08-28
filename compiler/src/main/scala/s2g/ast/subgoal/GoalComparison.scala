package s2g.ast.subgoal

import org.apache.spark.rdd.RDD
import s2g.eval.{TableStates, Context, EvaluationState, PartialSolution}
import s2g.ast.exp.Exp
import s2g.ast.comparisonoperator.ComparisonOperator
import s2g.spark.{Database, Valuation}

case class GoalComparison(left: Exp, right: Exp, operator: ComparisonOperator) extends Subgoal {
  override def solveOn(context: Context, tableStates: TableStates): Set[PartialSolution] = {
    val leftValue = left.evaluate(context)
    val rightValue = right.evaluate(context)
    val comparison = leftValue.typ.compare(leftValue, rightValue)
    if (operator.decide(comparison)) {
      Set(context.partialSolution)
    } else {
      Set()
    }
  }

  override def getInputs(context: Context): Set[String] =
    left.tryToEvaluate(context).getFreeVariables ++ right.tryToEvaluate(context).getFreeVariables

  override def getOutputs(context: Context): Set[String] = Set()

  override def getInVariables: Set[String] = left.getFreeVariables ++ right.getFreeVariables

  override def getOutVariables: Set[String] = Set()

  override def evaluateStatic(valuation: Valuation): Set[Valuation] =
    if(decideStatic(valuation))
      Set(valuation)
    else
      Set()

  def decideStatic(valuation: Valuation): Boolean = {
    operator.decide(left.evaluate(valuation) - left.evaluate(valuation))
  }

  override def join(valuations: RDD[Valuation], boundVariables: Set[String], database: Database): RDD[Valuation] =
    valuations.filter(decideStatic)

  override def select(initialValuations: Set[Valuation], boundVariables: Set[String], database: Database): RDD[Valuation] = ???
}
