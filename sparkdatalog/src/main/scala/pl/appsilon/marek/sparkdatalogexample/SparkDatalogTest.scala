package pl.appsilon.marek.sparkdatalogexample

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import pl.appsilon.marek.sparkdatalog._

object SparkDatalogTest
{

  val parentInput = Seq((1, 2), (1, 3), (2, 4), (2, 5), (3, 6), (3, 7), (6, 8), (6, 9), (8, 10), (8, 11), (9, 12), (9, 13)) //this is the edge relation, ignore the name
  val oddInput = Seq(1,3,5,7,9,11,13)
  val evenInput = Seq(2,4,6,8,10,12)
  val input = Seq(1,2,3,4,5,6,7,8,9,10,11,12,13)

  def pathSec72(sc: SparkContext): Unit = {
    val edgeRdd = sc.parallelize(parentInput)
    val inputRdd = sc.parallelize(input)
    val oddInputRdd = sc.parallelize(oddInput)
    val evenInputRdd = sc.parallelize(evenInput)
    val database = Database(
      Relation.binary("E", edgeRdd),
      Relation.unary("Input", inputRdd),
      Relation.unary("Odd", oddInputRdd),
      Relation.unary("Even", evenInputRdd))

    //E is distributed
    val query = """
                  |declare T(int x, int y).
                  |T11(x,y) :- E(x,y), Odd(x).
                  |T11(x,y) :- T11(x,z), E(z,y),Odd(z).
                  |T21(x,y) :- T22(x,z), E(z,y),Odd(z).
                  |T11(x,y) :- T21(x,y).
                  |T22(x,y) :- E(x,y), Even(x).
                  |T22(x,y) :- T22(x,z), E(z,y),Even(z).
                  |T12(x,y) :- T11(x,z), E(z,y),Even(z).
                  |T22(x,y) :- T12(x,y).
                  |T(x,y) :- T11(x,y).
                  |T(x,y) :- T22(x,y).
                """.stripMargin
    val resultDatabase = database.datalog(query)

    // -----------------------------------------------------------------------------------

    println("Result: " + resultDatabase.relations("T"))

    // We can now save the paths RDD to distributed storage or perform further computations on it.
    // We can of course also print it to stdout:
    val resultSGRdd: RDD[Fact] = resultDatabase("T")
    println("Computed %d results.".format(resultSGRdd.count()))
  }

  def main(args: Array[String]): Unit = {
    // Standard Spark setup
    val conf = new SparkConf().setAppName("Spark Datalog Test Computation").setMaster("local[4]")
    val sc = new SparkContext(conf)
    sc.setCheckpointDir("checkpoint")
    pathSec72(sc)

  }
}
