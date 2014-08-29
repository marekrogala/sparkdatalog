package pl.appsilon.marek.sparkdatalog.ast.rule

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{Relation, Valuation}

case class Head(name: String, args: Seq[String]) {
  def emitSolutionsSpark(valuations: RDD[Valuation]): Relation = {
    Relation(name, valuations.map(args.map(_)))
  }
}
