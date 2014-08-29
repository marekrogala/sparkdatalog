package s2g.ast

import org.scalatest._
import s2g.Parser
import s2g.eval.SemanticException
import s2g.spark.Database
import s2g.util.SparkTestUtils

class SemanticAnalysisTest extends FlatSpec with Matchers {

  it must "throw if there is a rule with no relational subgoals " in {
    val programSource = "Q(x) :- x = 2."
    a [SemanticException] should be thrownBy {
      Parser(programSource)
    }
  }

}
