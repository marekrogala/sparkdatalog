package pl.appsilon.marek.sparkdatalogexample

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import pl.appsilon.marek.sparkdatalog._

object SparkDatalogExample
{
  val exampleEdges = Seq((1, 2, 1), (1, 3, 5), (2, 3, 1))
  val sourceNode = Seq(1)

  def main(args: Array[String]): Unit = {
    // Standard Spark setup
    val conf = new SparkConf().setAppName("Spark Datalog SSSP Computation").setMaster("local[4]")
    val sc = new SparkContext(conf)
    sc.setCheckpointDir("checkpoint")

    // Let us assume we have graph edges represented as triples (source, target, distance) in an RDD.
    // It could have been read for example from HDFS or other distributed file systems, just like any Spark RDD.
    val edgesRdd = sc.parallelize(exampleEdges)
    val sourceRdd = sc.parallelize(sourceNode)

    // -----------------------------------------------------------------------------------
    // Compute shortests paths from the source node using Spark Datalog API:
    //   1. Create a Database from Relations built from RDDs.
    //   2. Execute a Datalog query on the database, producing a new Database.
    //   3. Retrieve the result from the new Database.
    
    val database = Database(
      Relation.ternary("Edge", edgesRdd),
      Relation.unary("IsSource", sourceRdd))

    val query = """
        |declare Path(int v, int dist aggregate Min).
        |Path(x, d) :- IsSource(s), Edge(s, x, d).
        |Path(x, d) :- Path(y, da), Edge(y, x, db), d = da + db.
      """.stripMargin
    val resultDatabase = database.datalog(query)

    // -----------------------------------------------------------------------------------

    println(resultDatabase.relations)

    // We can now save the paths RDD to distributed storage or perform further computations on it.
    // We can of course also print it to stdout:
    val resultPathsRdd: RDD[Fact] = resultDatabase("Path")
    println("computed %d results".format(resultPathsRdd.count()))

  }
}
