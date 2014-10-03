package pl.appsilon.marek.sparkdatalogexample

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx.VertexId
import org.apache.spark.graphx.util.GraphGenerators
import pl.appsilon.marek.sparkdatalog.util.Timed

object SparkShortestPathsTest
{
  def main(args: Array[String]): Unit = {
    // Standard Spark setup
    val conf = new SparkConf().setAppName("Spark Datalog SSSP Computation").setMaster(args(0))
    val sc = new SparkContext(conf)
    sc.setCheckpointDir("checkpoint")

    // Generate random graph with random edge lengths
    // val graph = GraphGenerators.logNormalGraph(sc, numVertices = 10000)

    val diam: Int = args(1).toInt
    val graph = GraphGenerators.gridGraph(sc, diam, diam) //GraphGenerators.logNormalGraph(sc, numVertices = args(1).toInt)

    val sourceId: VertexId = 0

    val initialGraph = graph.mapVertices((id, _) => if (id == sourceId) 0.0 else Double.PositiveInfinity)
    Timed("pregel", () => {
      val sssp = initialGraph.pregel(Double.PositiveInfinity)(
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
      //println(sssp.vertices.collect.mkString("\n"))
    })


  }
}
