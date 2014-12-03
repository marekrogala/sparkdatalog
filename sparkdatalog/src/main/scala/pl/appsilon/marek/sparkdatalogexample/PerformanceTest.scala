package pl.appsilon.marek.sparkdatalogexample

import org.apache.spark.{SparkConf, SparkContext}
import pl.appsilon.marek.sparkdatalog.util.Timed

trait PerformanceTest {

  def initialize(args: Seq[String]): Unit
  def runPregel(): Unit
  def runDatalog(): Unit
  def name: String

  var root: String = _

  var sc: SparkContext = _

  def main(args: Array[String]): Unit = {
    val master +: moreArgs = args.toList
//
    val masterUrl: String = "spark://" + master + ":7077"
    println("configuring master: "+ masterUrl)
    val conf = new SparkConf().setAppName("Perf test: " + name).setMaster(masterUrl)
    //conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    sc = new SparkContext(conf)
    sc.setCheckpointDir("hdfs://" + master + ":9000/checkpoint")
    sc.addJar("target/scala-2.10/sparkdatalog_2.10-1.0.0.jar")
    root =  "hdfs://" + master + ":9000/input"

    println("sprawdzenie: " + sc.parallelize(Seq(1,2,3)).count())


//    val conf = new SparkConf().setAppName("Spark Datalog SSSP Computation").setMaster("local[4]")
//    //conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
//    sc = new SparkContext(conf)
//    sc.setCheckpointDir("checkpoint")
//    root =  "/home/marek/magisterka/sparkdatalog/sparkdatalog"

    Timed("initialize data", initialize(moreArgs))

    Timed("run pregel", runPregel())
    Timed("run datalog", runDatalog())
    Timed("run pregel", runPregel())

//    version match {
//      case "pregel" => Timed("run pregel", runPregel())
//      case "datalog" => Timed("run datalog", runDatalog())
//      case _ => ???
//    }

  }

}
