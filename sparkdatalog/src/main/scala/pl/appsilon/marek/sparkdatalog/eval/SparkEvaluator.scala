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
    val newFullDatabase = fullDatabase.mergeIn(generatedRelations, staticContext.aggregations).cache().materialize()
    val newDeltaDatabase = newFullDatabase.subtract(fullDatabase).cache().materialize()
    (newFullDatabase, newDeltaDatabase)
  }

  def evaluate(database: Database, program: Program): Database = {
    var iteration = 0
    database.cache()
    var fullDatabase = database
    var deltaDatabase = database

    do {
      println("Making iteration " + iteration)

      //println("pr1 = " + fullDatabase.sc.getPersistentRDDs.size)


      //println("pr2 = " + fullDatabase.sc.getPersistentRDDs.size)

      val (newFullDatabase, newDeltaDatabase) = makeIteration(
        StaticEvaluationContext(program.aggregations), program.rules, fullDatabase, deltaDatabase)

      // fullDatabase.unpersist(blocking = false)
      // deltaDatabase.unpersist(blocking = false)

      fullDatabase = newFullDatabase
      deltaDatabase = newDeltaDatabase
      iteration += 1

      //println("pr4 = " + fullDatabase.sc.getPersistentRDDs.size)
      println(fullDatabase("Path").collect().map("Path(" + _.mkString(", ") + ")").mkString("\n"))
      //println("pr5 = " + fullDatabase.sc.getPersistentRDDs.size)
    } while (!deltaDatabase.empty)

    fullDatabase
  }
}
