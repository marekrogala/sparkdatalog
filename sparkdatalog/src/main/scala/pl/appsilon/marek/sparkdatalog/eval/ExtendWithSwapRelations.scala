package pl.appsilon.marek.sparkdatalog.eval

import pl.appsilon.marek.sparkdatalog.ast.Program
import pl.appsilon.marek.sparkdatalog.ast.exp.EValue
import pl.appsilon.marek.sparkdatalog.ast.predicate.Predicate
import pl.appsilon.marek.sparkdatalog.ast.rule.{Head, Rule, RuleBody}
import pl.appsilon.marek.sparkdatalog.ast.subgoal.{GoalAssign, GoalPredicate, Subgoal}
import pl.appsilon.marek.sparkdatalog.ast.value.{Value, ValueLiteral, ValueVar}
import socialite.Absyn.ValueInt

object ExtendWithSwapRelations {
  val auxRuleNameTmpl = "_aux_%s_%s"
  val auxArgTemplate: String = "_aux_var_%d"

  def apply(program: Program): Program = {
    val (auxRulesNeeded, fixedRules) =
      (for((rule, ruleId) <- program.rules.zipWithIndex) yield {
        val relationalSubgoals = rule.body.relationalSubgoals.map(_.asInstanceOf[GoalPredicate])
        val lastRelationalSubgoal = relationalSubgoals(relationalSubgoals.lastIndexWhere(_.isRelational)) //TODO: no relat subgoal
        val computationLocation = lastRelationalSubgoal.location

        val (auxRulesNeeded, newRelationalSubgoals) = relationalSubgoals.zipWithIndex.map({ case (subgoal, subgoalId) =>
          if (subgoal.location != computationLocation) {
            val (sendTo, sendToName) = computationLocation match {
              case ValueVar(_) =>
                val index = subgoal.predicate.args.indexOf(computationLocation)
                if (index == -1) {
                  ???
                  //(computationLocation, "_broadcast") // TODO: broadcast!
                }
                else {
                  (ValueVar(auxArgTemplate.format(index)), "arg_%d".format(index))
                }
              case ValueLiteral(_, v) => (computationLocation, "literal_%s".format(v.toString))
            }

            val auxRelationName = auxRuleNameTmpl.format(subgoal.predicate.tableName, sendToName)
            val newPredicate = GoalPredicate(subgoal.predicate.copy(
              tableName = auxRelationName,
              args = computationLocation +: subgoal.predicate.args))

            (Some((subgoal.predicate, sendTo, sendToName)), newPredicate)
          } else {
            (None, subgoal)
          }
        }).unzip
        (auxRulesNeeded.flatten, rule.copy(body = RuleBody(rule.body.nonRelationalSubgoals ++ newRelationalSubgoals)))
      }).unzip

    val uniqueAuxRulesNeeded = auxRulesNeeded.flatten.toSet.toSeq
    val auxRules = uniqueAuxRulesNeeded.map {
      case (predicate, location, locationName) =>
        val auxRuleName = auxRuleNameTmpl.format(predicate.tableName, locationName)
        val auxArgs = 0.until(predicate.args.size).map(auxArgTemplate.format(_))
        val subgoals = Seq(GoalPredicate(Predicate(predicate.tableName, auxArgs.map(ValueVar))), GoalAssign("_location", EValue(location)))
        Rule(Head(auxRuleName, "_location" +: auxArgs), RuleBody(subgoals))
    }

    val newRules = auxRules ++ fixedRules

    val newProgram = program.copy(rules = newRules)
    println("new rules: " + newProgram.toString)

    newProgram
  }
}
