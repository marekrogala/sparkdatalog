package s2g.ast.comparisonoperator

sealed trait ComparisonOperator {
  def decide(compareSign: Int): Boolean
}

case class CompareLe() extends ComparisonOperator{
  override def decide(compareSign: Int): Boolean = compareSign <= 0
}
case class CompareGe() extends ComparisonOperator{
  override def decide(compareSign: Int): Boolean = compareSign >= 0
}
case class CompareLt() extends ComparisonOperator{
  override def decide(compareSign: Int): Boolean = compareSign < 0
}
case class CompareGt() extends ComparisonOperator{
  override def decide(compareSign: Int): Boolean = compareSign > 0
}
case class CompareEq() extends ComparisonOperator{
  override def decide(compareSign: Int): Boolean = compareSign == 0
}
case class CompareNe() extends ComparisonOperator{
  override def decide(compareSign: Int): Boolean = compareSign != 0
}
