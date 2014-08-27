package s2g.learning

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest._


abstract class XFact[T] {
  def aggregate(other: T): Seq[T] = Seq(this.asInstanceOf[T], other)
}
case class A(name: String, age: Int) extends XFact[A]
case class B(name: String, age: Int) extends XFact[B]

class DatabaseSchema extends FlatSpec with Matchers {
  val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
  val sc = new SparkContext(conf)

  "A Stack" should "pop values in last-in-first-out order" in {
    val people1 = sc.parallelize(Seq[XFact[_]](A("Ziom", 15), A("Ziom2", 20)))
    val people2 = sc.parallelize(Seq[XFact[_]](B("Ziom", 15), B("Ziom2", 20)))
    people1.mapPartitionsWithIndex()
    println((people1 ++ people2).collect().mkString("\n"))

  }

}
