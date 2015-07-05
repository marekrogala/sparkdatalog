package pl.appsilon.marek.sparkdatalog.ast.subgoal

import pl.appsilon.marek.sparkdatalog.ast.SemanticException

import scala.annotation.tailrec

object NonrelationalSubgoalsTopologicalSort {

  @tailrec
  private def iterate(
      processed: Seq[(Subgoal, Boolean)], relational: Seq[(Subgoal, Boolean)], nonrelational: Seq[(Subgoal, Boolean)], available: Set[String])
      : Seq[(Subgoal, Boolean)] =
    if (relational.isEmpty && nonrelational.isEmpty) {
      processed
    } else {
      val (nrNewEvaluable, nrNotEvaluable) = nonrelational partition(_._1.getInVariables subsetOf available)
      if (nrNewEvaluable.isEmpty) {
        val (rNewEvaluable, rNotEvaluable) = relational partition(_._1.getInVariables subsetOf available)
        if(rNewEvaluable.isEmpty) {
          assert(false)
          Seq()
        } else {
          val newBoundVariables = rNewEvaluable.flatMap(_._1.getOutVariables).toSet
          iterate(processed ++ rNewEvaluable, rNotEvaluable, nonrelational, available ++ newBoundVariables)
        }
      } else {
        val newBoundVariables = nrNewEvaluable.flatMap(_._1.getOutVariables).toSet
        iterate(processed ++ nrNewEvaluable, relational, nrNotEvaluable, available ++ newBoundVariables)
      }
    }

  def apply(relational: Seq[(Subgoal, Boolean)], nonrelational: Seq[(Subgoal, Boolean)]): Seq[(Subgoal, Boolean)] = {
    iterate(Seq(), relational, nonrelational, Set())
  }

}
