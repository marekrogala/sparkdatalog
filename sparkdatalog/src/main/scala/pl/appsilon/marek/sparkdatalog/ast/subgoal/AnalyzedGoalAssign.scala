package pl.appsilon.marek.sparkdatalog.ast.subgoal

import pl.appsilon.marek.sparkdatalog.Valuation
import pl.appsilon.marek.sparkdatalog.ast.exp.AnalyzedExp
import pl.appsilon.marek.sparkdatalog.eval.RelationInstance

case class AnalyzedGoalAssign(lValueVariable: Int, exp: AnalyzedExp) extends AnalyzedSubgoal {

  override def evaluateStatic(valuation: Valuation): Option[Valuation] = {
    val value = exp.evaluate(valuation)
    valuation(lValueVariable) match {
      case None =>
        valuation.update(lValueVariable, Some(value))
        Some(valuation)
      case Some(`value`) => Some(valuation)
      case _ => None
    }
  }

  override def solveOn(valuation: Valuation, relations: Map[String, RelationInstance]): Seq[Valuation] = evaluateStatic(valuation).toSeq

  override def solveOnSet(valuations: Seq[Valuation], relations: Map[String, RelationInstance]): Seq[Valuation] =
    valuations.map(evaluateStatic).flatten // TODO maybe some more effective way?

  override def getLocation: Option[Int] = None
}
