package pl.appsilon.marek.sparkdatalog.eval

import pl.appsilon.marek.sparkdatalog.Database
import pl.appsilon.marek.sparkdatalog.astbuilder.Parser

object SparkDatalog {

  def datalog(database: Database, programSource: String): Database = {
    //SparkEvaluator.evaluate(database, Parser(programSource))
    SparkShardedEvaluator.evaluate(database, ExtendWithSwapRelations(Parser(programSource)))
  }

}
