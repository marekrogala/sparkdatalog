package pl.appsilon.marek.sparkdatalogexample

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.VertexId
import org.apache.spark.graphx.util.GraphGenerators
import pl.appsilon.marek.sparkdatalog.{Relation, Database}
import pl.appsilon.marek.sparkdatalog.util.Timed

import scala.util.Random

object SparkConnectedComponentsTest
{

  def main(args: Array[String]): Unit = {
    // Standard Spark setup
    val conf = new SparkConf().setAppName("Spark Datalog SSSP Computation").setMaster("local[4]")
    val sc = new SparkContext(conf)
    sc.setCheckpointDir("checkpoint")
    //sc.addJar("target/scala-2.10/sparkdatalog_2.10-1.0.0.jar")

    val diam: Int = 3 //args(1).toInt
    // Generate random graph with random edge lengths
    //val graph = GraphGenerators.gridGraph(sc, diam, diam)
    val graph = GraphGenerators.logNormalGraph(sc, numVertices = diam)
    val edgesRdd = graph.edges.map(edge => (edge.srcId.toInt, edge.dstId.toInt, Random.nextInt(1000)))
    val verticesRdd = sc.parallelize(0 until diam)

    println("computing Comp, %d edges".format(edgesRdd.count()))
    println("edges: " + graph.edges.collect().mkString(", "))

    // Compute shortests paths from the source node using Spark Datalog API:

    val database = Database(
      Relation.ternary("Edge", edgesRdd),
      Relation.unary("Node", verticesRdd))


    Timed("ALL", () => {
      val query = """
                    |declare Component(int n, int component aggregate Min).
                    |declare ComponentId(int n).
                    |Component(n, i) :- Node(n), i = n.
                    |Component(n, i) :- Component(p, i), Edge(p, n).
                    |ComponentId(id) :- Component(x, id).
                  """.stripMargin


      val resultDatabase = database.datalog(query)


      val resultComponentRdd = resultDatabase("Component")
      val resultComponentIdRdd = resultDatabase("ComponentId")
      println(resultComponentRdd.collect().map("Component(" + _.mkString(", ") + ")").mkString("\n"))
      println(resultComponentIdRdd.collect().map("ComponentId(" + _.mkString(", ") + ")").mkString("\n"))
    })
  }

}
