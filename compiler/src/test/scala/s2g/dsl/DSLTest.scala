import org.scalatest._

import s2g.dsl.DatalogDSL._

class DSLTest extends FlatSpec with Matchers {

  "A Stack" should "pop values in last-in-first-out order" in {
    val A = Relation("A")
    val x = 'A :- 'B
  }

}
