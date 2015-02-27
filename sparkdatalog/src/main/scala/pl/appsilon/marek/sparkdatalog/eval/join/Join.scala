package pl.appsilon.marek.sparkdatalog.eval.join

import scala.collection.immutable.HashSet

object Join {
  def innerJoin[K, V, U](left: Seq[(K, V)], right: Seq[(K, U)]): Iterable[(K, (Seq[V], Seq[U]))] = {
    if(left.isEmpty || right.isEmpty) {
      Seq()
    } else if(left.size <= 2 || right.size <= 2) {
      LoopJoin.innerJoin(left, right)
    } else {
      HashJoin.innerJoin(left, right)
    }
  }
}
