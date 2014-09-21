package pl.appsilon.marek.sparkdatalog.ast.predicate

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog
import pl.appsilon.marek.sparkdatalog.eval.RelationInstance
import pl.appsilon.marek.sparkdatalog.{Fact, Relation, Valuation}
import pl.appsilon.marek.sparkdatalog.ast.value.{Value, ValueLiteral, ValueVar}

case class Predicate(tableName: String, args: Seq[Value]) {


  // TODO: co z R(x, x, .... ) ?
  def matchArgsGeneric(fact: Fact, initialValuation: Valuation): Option[Valuation] =
    fact.zip(args).foldLeft(Some(initialValuation): Option[Valuation]) {
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

  def matchArgs(fact: Fact): Option[Valuation] = matchArgsGeneric(fact, Valuation())

  def evaluate(relation: Relation): RDD[Valuation] = relation.data.flatMap(matchArgs)

  def evaluateLocally(relation: RelationInstance): Seq[Valuation] = relation.facts.flatMap(matchArgs)

  def fetchMatchingInstances(valuation: Valuation, relation: RelationInstance): Seq[Valuation] =
    relation.facts.flatMap(matchArgsGeneric(_, valuation))

  def getVariables: Set[String] = args.flatMap(_.getFreeVariables).toSet

}
