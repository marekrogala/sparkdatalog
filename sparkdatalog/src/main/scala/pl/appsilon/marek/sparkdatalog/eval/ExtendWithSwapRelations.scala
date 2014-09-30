package pl.appsilon.marek.sparkdatalog.eval

import pl.appsilon.marek.sparkdatalog.ast.Program
import pl.appsilon.marek.sparkdatalog.ast.predicate.Predicate
import pl.appsilon.marek.sparkdatalog.ast.rule.{Head, Rule, RuleBody}
import pl.appsilon.marek.sparkdatalog.ast.subgoal.{GoalPredicate, Subgoal}
import pl.appsilon.marek.sparkdatalog.ast.value.ValueVar

object ExtendWithSwapRelations {
  def apply(program: Program): Program = {
//    val newRules =
//      for((rule, ruleId) <- program.rules.zipWithIndex) yield {
//          val subgoals = rule.body.dynamicSubgoals.map(_._1)
//          val constantSubgoals: Seq[Subgoal] = rule.body.constantSubgoals.map(_._1)
//
//          // Find subsequences that are placed on one location.
//          // TODO: deal with e.x. P(1, x)
//
//          if(subgoals.size > 0) {
//            val (lastLocationVal, subsequencesWithoutLast, lastSubsequence) =
//              subgoals.foldLeft((subgoals(0).getLocation.get, Seq(), constantSubgoals): (String, Seq[(String, Seq[Subgoal])], Seq[Subgoal])) {
//                case ((lastLocation, subseqs, current), subgoal) =>
//                  subgoal.getLocation match {
//                    case None => (lastLocation, subseqs, current :+ subgoal)
//                    case Some(`lastLocation`) => (lastLocation, subseqs, current :+ subgoal)
//                    case Some(newLocation) => (newLocation, subseqs :+(lastLocation, current), Seq(subgoal))
//                  }
//              }
//            val subsequences = (lastLocationVal, lastSubsequence) +: subsequencesWithoutLast
//
//            val subsequencesWithoutLastWithDest: Seq[(Seq[Subgoal], String)] = subsequences.dropRight(1).map(_._2).zip(subsequences.tail.map(_._1))
//            val (predicates, auxRules) = subsequencesWithoutLastWithDest.zipWithIndex.map({
//              case ((subgoalsSeq, dest), auxRuleId) =>
//                val auxRuleName = "_aux_%s_%d_%d_".format(rule.head.name, ruleId, auxRuleId)
//                val auxRuleBody = RuleBody(subgoalsSeq)
//                val auxRuleArgs = dest +: auxRuleBody.outVariables.toSeq
//                (Predicate(auxRuleName, auxRuleArgs.map(ValueVar)), Rule(Head(auxRuleName, auxRuleArgs), auxRuleBody))
//            }).unzip
//            val mainRuleBody = RuleBody(predicates.map(GoalPredicate) ++ lastSubsequence)
//            val mainRule = Rule(rule.head, mainRuleBody)
//            val newRulesForRule = mainRule +: auxRules
//            newRulesForRule
//          } else {
//            val newRulesForRule = Seq(rule)
//            newRulesForRule
//          }
//        }
//
//    println("new rules: " + newRules.toString)
//    program.copy(rules = newRules.flatten)
    program
  }
}
