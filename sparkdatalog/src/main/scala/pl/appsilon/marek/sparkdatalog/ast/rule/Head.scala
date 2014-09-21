package pl.appsilon.marek.sparkdatalog.ast.rule

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{Relation, Valuation}
import pl.appsilon.marek.sparkdatalog.eval.RelationInstance

case class Head(name: String, args: Seq[String]) {
  def emitSolutions(valuations: Seq[Valuation]): RelationInstance =
    RelationInstance(name, valuations.map(args.map(_)))

  def emitSolutionsSpark(valuations: RDD[Valuation]): Relation = {
    Relation(name, valuations.map(args.map(_)))
  }
}
