package pl.appsilon.marek.sparkdatalog.spark

import java.util

import org.apache.spark.{HashPartitioner, Partitioner}

class ArrayHashPartitioner(partitions: Int) extends Partitioner {
  def numPartitions = partitions

  def nonNegativeMod(x: Int, mod: Int): Int = {
    val rawMod = x % mod
    rawMod + (if (rawMod < 0) mod else 0)
  }

  def getPartition(key: Any): Int = key match {
    case null => 0
    case _ =>
      val hash = key match {
        case k: Array[Int] => util.Arrays.hashCode(k)
        case k => k.hashCode()
      }
      println("hash for --> " + hash)
      nonNegativeMod(hash, numPartitions)
  }

  override def equals(other: Any): Boolean = other match {
    case h: HashPartitioner =>
      h.numPartitions == numPartitions
    case _ =>
      false
  }
}
