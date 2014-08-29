package s2g.spark

case class Aggregation(column: Int, operator: (Int, Int) => Int){
  val maskedValue = 0

  def merge(partitionedFact: (Fact, Int)) = partitionedFact._1.updated(column, partitionedFact._2)

  def partition(fact: Fact): (Fact, Int) = (fact.updated(column, maskedValue), fact(column))
}
