package pl.appsilon.marek.sparkdatalog.eval.join

object HashJoin {
  def innerJoin[K, V, U](left: Seq[(K, V)], right: Seq[(K, U)]): Iterable[(K, (Seq[V], Seq[U]))] = {
    val rightMap = right.groupBy(_._1)
    for ((key, leftVals) <- left.groupBy(_._1).toIterable;
         rightVals <- rightMap.get(key))
      yield (key, (leftVals.map(_._2), rightVals.map(_._2)))
  }
}
