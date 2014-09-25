package pl.appsilon.marek.sparkdatalog.ast.rule

import pl.appsilon.marek.sparkdatalog.util.Timed
import pl.appsilon.marek.sparkdatalog.{Valuation, Database, Relation}
import pl.appsilon.marek.sparkdatalog.ast.SemanticException
import pl.appsilon.marek.sparkdatalog.eval.{RelationInstance, StateShard, StaticEvaluationContext}

case class Rule(head: Head, body: RuleBody) {


  /** Semantic analysis */
  val notBoundHeadVariables = head.args.toSet -- body.outVariables
  if (notBoundHeadVariables.nonEmpty)
    throw new SemanticException("Unbound variable(s) in rule head: " +
      notBoundHeadVariables.mkString(", ") +
      " (head has free variables: " + head.args.mkString(", ") +
      "; but positive variables in body are: " + body.outVariables.mkString(", ") + ")")



  def evaluate(context: StaticEvaluationContext, shard: StateShard): Seq[(Long, RelationInstance)] = {
    val solutions: Seq[Valuation] = Timed("findSolutions_"+head, () => body.findSolutions(context, shard))
    val generatedRelations = Timed("emitSolutions_"+head, () =>head.emitSolutions(solutions))
   // println("evaluate shard = " + " \n\n\t -> " + generatedRelations.head.facts.size)
    generatedRelations.toKeyValue
  }

  /** Evaluation */
  def evaluateOnSpark(context: StaticEvaluationContext, fullDatabase: Database, deltaDatabase: Database): Option[Relation] = {
    val generatedRelations = body.findSolutionsSpark(context, fullDatabase, deltaDatabase).map(head.emitSolutionsSpark)
    generatedRelations
  }

}
