package s2g.ast.subgoal

import s2g.eval.{PartialSolution, Context}

object SubgoalsTopologicalSort {

  def apply(subgoals: Seq[Subgoal], environment: PartialSolution): Seq[Subgoal] = {
    val context = Context(environment, PartialSolution())
    val subgoalsWithVariables = for(subgoal <- subgoals) yield (subgoal, subgoal.getInputs(context), subgoal.getOutputs(context))
    subgoals
  }
}
