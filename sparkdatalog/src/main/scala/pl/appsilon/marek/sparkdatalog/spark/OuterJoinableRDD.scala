package pl.appsilon.marek.sparkdatalog.spark

import org.apache.spark.Partitioner
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.{PairRDDFunctions, RDD}

import scala.reflect.ClassTag

class OuterJoinableRDD[K, V](self: RDD[(K, V)])(implicit kt: ClassTag[K], vt: ClassTag[V]) {
    def fullOuterJoin[W](other: RDD[(K, W)], partitioner: Partitioner): RDD[(K, (Option[V], Option[W]))] = {
      self.cogroup(other, partitioner).flatMapValues {
        case (vs, Seq()) => vs.map(v => (Some(v), None: Option[W]))
        case (Seq(), ws) => ws.map(w => (None: Option[V], Some(w)))
        case (vs, ws) => for (v <- vs; w <- ws) yield (Some(v), Some(w))
      }
    }

    def fullOuterJoin[W](other: RDD[(K, W)]): RDD[(K, (Option[V], Option[W]))] = {
      fullOuterJoin(other, Partitioner.defaultPartitioner(self, other))
    }
}

object OuterJoinableRDD {

  implicit def rddToOuterJoinableRDD[K, V](rdd: RDD[(K, V)])
                                          (implicit kt: ClassTag[K], vt: ClassTag[V], ord: Ordering[K] = null) = {
    new OuterJoinableRDD(rdd)
  }
}
