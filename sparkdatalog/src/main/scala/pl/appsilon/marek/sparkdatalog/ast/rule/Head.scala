package pl.appsilon.marek.sparkdatalog.ast.rule

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{Relation, Valuation}
import pl.appsilon.marek.sparkdatalog.eval.RelationInstance

case class Head(name: String, args: Seq[String]) {
  def emitSolutions(valuations: Seq[Valuation], variables: Map[String, Int]): RelationInstance = {
    val argIds = args.map(variables)
    RelationInstance(name, valuations.map(valuation => argIds.map(valuation(_))))
  }

  def emitSolutionsSpark(valuations: RDD[Valuation], variables: Map[String, Int]): Relation = {
    val argIds = args.map(variables)
    Relation(name, valuations.map(valuation => argIds.map(valuation(_))))
  }

  override def toString = name + args.mkString("(", ", ", ")")
}
