package s2g.ast.predicate

import s2g.eval.{TableStates, Context, PartialSolution}

trait Predicate {
  def fetchMatchingInstances(partialSolution: PartialSolution,
                             tableStates: TableStates,
                             environment: PartialSolution): Set[PartialSolution]
  def getInputs(context: Context): Set[String]
  def getOutputs(context: Context): Set[String]
}
