package s2g

import s2g.spark.{SparkDedicatedEvaluator, Database}

object SparkDatalog {

  def datalog(database: Database, programSource: String): Database = {
    SparkDedicatedEvaluator.evaluate(database, Parser(programSource))
  }

}
