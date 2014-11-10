package pl.appsilon.marek.sparkdatalogexample

import pl.appsilon.marek.sparkdatalogexample.ShortestPathsPerfTest._

import scala.io.Source
import scala.util.Random

import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.graphx.util.GraphGenerators
import pl.appsilon.marek.sparkdatalog.{Database, Relation}

object ConnectedComponentsPerfTest extends PerformanceTest
{
  var graph: Graph[Int, Int] = _
  var database: Database = _

  def initialize(args: Seq[String]): Unit = {
//    val edges = Source.fromFile(root + "/twitter.txt").getLines().map({
//      str =>
//        val s = str.split(" ")
//        (s(0).toInt, s(1).toInt)
//    }).toSeq
//    val vertices = (edges.map(_._1) ++ edges.map(_._2)).distinct.sorted

    //val diam = args(0).toInt
    //graph = GraphGenerators.logNormalGraph(sc, numVertices = diam)
    //val edgesRdd = graph.edges.map(edge => (edge.srcId.toInt, edge.dstId.toInt, Random.nextInt(1000)))
    //val verticesRdd = sc.parallelize(0 until diam)
//    val edgesRdd = sc.parallelize(edges)
//    val verticesRdd = sc.parallelize(vertices)




    val path: String = root + "/twitter.txt"
    println("reading from " + path)
    val edgesRdd = sc.textFile(path).map({
      str =>
        val s = str.split(" ")
        val e = (s(0).toInt, s(1).toInt)
        if(e._1 > e._2) e.swap else e
    }).repartition(128)
    val verticesRdd = edgesRdd.map(_._1).union(edgesRdd.map(_._2)).distinct()

    graph = Graph.fromEdges(edgesRdd.map({case (a, b) => Edge(a, b)}), 0)

    database = Database(
      Relation.binary("Edge", edgesRdd),
      Relation.unary("Node", verticesRdd))

  }

  override def runDatalog(): Unit = {
    val query = """
                  |declare Component(int n, int component aggregate Min).
                  |declare ComponentId(int n).
                  |Component(n, i) :- Node(n), i = n.
                  |Component(n, i) :- Component(p, i), Edge(p, n).
                  |ComponentId(id) :- Component(x, id).
                """.stripMargin


    val resultDatabase = database.datalog(query)
    println(resultDatabase("ComponentId").count())
  }

  override def name: String = "Connected Components"

  override def runPregel(): Unit = {
    val initialGraph = graph.mapVertices((id, _) => id.toInt)

    val sssp = initialGraph.pregel(Int.MaxValue)(
      (id, cmp, newCmp) => math.min(cmp, newCmp), // Vertex Program
      triplet => {  // Send Message
        if (triplet.srcAttr < triplet.dstAttr) {
          Iterator((triplet.dstId, triplet.srcAttr))
        } else {
          Iterator.empty
        }
      },
      (a, b) => math.min(a, b) // Merge Message
    )

    val componentIds = sssp.vertices.map(_._2).distinct()
    println(componentIds.count())
  }
}
