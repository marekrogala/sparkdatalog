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
      case q@(valuationOption, (actualValue, literal: ValueLiteral)) =>
        valuationOption.filter { _ => literal.value.asInstanceOf[Int] == actualValue }
      case q@(valuationOption, (actualValue, ValueVar(varName))) =>
        valuationOption.flatMap { valuation =>
          valuation.get(varName) match {
            case Some(`actualValue`) => valuationOption
            case Some(_) => None
            case None => Some(valuation + (varName -> actualValue))
          }
        }
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

  def getVariables: Set[String] = args.flatMap(_.getFreeVariables).toSet

}
