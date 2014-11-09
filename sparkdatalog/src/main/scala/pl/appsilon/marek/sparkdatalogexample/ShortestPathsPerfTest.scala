package pl.appsilon.marek.sparkdatalogexample

import pl.appsilon.marek.sparkdatalogexample.TrianglesPerfTest._

import scala.io.Source
import scala.util.Random

import org.apache.spark.graphx.{Edge, Graph, VertexId}
import pl.appsilon.marek.sparkdatalog.{Database, Relation}

object ShortestPathsPerfTest extends PerformanceTest
{
  var sourceId: VertexId = _
  var graph: Graph[Int, Double] = _
  var database: Database = _

  def initialize(args: Seq[String]) = {
    //graph = GraphGenerators.gridGraph(sc, diam, diam) //GraphGenerators.logNormalGraph(sc, numVertices = args(1).toInt)


//    val edges = Source.fromFile(root + "/twitter.txt").getLines().map({
//      str =>
//        val s = str.split(" ")
//        (s(0).toInt, s(1).toInt)
//    }).toSeq
//    val sourceNumber = (edges.map(_._1) ++ edges.map(_._2)).distinct.sorted.head
//    val edgesRawRdd = sc.parallelize(edges)

    val path: String = root + "/twitter.txt"
    println("reading from " + path)
    val edgesRawRdd = sc.textFile(path).map({
      str =>
        val s = str.split(" ")
        val e = (s(0).toInt, s(1).toInt)
        if(e._1 > e._2) e.swap else e
    })
    val edgesRdd = edgesRawRdd.map(edge => (edge._1, edge._2, Random.nextInt(1000)))
    val sourceNumber = edgesRdd.map(_._1).distinct().take(1).head

    println("Read " + edgesRdd.count() + " edges in " + edgesRdd.partitions.size + " partitions.")

    sourceId = sourceNumber
    graph = Graph.fromEdges(edgesRdd.map({case (a, b, c) => Edge(a, b, c)}), 0)

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

    println("sssp" + sssp.vertices.collect().map(_._2).sum)
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
