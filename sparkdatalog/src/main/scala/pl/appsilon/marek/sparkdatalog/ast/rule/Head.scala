package pl.appsilon.marek.sparkdatalog.ast.rule

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{Relation, RelationRepr, FactW, Valuation}
import pl.appsilon.marek.sparkdatalog.eval.{StaticEvaluationContext, RelationInstance}

case class Head(name: String, args: Seq[String]) {
  def emitSolutions(valuations: Seq[Valuation], variables: Map[String, Int]): RelationInstance = {
    val argIds = args.map(variables)
    RelationInstance(name, valuations.map(valuation => argIds.map(valuation(_))))
  }

  def emitSolutionsSpark(valuations: RDD[Valuation], context: StaticEvaluationContext, variables: Map[String, Int]): RelationRepr = {
    val argIds = args.map(variables)
    RelationRepr(Relation(name, valuations.map(valuation => argIds.map(valuation(_)))), context.aggregations.get(name))
  }

  override def toString = name + args.mkString("(", ", ", ")")
}
