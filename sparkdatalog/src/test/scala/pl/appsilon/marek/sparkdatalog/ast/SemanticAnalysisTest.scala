package pl.appsilon.marek.sparkdatalog.ast

import org.scalatest._
import pl.appsilon.marek.sparkdatalog.astbuilder.Parser

class SemanticAnalysisTest extends FlatSpec with Matchers {

  it must "throw if there is a rule with no relational subgoals " in {
    val programSource = "Q(x) :- x = 2."
    a [SemanticException] should be thrownBy {
      Parser(programSource)
    }
  }

  it must "throw if there is an unsafe rule" in {
    val programSource = "Q(x, y) :- x = 2."
    a [SemanticException] should be thrownBy {
      Parser(programSource)
    }
  }

}
