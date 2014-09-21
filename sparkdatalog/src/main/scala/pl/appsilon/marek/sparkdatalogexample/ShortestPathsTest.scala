package pl.appsilon.marek.sparkdatalogexample

import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{Relation, Database}

import scala.util.Random

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.util.GraphGenerators

object ShortestPathsTest
{
  val sourceNode = Seq(0)

  def main(args: Array[String]): Unit = {
    // Standard Spark setup
    val conf = new SparkConf().setAppName("Spark Datalog SSSP Computation").setMaster(args(0))
    val sc = new SparkContext(conf)

    // Generate random graph with random edge lengths
    val graph = GraphGenerators.logNormalGraph(sc, numVertices = args(1).toInt)
    val edgesRdd = graph.edges.map(edge => (edge.srcId.toInt, edge.dstId.toInt, Random.nextInt(1000)))
    val sourceRdd = sc.parallelize(sourceNode)

    println("computing SSSP, %d edges".format(edgesRdd.count()))
    println("edges: " + graph.edges.collect().mkString(", "))

    // Compute shortests paths from the source node using Spark Datalog API:

    val database = Database(
      Relation.ternary("Edge", edgesRdd),
      Relation.unary("IsSource", sourceRdd))


    val query = """
      |declare Path(int v, int dist aggregate Min).
      |Path(x, d) :- IsSource(s), Edge(s, x, d).
      |Path(x, d) :- Path(y, da), Edge(y, x, db), d = da + db.
      """.stripMargin
    val resultDatabase = database.datalog(query)


    val resultPathsRdd = resultDatabase("Path")
    println(resultPathsRdd.collect().map("Path(" + _.mkString(", ") + ")").mkString("\n"))

  }
}
