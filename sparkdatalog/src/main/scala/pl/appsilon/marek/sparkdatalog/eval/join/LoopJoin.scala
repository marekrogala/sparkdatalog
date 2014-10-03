package pl.appsilon.marek.sparkdatalog.eval.join

object LoopJoin {
  def innerJoin[K, V, U](left: Seq[(K, V)], right: Seq[(K, U)]): Iterable[(K, (Seq[V], Seq[U]))] = {
    for ((key, leftVal) <- left.toIterable)
      yield (key, (Seq(leftVal), right.filter(_._1 == key).map(_._2)))
  }
}
