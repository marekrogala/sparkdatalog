package pl.appsilon.marek.sparkdatalog.ast.predicate

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{Relation, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.value.{Value, ValueLiteral, ValueVar}

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

  def getVariables: Set[String] = args.flatMap(_.getFreeVariables).toSet

}
