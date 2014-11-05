package pl.appsilon.marek.sparkdatalogexample

import java_cup.version

import org.apache.spark.{SparkContext, SparkConf}
import pl.appsilon.marek.sparkdatalog.util.Timed

trait PerformanceTest {

  def initialize(args: Seq[String]): Unit
  def runPregel(): Unit
  def runDatalog(): Unit
  def name: String

  var sc: SparkContext = _

  def main(args: Array[String]): Unit = {
    val master +: version +: moreArgs = args.toList

    val conf = new SparkConf().setAppName("Spark Datalog SSSP Computation").setMaster(master)
    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    sc = new SparkContext(conf)
    sc.setCheckpointDir("checkpoint")
    //sc.setCheckpointDir("hdfs://ec2-54-165-76-72.compute-1.amazonaws.com:9000/checkpoint")
    //sc.addJar("target/scala-2.10/sparkdatalog_2.10-1.0.0.jar")

    Timed("initialize data", initialize(moreArgs))

    Timed("run pregel", runPregel())
    Timed("run datalog", runDatalog())

//    version match {
//      case "pregel" => Timed("run pregel", runPregel())
//      case "datalog" => Timed("run datalog", runDatalog())
//      case _ => ???
//    }

  }

}
