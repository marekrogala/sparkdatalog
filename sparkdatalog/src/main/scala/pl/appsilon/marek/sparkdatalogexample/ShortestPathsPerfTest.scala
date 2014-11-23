package pl.appsilon.marek.sparkdatalogexample

import org.apache.spark.graphx.util.GraphGenerators
import pl.appsilon.marek.sparkdatalogexample.TrianglesPerfTest._

import scala.io.Source
import scala.util.Random

import org.apache.spark.graphx.{Edge, Graph, VertexId}
import pl.appsilon.marek.sparkdatalog.{Database, Relation}

object ShortestPathsPerfTest extends PerformanceTest
{
  var sourceId: VertexId = _
  var graph: Graph[Int, Int] = _
  var database: Database = _

  def initialize(args: Seq[String]) = {

//    val edges = Source.fromFile(root + "/twitter.txt").getLines().map({
//      str =>
//        val s = str.split(" ")
//        (s(0).toInt, s(1).toInt)
//    }).toSeq
//    val sourceNumber = (edges.map(_._1) ++ edges.map(_._2)).distinct.sorted.head
//    val edgesRawRdd = sc.parallelize(edges)

    //val path: String = root + "/twitter.txt"
    //println("reading from " + path)
//    val edgesXRdd = sc.textFile(path).map({
//      str =>
//        val s = str.split(" ")
//        val e = (s(0).toInt, s(1).toInt)
//        if(e._1 > e._2) e.swap else e
//    }).repartition(64)

    val diam = 10000
    graph = GraphGenerators.rmatGraph(sc, diam, 10*diam).mapEdges(_ => Random.nextInt(1000))
    graph = Graph(graph.vertices, graph.edges.union(graph.reverse.edges))

    val edgesRdd = graph.edges.map({case Edge(a, b, c) => (a.toInt, b.toInt, c)})
    val sourceNumber = edgesRdd.map(_._1).distinct().take(1).head

    println("Read " + edgesRdd.count() + " edges in " + edgesRdd.partitions.size + " partitions.")

    sourceId = sourceNumber

    val sourceRdd = sc.parallelize(Seq(sourceNumber))
    database = Database(
      Relation.ternary("Edge", edgesRdd),
      Relation.unary("IsSource", sourceRdd))
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

    println("sssp" + sssp.vertices.map(v => (v._1, v._2)).reduce({case (a, b) => (a._1 + b._1, a._2 + b._2)}))
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
