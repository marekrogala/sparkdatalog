package pl.appsilon.marek.sparkdatalogexample

import scala.util.Random

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.util.GraphGenerators
import pl.appsilon.marek.sparkdatalog.{Database, Relation}

object ShortestPathsLocalTest
{
  val sourceNode = Seq(0)
  val exampleEdges = Seq((0, 1, 1), (0, 2, 5), (1, 2, 1))

  def main(args: Array[String]): Unit = {

    // Standard Spark setup
    val conf = new SparkConf().setAppName("Spark Datalog SSSP Computation").setMaster("local[4]")
    val sc = new SparkContext(conf)

    // Generate random graph with random edge lengths
    val graph = GraphGenerators.logNormalGraph(sc, numVertices = 1000)
    //val edgesRdd = graph.edges.map(edge => (edge.srcId.toInt, edge.dstId.toInt, Random.nextInt(1000)))
    val edgesRdd = sc.parallelize(exampleEdges)
    val sourceRdd = sc.parallelize(sourceNode)

    println("computing SSSP, %d edges".format(edgesRdd.count()))
    println("edges: " + edgesRdd.collect().mkString(", "))

    // Compute shortests paths from the source node using Spark Datalog API:

    val database = Database(
      Relation.ternary("Edge", edgesRdd),
      Relation.unary("IsSource", sourceRdd))


    val query = """
      |declare Path(int v, int dist aggregate Min).
      |Path(x, d) :- IsSource(s), Edge(s, x, d).
      |Path(x, d) :- Path(y, da), Edge(y, x, db), d = da + db.
      """.stripMargin
    val resultDatabase = database.datalogLocally(query)


    println(resultDatabase)

  }
}
