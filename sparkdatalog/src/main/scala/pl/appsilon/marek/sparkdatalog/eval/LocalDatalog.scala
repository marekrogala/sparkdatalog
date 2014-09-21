package pl.appsilon.marek.sparkdatalog.eval

import pl.appsilon.marek.sparkdatalog.Database
import pl.appsilon.marek.sparkdatalog.astbuilder.Parser

object LocalDatalog {

  def datalog(database: Database, programSource: String): Seq[(Long, StateShard)] = {
    LocalEvaluator.evaluate(database, Parser(programSource))
  }

}
