package pl.appsilon.marek.sparkdatalog.eval

import org.scalatest._
import pl.appsilon.marek.sparkdatalog.{Database, Relation}
import pl.appsilon.marek.sparkdatalog.util.SparkTestUtils

class SparkEvaluationTest extends SparkTestUtils with Matchers {

  sparkTest("perform multiple iterations") {
    //given
    val p = sc.parallelize(Seq(0))
    val database = Database(Relation.unary("P", p))

    val programSource =
      """
        |Q(x) :- P(x).
        |Q(x) :- Q(y), x < 4, x = y + 1.
      """.stripMargin

    //when
    val result = database.datalog(programSource)

    //then
    result.collect() should contain ("Q" -> (Set() ++ (0 to 3).map(Seq(_))))
  }

  sparkTest("correctly compute SSSP without aggregation") {
    //given
    val edge = sc.parallelize(Seq((1, 2, 1), (2, 3, 1)))
    val database = Database(Relation.ternary("Edge", edge))

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

  sparkTest("empty program is identity") {
    //given
    val edge = sc.parallelize(Seq((1, 2, 1), (2, 3, 1)))
    val database = Database(Relation.ternary("Edge", edge))

    //when
    val result = database.datalog("")

    //then
    result.collect() should be (Map("Edge" -> Set(Seq(1, 2, 1), Seq(2, 3, 1))))
  }


  sparkTest("correctly compute SSSP with aggregation") {
    //given
    val edge = sc.parallelize(Seq((1, 2, 1), (1, 3, 5), (2, 3, 1)))
    val database = Database(Relation.ternary("Edge", edge))

    val programSource =
      """
        |declare Path(int v, int dist aggregate Min).
        |Path(x, d) :- Edge(1, x, d).
        |Path(x, d) :- Path(y, da), Edge(y, x, db), d = da + db.
      """.stripMargin

    //when
    val result = database.datalog(programSource)

    //then
    result.collect() should contain ("Path" -> Set(Seq(2, 1), Seq(3, 2)))
  }

}
