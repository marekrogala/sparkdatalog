package pl.appsilon.marek.sparkdatalog.ast.rule

import pl.appsilon.marek.sparkdatalog.{Database, Relation}
import pl.appsilon.marek.sparkdatalog.ast.SemanticException
import pl.appsilon.marek.sparkdatalog.eval.{RelationInstance, StateShard, State, StaticEvaluationContext}

case class Rule(head: Head, ruleBodies: Seq[RuleBody]) {


  /** Semantic analysis */
  ruleBodies.foreach(body => {
    val notBoundHeadVariables = head.args.toSet -- body.outVariables
    if (notBoundHeadVariables.nonEmpty)
      throw new SemanticException("Unbound variable(s) in rule head: " +
        notBoundHeadVariables.mkString(", ") +
        " (head has free variables: " + head.args.mkString(", ") +
        "; but positive variables in body are: " + body.outVariables.mkString(", ") + ")")
  })


  def evaluate(context: StaticEvaluationContext, shard: StateShard): Option[Seq[(Long, RelationInstance)]] = {
    val generatedRelations = ruleBodies.map(_.findSolutions(context, shard)).map(head.emitSolutions)
   // println("evaluate shard = " + " \n\n\t -> " + generatedRelations.head.facts.size)
    generatedRelations.reduceOption(_.merge(_, context.aggregations.get(head.name))).map(_.toKeyValue)
  }

  /** Evaluation */
  def evaluateOnSpark(context: StaticEvaluationContext, fullDatabase: Database, deltaDatabase: Database): Option[Relation] = {
    val generatedRelations = ruleBodies.flatMap(_.findSolutionsSpark(context, fullDatabase, deltaDatabase).map(head.emitSolutionsSpark))
    generatedRelations.reduceOption(_ + _)
  }

}
