package pl.appsilon.marek.sparkdatalogexample

import scala.io.Source

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog
import pl.appsilon.marek.sparkdatalog.{Database, Relation}

object TrianglesPerfTest extends PerformanceTest
{
  var edgesRdd: RDD[(Int, Int)] = _
  var database: Database = _

  def initialize(args: Seq[String]): Unit = {
    val edges = Source.fromFile("/root/sparkdatalog/sparkdatalog/twitter.txt").getLines().take(200000).map({
      str =>
        val s = str.split(" ")
        val e = (s(0).toInt, s(1).toInt)
        if(e._1 > e._2) e.swap else e
    }).toSeq
//        val edges = Source.fromFile("/home/marek/magisterka/sparkdatalog/sparkdatalog/twitter.txt").getLines().map({
//          str =>
//            val s = str.split(" ")
//            val e = (s(0).toInt, s(1).toInt)
//            if(e._1 > e._2) e.swap else e
//        }).toSeq
//    println("edges cnt:" + edges.size)
//    println("vertx cnt:" + (edges.map(_._1) ++ edges.map(_._2)).toSet.size)

//    val edges = Seq(1->2,2->3, 1->3)

    //val diam = args(0).toInt
    //graph = GraphGenerators.logNormalGraph(sc, numVertices = diam)
    //val edgesRdd = graph.edges.map(edge => (edge.srcId.toInt, edge.dstId.toInt, Random.nextInt(1000)))
    edgesRdd = sc.parallelize(edges).repartition(sparkdatalog.numPartitions)

//
//    edgesRdd = sc.textFile(root + "/twitter.txt").map({
//        str =>
//          val s = str.split(" ")
//          val e = (s(0).toInt, s(1).toInt)
//          if(e._1 > e._2) e.swap else e
//      })
    println("Read " + edgesRdd.count() + " edges in " + edgesRdd.partitions.size + " partitions.")

    database = Database(Relation.binary("Edge", edgesRdd))
  }

  override def runPregel(): Unit = {
//    val result = graph.triangleCount()
//    println("triangles:", result.vertices.collect().map(_._2).sum)
    val canonicalEdges = edgesRdd.filter(Function.tupled(_ < _)).distinct()
    val swappedEdgesRdd = canonicalEdges.map(_.swap)
    val pathOf2 = swappedEdgesRdd.join(canonicalEdges).map( {case (y, (x, z)) => (x, (y, z)) } )
    val triangle = pathOf2.join(canonicalEdges).flatMap({
      case (x, ((y, z), zp)) => if (z == zp) Seq((x, y, z)) else Seq()
    })
    val count = triangle.map(_ => 1).aggregate(0)(_ + _,  _ + _)
    println("Triangle count: " + count)
  }

  def show[T](rdd: RDD[T]) = println(rdd.collect().mkString(", "))

  override def runDatalog(): Unit = {
//
//    val edga = edgesRdd.map(x => Seq(x._1, x._2))
//    val canonicalEdges = edgesRdd.distinct()
//
//
//    val tojoin1 = edga.filter(f => f(0) < f(1)).distinct().map(f => f.slice(1, 2) -> f.slice(0, 1)).distinct()
//    val tojoin2 = edga.map(f => f.slice(0, 1) -> f.slice(1, 2)).distinct()
//    val joined = tojoin1.join(tojoin2).map({case (y, (x, z)) => x ++ y ++ z}).distinct()
//    val filtered = joined.filter(f => f(1) < f(2)).distinct()
//
//    val tojoint1 = filtered.map(f => f.slice(0, 1) ++ f.slice(2, 3) -> f.slice(1, 2))
//    val tojoint2 = edga.map(f => f.slice(0, 2) -> f.slice(2, 2)).distinct()
//    val triangle = tojoint1.join(tojoint2).map({ case (xz, (y, _)) => Seq(xz(0), y(0), xz(1)) }).distinct()
//
//    val count = triangle.map(_ => 1).aggregate(0)(_ + _,  _ + _)
//    println("Triangle count: " + count)

//    val query = """ |declare PathOf2(int v, int w).
//                    |declare Triangle(int v, int w, int u).
//                    |declare Total(int a, int b aggregate Sum).
//                    |PathOf2(x, y, z) :- Edge(x, y), x < y, Edge (y, z), y < z.
//                    |Triangle(x, y, z) :- PathOf2(x, y, z), Edge(x, z).
//                    |Total(a, c) :- Triangle(x, y, z), a = 1, c = 1. """.stripMargin
    val query = """ |declare Triangle(int v, int w, int u).
                    |declare Total(int a, int b aggregate Sum).
                    |Triangle(x, y, z) :- Edge(x, y), x < y, Edge (y, z), y < z, Edge(x, z).
                    |Total(a, c) :- Triangle(x, y, z), a = 1, c = 1. """.stripMargin
    val resultDatabase: Database = database.datalog(query)

    println("Triangles count: " + resultDatabase("Total").collect().mkString)
  }

  override def name: String = "Triangles"
}
