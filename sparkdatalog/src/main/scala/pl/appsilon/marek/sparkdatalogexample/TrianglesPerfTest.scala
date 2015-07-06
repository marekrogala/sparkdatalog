package pl.appsilon.marek.sparkdatalogexample

import pl.appsilon.marek
import pl.appsilon.marek.sparkdatalogexample.ConnectedComponentsPerfTest._

import scala.io.Source

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog
import pl.appsilon.marek.sparkdatalog.{Database, Relation}

object TrianglesPerfTest extends PerformanceTest
{
  var edgesRdd: RDD[(Int, Int)] = _
  var database: Database = _

  def initialize(args: Seq[String]): Unit = {
//    val edges = Source.fromFile("/root/sparkdatalog/sparkdatalog/twitter.txt").getLines().map({
//      str =>
//        val s = str.split(" ")
//        val e = (s(0).toInt, s(1).toInt)
//        if(e._1 > e._2) e.swap else e
//    }).toSeq
//    edgesRdd = sc.parallelize(edges).repartition(sparkdatalog.numPartitions).cache()

    val edgesRdd = sc.textFile(root + "/twitter.txt").map({
      str =>
        val s = str.split(" ")
        val e = (s(0).toInt, s(1).toInt)
        if(e._1 > e._2) e.swap else e
    }).repartition(marek.sparkdatalog.numPartitions)

    println("Read " + edgesRdd.count() + " edges in " + edgesRdd.partitions.size + " partitions.")
//    database = Database(Relation.binary("Edge", edgesRdd))
  }

  override def runPregel(): Unit = {
    val canonicalEdges = edgesRdd.filter(Function.tupled(_ < _)).distinct().cache()
    val swappedEdgesRdd = canonicalEdges.map(_.swap)
    val pathOf2 = swappedEdgesRdd.join(canonicalEdges).coalesce(sparkdatalog.numPartitions).map( {
      case (y, (x, z)) => (x, (y, z))
    } )
    val triangle = pathOf2.join(canonicalEdges).coalesce(sparkdatalog.numPartitions)
      .filter({ case (x, ((y, z), zp)) => z == zp })
      .map({ case (x, ((y, z), zp)) => (x, y, z) })
    val count = triangle.count()
    println("Triangle count: " + count)
  }

  def show[T](rdd: RDD[T]) = println(rdd.collect().mkString(", "))

  override def runDatalog(): Unit = {
    val query = """ |declare Triangle(int v, int w, int u).
                    |declare Total(int a, int b aggregate Sum).
                    |Triangle(x, y, z) :- Edge(x, y), x < y, Edge (y, z), y < z, Edge(x, z).
                    |Total(a, c) :- Triangle(x, y, z), a = 1, c = 1. """.stripMargin
    val resultDatabase: Database = database.datalog(query)

    println("Triangles count: " + resultDatabase("Total").collect().mkString)
  }

  override def name: String = "Triangles"
}
