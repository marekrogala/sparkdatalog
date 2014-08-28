package s2g.spark

import s2g.ast.Program
import s2g.ast.rule.Rule

class SparkDedicatedEvaluator {

  private def makeIteration(
      staticContext: StaticEvaluationContext,
      rules: Iterable[Rule],
      fullDatabase: Database,
      deltaDatabase: Database): (Database, Database) = {
    val generatedRelations = rules.map(_.evaluateOnSpark(staticContext, fullDatabase, deltaDatabase))
    val generatedDatabase = Database() ++ generatedRelations
    val newFullDatabase = fullDatabase + generatedDatabase
    val newDeltaDatabase = generatedDatabase // TODO: newFullDatabase - fullDatabase
    (newFullDatabase, newDeltaDatabase)
  }

  def evaluate(database: Database, program: Program): Database = {

    var iteration = 0
    var fullDatabase = database
    var deltaDatabase = Database()

    do {
      println("Iteration " + (iteration+1))
      val (newFullDatabase, newDeltaDatabase) =
        makeIteration(StaticEvaluationContext(program.declarations), program.rules, fullDatabase, deltaDatabase)
      fullDatabase = newFullDatabase
      deltaDatabase = newDeltaDatabase
      println("after: \n\n" + database.toString)
      iteration += 1
    } while (iteration < 3) // TODO: check fix-point
    println("made " + iteration + " iters\n\n")

    fullDatabase
  }
}
