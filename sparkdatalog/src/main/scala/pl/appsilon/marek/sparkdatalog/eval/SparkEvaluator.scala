package pl.appsilon.marek.sparkdatalog.eval

import pl.appsilon.marek.sparkdatalog.Database
import pl.appsilon.marek.sparkdatalog.ast.Program
import pl.appsilon.marek.sparkdatalog.ast.rule.Rule

object SparkEvaluator {

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

    database.cache()

    var iteration = 0
    var fullDatabase = database
    var deltaDatabase = database

    do {
      val (newFullDatabase, newDeltaDatabase) = makeIteration(
        StaticEvaluationContext(program.aggregations), program.rules, fullDatabase.cache(), deltaDatabase.cache())

      fullDatabase.unpersist(blocking = false)
      deltaDatabase.unpersist(blocking = false)

      fullDatabase = newFullDatabase
      deltaDatabase = newDeltaDatabase
      iteration += 1
    } while (!deltaDatabase.empty)

    fullDatabase
  }
}
