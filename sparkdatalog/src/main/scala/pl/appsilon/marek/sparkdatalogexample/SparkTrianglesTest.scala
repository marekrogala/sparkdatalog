package pl.appsilon.marek.sparkdatalogexample

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.VertexId
import org.apache.spark.graphx.util.GraphGenerators
import pl.appsilon.marek.sparkdatalog.{Relation, Database}
import pl.appsilon.marek.sparkdatalog.util.Timed

import scala.util.Random

object SparkTrianglesTest
{

  def main(args: Array[String]): Unit = {
    // Standard Spark setup
    val conf = new SparkConf().setAppName("Spark Datalog SSSP Computation").setMaster("local[4]")
    val sc = new SparkContext(conf)
    sc.setCheckpointDir("checkpoint")
    //sc.addJar("target/scala-2.10/sparkdatalog_2.10-1.0.0.jar")

    val diam: Int = 6 //args(1).toInt
    // Generate random graph with random edge lengths
    //val graph = GraphGenerators.gridGraph(sc, diam, diam)
    //val graph = GraphGenerators.logNormalGraph(sc, numVertices = diam)
    //val edgesRdd = graph.edges.map(edge => (edge.srcId.toInt, edge.dstId.toInt, Random.nextInt(1000)))
    val exampleEdges = for (x <- 1 to diam; y <- 1 to diam) yield x -> y
    val edgesRdd = sc.parallelize(exampleEdges)

    println("computing Comp, %d edges".format(edgesRdd.count()))


    // Compute shortests paths from the source node using Spark Datalog API:

    val database = Database(
      Relation.binary("Edge", edgesRdd))


    Timed("ALL", () => {
      val query = """
                    |declare PathOf2(int v, int w).
                    |declare Triangle(int v, int w, int u).
                    |declare Total(int a, int b aggregate Sum).
                    |PathOf2(x, y, z) :- Edge(x, y), x < y, Edge (y, z), y < z.
                    |Triangle(x, y, z) :- PathOf2(x, y, z), Edge(x, z).
                    |Total(a, c) :- Triangle(x, y, z), a = 1, c = 1.
                  """.stripMargin


      val resultDatabase = database.datalog(query)


      val resultTriangleRdd = resultDatabase("Triangle")
      val resultTotalRdd = resultDatabase("Total")
      println("triangles: " + resultTriangleRdd.collect().map("Triangle(" + _.mkString(", ") + ")").mkString("\n"))
      println("total: " + resultTotalRdd.collect().map("Total(" + _.mkString(", ") + ")").mkString("\n"))
    })
  }

}
