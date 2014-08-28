package s2g.ast.predicate

import org.apache.spark.rdd.RDD
import s2g.eval._
import s2g.ast.value.{ValueVar, ValueLiteral, Value}
import s2g.eval.Context
import s2g.eval.Pattern
import s2g.eval.PartialSolution
import s2g.spark.{Valuation, Relation}

case class Predicate(tableName: String, args: Seq[Value]) {
  def matchArgs(fact: Seq[Int]): Option[Valuation] = {
    fact.zip(args).foldLeft(Some(Valuation()): Option[Valuation]) {
      case (valuationOption, (actualValue, literal: ValueLiteral)) =>
        valuationOption.filter { _ => literal.value.asInstanceOf[Int] == actualValue }
      case (valuationOption, (actualValue, ValueVar(varName))) =>
        valuationOption.flatMap(_.get(varName) match {
            case Some(`actualValue`) => valuationOption
            case Some(_) => None
            case None => valuationOption
          }
        )
    }
  }

  def evaluate(relation: Relation): RDD[Valuation] = {
    relation.data.flatMap(matchArgs)
  }

  def buildPattern(context: Context): Pattern = Pattern(args map (_.tryToEvaluate(context)))

  def fetchMatchingInstances(
      partialSolution: PartialSolution,
      tableStates: TableStates,
      environment: PartialSolution): Set[PartialSolution] =
    tableStates.find(tableName, buildPattern(Context(environment, partialSolution))).map(_ ++ partialSolution)

  def getOutputs(context: Context): Set[String] = args.flatMap(_.tryToEvaluate(context).getFreeVariables).toSet

  def getInputs(context: Context): Set[String] = Set()

  def getOutVariables: Set[String] = args.flatMap(_.getFreeVariables).toSet

  def getInVariables: Set[String] = Set()
}
