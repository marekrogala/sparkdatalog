package s2g.ast.rule

import org.apache.spark.rdd.RDD
import s2g.eval.{Fact, Instance, PartialSolution}
import s2g.spark.{Relation, Valuation}

case class Head(name: String, args: Seq[String]) {

  def bindArguments(solution: PartialSolution): Instance = Instance(args map (solution(_)))

  def emitSolutions(solutions: Set[PartialSolution]): Set[Fact] =
    solutions.map(bindArguments).map(Fact(name, _)).toSet

  def emitSolutionsSpark(valuations: RDD[Valuation]): Relation = {
    Relation(name, valuations.map(args.map(_)))
  }
}
