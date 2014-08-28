package s2g.ast.rule

import org.apache.spark.rdd.RDD
import s2g.eval.{Fact, PartialSolution}
import s2g.spark.{Relation, Valuation}

trait Head {
  def emitSolutionsSpark(valuations: RDD[Valuation]): Relation

  def emitSolutions(solutions: Set[PartialSolution]): Set[Fact]
  def args: Seq[String]
}
