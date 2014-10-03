package pl.appsilon.marek.sparkdatalog.ast.subgoal

import pl.appsilon.marek.sparkdatalog.ast.SemanticException

import scala.annotation.tailrec

object NonrelationalSubgoalsTopologicalSort {

  @tailrec
  private def iterate(
      processed: Seq[Subgoal], relational: Seq[Subgoal], nonrelational: Seq[Subgoal], available: Set[String])
      : Seq[Subgoal] =
    if (relational.isEmpty && nonrelational.isEmpty) {
      processed
    } else {
      val (nrNewEvaluable, nrNotEvaluable) = nonrelational partition(_.getInVariables subsetOf available)
      if (nrNewEvaluable.isEmpty) {
        val (rNewEvaluable, rNotEvaluable) = relational partition(_.getInVariables subsetOf available)
        if(rNewEvaluable.isEmpty) {
          assert(false)
          Seq()
        } else {
          val newBoundVariables = rNewEvaluable.flatMap(_.getOutVariables).toSet
          iterate(processed ++ rNewEvaluable, rNotEvaluable, nonrelational, available ++ newBoundVariables)
        }
      } else {
        val newBoundVariables = nrNewEvaluable.flatMap(_.getOutVariables).toSet
        iterate(processed ++ nrNewEvaluable, relational, nrNotEvaluable, available ++ newBoundVariables)
      }
    }

  def apply(relational: Seq[Subgoal], nonrelational: Seq[Subgoal]): Seq[Subgoal] = {
    iterate(Seq(), relational, nonrelational, Set())
  }

}
