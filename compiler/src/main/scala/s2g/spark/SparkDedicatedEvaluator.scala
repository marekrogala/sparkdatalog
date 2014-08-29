package s2g.spark

import s2g.ast.Program
import s2g.ast.rule.Rule

object SparkDedicatedEvaluator {

  private def makeIteration(
      staticContext: StaticEvaluationContext,
      rules: Iterable[Rule],
      fullDatabase: Database,
      deltaDatabase: Database): (Database, Database) = {
    val generatedRelations = rules.map(_.evaluateOnSpark(staticContext, fullDatabase, deltaDatabase)).flatten
    val newFullDatabase = fullDatabase.mergeIn(generatedRelations, staticContext.aggregations)
    (newFullDatabase, newFullDatabase.subtract(fullDatabase))
  }

  def evaluate(database: Database, program: Program): Database = {

    var iteration = 0
    var fullDatabase = database
    var deltaDatabase = Database()

    do {
      println("Iteration " + (iteration+1))
      val (newFullDatabase, newDeltaDatabase) =
        makeIteration(StaticEvaluationContext(program.aggregations), program.rules, fullDatabase, deltaDatabase)
      fullDatabase = newFullDatabase
      deltaDatabase = newDeltaDatabase
      println("after: \n\n" + fullDatabase.toString)
      iteration += 1
    } while (!deltaDatabase.empty)
    println("made " + iteration + " iters\n\n")

    fullDatabase
  }
}
