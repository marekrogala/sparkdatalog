package s2g.learning

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.scalatest._


case class Person(name: String, age: Int)
class SQLSchema extends FlatSpec with Matchers {
  val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
  val sc = new SparkContext(conf)

  it should "execute simple query" in {
    val sqlContext = new SQLContext(sc)



    // Create an RDD of Person objects and register it as a table.
    val people = sqlContext.createSchemaRDD(sc.parallelize(Seq(Person("Ziom", 15), Person("Ziom2", 20))))
    people.registerAsTable("people")
    people.printSchema()

    // SQL statements can be run by using the sql methods provided by sqlContext.
    val teenagers = sqlContext.sql("SELECT name FROM people WHERE age >= 13 AND age <= 19")

    // The results of SQL queries are SchemaRDDs and support all the normal RDD operations.
    // The columns of a row in the result can be accessed by ordinal.
    teenagers.map(t => "Name: " + t(0)).collect().foreach(println)
  }

}
