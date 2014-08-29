package s2g.spark

import org.scalatest._
import s2g.{Interpreter, Parser}
import s2g.util.SparkTestUtils

class SparkEvaluationTest extends SparkTestUtils with Matchers {

  sparkTest("perform multiple iterations") {
    //given
    val p = sc.parallelize(Seq(0))
    val database = Database(Seq(Relation.fromUnary("P", p)))

    val programSource =
      """
        |Q(x) :- P(x).
        |Q(x) :- Q(y), x < 7, x = y + 1.
      """.stripMargin

    //when
    val result = database.datalog(programSource)

    //then
    result.collect() should contain ("Q" -> (Set() ++ (0 to 6).map(Seq(_))))
  }

  sparkTest("correctly compute SSSP without aggregation") {
    //given
    val edge = sc.parallelize(Seq((1, 2, 1), (2, 3, 1)))
    val database = Database(Seq(Relation.fromTuple3("Edge", edge)))

    val programSource =
      """
        |Path(x, d) :- Edge(1, x, d).
        |Path(x, d) :- Path(y, da), Edge(y, x, db), d = da + db.
      """.stripMargin

    //when
    val result = database.datalog(programSource)

    //then
    result.collect() should be (Map("Edge" -> Set(Seq(1, 2, 1), Seq(2, 3, 1)), "Path" -> Set(Seq(2, 1), Seq(3, 2))))
  }

}
