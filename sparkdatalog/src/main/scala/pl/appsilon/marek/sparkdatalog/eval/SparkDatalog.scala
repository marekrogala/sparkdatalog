package pl.appsilon.marek.sparkdatalog.eval

import pl.appsilon.marek.sparkdatalog.Database
import pl.appsilon.marek.sparkdatalog.ast.Program
import pl.appsilon.marek.sparkdatalog.astbuilder.Parser

object SparkDatalog {

  def datalog(database: Database, programSource: String): Database = {
    //SparkEvaluator.evaluate(database, Parser(programSource))
    val program: Program = ExtendWithSwapRelations(Parser(programSource))
    println("Program:\n" + program.toString)
    SparkShardedEvaluator.evaluate(database, program)
  }

}
