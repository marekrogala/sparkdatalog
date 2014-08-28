package s2g.spark

import org.apache.spark.{SparkContext, SparkConf}
import org.scalatest._
import s2g.{Parser, Interpreter}

class SparkEvaluationTest extends FlatSpec with Matchers {
  val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
  val sc = new SparkContext(conf)

  it should "correctly compute SSSP" in {
    val edge = sc.parallelize(Seq((1, 2, 1), (2, 3, 1)))
    val database = Database(Seq(Relation.fromTuple3("Edge", edge)))

    val programSource =
      """
        |Path(x, d) :- Edge(1, x, d).
        |Path(x, d) :- Path(y, da), Edge(y, x, db), d = da + db.
      """.stripMargin
    val interpreter = new Interpreter(null)
    val program = Parser(programSource).get

    val evaluator = new SparkDedicatedEvaluator
    val result = evaluator.evaluate(database, program)
    println(result.relations.map(_.toString()))
  }

}
