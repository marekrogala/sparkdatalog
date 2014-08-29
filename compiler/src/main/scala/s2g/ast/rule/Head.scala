package s2g.ast.rule

import org.apache.spark.rdd.RDD
import s2g.spark.{Relation, Valuation}

case class Head(name: String, args: Seq[String]) {
  def emitSolutionsSpark(valuations: RDD[Valuation]): Relation = {
    Relation(name, valuations.map(args.map(_)))
  }
}
