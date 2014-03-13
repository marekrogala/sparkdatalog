package s2g.eval

import s2g.ast.ValueLiteral

case class PartialSolution(map: Map[String, ValueLiteral] = Map()) {
  def ++(other: PartialSolution): PartialSolution = PartialSolution(map ++ other.map)
  def +(assignment: (String, ValueLiteral)): PartialSolution = PartialSolution(map + assignment)
  def apply(key: String): ValueLiteral = map(key)
  def get(key: String): Option[ValueLiteral] = map.get(key)
}
