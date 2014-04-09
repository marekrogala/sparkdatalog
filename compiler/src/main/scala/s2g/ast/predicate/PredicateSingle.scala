package s2g.ast.predicate

import s2g.eval._
import s2g.ast.value.Value
import s2g.eval.Context
import s2g.eval.Pattern
import s2g.eval.PartialSolution

case class PredicateSingle(tableName: String, args: Seq[Value]) extends Predicate {

  def buildPattern(context: Context): Pattern = Pattern(args map (_.tryToEvaluate(context)))

  override def fetchMatchingInstances(
      partialSolution: PartialSolution,
      tableStates: TableStates,
      environment: PartialSolution): Set[PartialSolution] =
    tableStates.find(tableName, buildPattern(Context(environment, partialSolution))).map(_ ++ partialSolution)

  override def getOutputs(context: Context): Set[String] = args.flatMap(_.tryToEvaluate(context).getFreeVariables).toSet

  override def getInputs(context: Context): Set[String] = Set()
}
