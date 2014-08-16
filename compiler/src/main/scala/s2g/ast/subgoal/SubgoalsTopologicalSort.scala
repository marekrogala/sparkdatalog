package s2g.ast.subgoal

import s2g.eval.{SemanticException, PartialSolution, Context}
import scala.annotation.tailrec

object SubgoalsTopologicalSort {

  @tailrec
  private def iterate(
      processed: Seq[Subgoal], toBeProcessed: Seq[(Subgoal, Set[String], Set[String])], available: Set[String])
      : Seq[Subgoal] =
    if (toBeProcessed.isEmpty) {
      processed
    } else {
      val (newEvaluable, notEvaluable) = toBeProcessed partition { case (_, inputs, _) => inputs subsetOf available }
      if (newEvaluable.isEmpty) {
        throw new SemanticException("Unbound variable(s) " + notEvaluable.mkString("'", "', '", "'"))
      } else {
        val newBoundVariables = newEvaluable.flatMap(_._3).toSet
        val newEvaluatedSubgoals = newEvaluable map (_._1)
        iterate(processed ++ newEvaluatedSubgoals, notEvaluable, available ++ newBoundVariables)
      }
    }


  def apply(subgoals: Seq[Subgoal], environment: PartialSolution): Seq[Subgoal] = {
    val context = Context(environment, PartialSolution())
    val subgoalsWithVariables = for(subgoal <- subgoals)
      yield (subgoal, subgoal.getInputs(context), subgoal.getOutputs(context))
    iterate(Seq(), subgoalsWithVariables, Set())
  }


}
