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

    val isLocalDebug: Boolean = master == "local" || master == "local[4]"

    val (masterUrl, rootDir, checkpointDir) =
      if (isLocalDebug) {
        (master, "/home/marek/magisterka/sparkdatalog/sparkdatalog", "checkpoint")
      } else {
        val root = "hdfs://" + master + ":9000/input"
        ("spark://" + master + ":7077", root, root + "/checkpoint")
      }

    val conf = new SparkConf().setAppName("Perf test: " + name).setMaster(masterUrl)
    sc = new SparkContext(conf)
    root = rootDir
    sc.setCheckpointDir(checkpointDir)

    println("Configuring master: " + masterUrl + ". Spark version: " + sc.version)

    if (!isLocalDebug) {
      sc.addJar("target/scala-2.10/sparkdatalog_2.10-1.0.0.jar")
    }


    Timed("initialize data", initialize(moreArgs))

    Timed("run pregel", runPregel())
    Timed("run datalog", runDatalog())
    Timed("run pregel", runPregel())

  }

}
