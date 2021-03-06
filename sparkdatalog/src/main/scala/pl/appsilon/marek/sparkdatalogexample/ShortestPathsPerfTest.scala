package pl.appsilon.marek.sparkdatalogexample

import scala.io.Source
import scala.util.Random

import org.apache.spark.graphx.{Edge, Graph, VertexId}
import pl.appsilon.marek
import pl.appsilon.marek.sparkdatalog.{Database, Relation}

object ShortestPathsPerfTest extends PerformanceTest
{
  var sourceId: VertexId = _
  var graph: Graph[Int, Int] = _
  var database: Database = _

  def initialize(args: Seq[String]) = {
    val edges = Source.fromFile(root + "/twitter.txt").getLines().take(10000).map({
      str =>
        val s = str.split(" ")
        (s(0).toInt, s(1).toInt)
    }).toSeq
    val edgesOriginalRdd = sc.parallelize(edges)
    val path: String = root + "/twitter.txt"
    println("reading from " + path)
//    val edgesOriginalRdd = sc.textFile(path).map({
//      str =>
//        val s = str.split(" ")
//        val e = (s(0).toInt, s(1).toInt)
//        if(e._1 > e._2) e.swap else e
//    }).repartition(marek.sparkdatalog.numPartitions)
    val edgesRawRdd = edgesOriginalRdd.map({case (a, b) => (a, b, 1 + Random.nextInt(10))})

    graph = Graph.fromEdges(edgesRawRdd.map({case (a, b, c) => Edge(a, b, c)}), 0)

    val edgesRdd = graph.edges.map({case Edge(a, b, c) => (a.toInt, b.toInt, c)}).cache()

    println("Read " + edgesRdd.count() + " edges in " + edgesRdd.partitions.size + " partitions.")

    sourceId = edgesRdd.map(_._1).take(1).head
    println("source: " + sourceId)
    database = Database(Relation.ternary("Edge", edgesRdd))
    database.materialize()
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

    val shortestPaths = sssp.vertices.map(v => (v._1, v._2))
  }

  override def runDatalog(): Unit = {
    val query = """ declare Path(int v, int dist aggregate Min).
                     Path(x, d) :- s == """ + sourceId + """ , Edge(s, x, d).
                     Path(x, d) :- Path(y, da), Edge(y, x, db), d = da + db."""
    val resultDatabase = database.datalog(query)
  }

  override def name: String = "Shortest Paths"
}
