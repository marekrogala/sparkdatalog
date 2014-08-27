package s2g.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest._

class DatabaseTest extends FlatSpec with Matchers {
  val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
  val sc = new SparkContext(conf)

  "RelationalDatabase" should "be created" in {
    val people = sc.parallelize(Seq((2, 15), (1, 20)))
    val parent = sc.parallelize(Seq((2, 15), (1, 20)))
    val database = Database(Seq(Relation("People", people)))

  }

}
