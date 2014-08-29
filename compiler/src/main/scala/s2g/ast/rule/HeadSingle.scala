package s2g.ast.rule

import org.apache.spark.rdd.RDD
import s2g.eval.{Fact, Instance, PartialSolution}
import s2g.spark.{Relation, Valuation}

case class HeadSingle(name: String, args: Seq[String]) extends Head {

  def bindArguments(solution: PartialSolution): Instance = Instance(args map (solution(_)))

  override def emitSolutions(solutions: Set[PartialSolution]): Set[Fact] =
    solutions.map(bindArguments).map(Fact(name, _)).toSet

  override def emitSolutionsSpark(valuations: RDD[Valuation]): Relation = {
    println("emitting [" + valuations.collect().mkString("; ") + "] to " + name + "(" + args.mkString(", ") + ")")
    Relation(name, valuations.map(args.map(_)))
  }
}
