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
//    val masterUrl = master
    println("configuring master: "+ masterUrl)
    val conf = new SparkConf().setAppName("Perf test: " + name).setMaster(masterUrl)
    sc = new SparkContext(conf)
    println("spark version: " + sc.version)
    sc.setCheckpointDir("hdfs://" + master + ":9000/checkpoint")
//    sc.setCheckpointDir("checkpoint")
    sc.addJar("target/scala-2.10/sparkdatalog_2.10-1.0.0.jar")
    root =  "hdfs://" + master + ":9000/input"
//    root =  "/home/marek/magisterka/sparkdatalog/sparkdatalog"

    Timed("initialize data", initialize(moreArgs))

    Timed("run pregel", runPregel())
    Timed("run datalog", runDatalog())
    Timed("run pregel", runPregel())

  }

}
