package pl.appsilon.marek.sparkdatalogexample

import scala.util.Random

import org.apache.spark.graphx.{Graph, VertexId}
import org.apache.spark.graphx.util.GraphGenerators
import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog.{Database, Relation}
import pl.appsilon.marek.sparkdatalog.util.Timed

object ShortestPathsPerfTest extends PerformanceTest
{
  var sourceNumber: Int = 0
  var sourceId: VertexId = sourceNumber
  var graph: Graph[(Int, Int), Double] = _
  var database: Database = _

  def initialize(args: Seq[String]) = {
    val diam = args(0).toInt
    graph = GraphGenerators.gridGraph(sc, diam, diam) //GraphGenerators.logNormalGraph(sc, numVertices = args(1).toInt)

    val edgesRdd = graph.edges.map(edge => (edge.srcId.toInt, edge.dstId.toInt, Random.nextInt(1000)))
    val sourceRdd = sc.parallelize(Seq(sourceNumber))
    database = Database(
      Relation.ternary("Edge", edgesRdd),
      Relation.unary("IsSource", sourceRdd))
  }

  def runPregel() = {
    val initialGraph = graph.mapVertices((id, _) => if (id == sourceId) 0.0 else Double.PositiveInfinity)
    val sssp =
      initialGraph.pregel(Double.PositiveInfinity)(
        (id, dist, newDist) => math.min(dist, newDist), // Vertex Program
        triplet => {  // Send Message
          if (triplet.srcAttr + triplet.attr < triplet.dstAttr) {
            Iterator((triplet.dstId, triplet.srcAttr + triplet.attr))
          } else {
            Iterator.empty
          }
        },
        (a,b) => math.min(a,b) // Merge Message
      )
    println("cnt" + sssp.vertices.count())
  }

  override def runDatalog(): Unit = {
    val query = """
                  |declare Path(int v, int dist aggregate Min).
                  |Path(x, d) :- IsSource(s), Edge(s, x, d).
                  |Path(x, d) :- Path(y, da), Edge(y, x, db), d = da + db.
                """.stripMargin

    val resultDatabase = database.datalog(query)
  }

  override def name: String = "Shortest Paths"
}
