package s2g.eval

import s2g.ast.value.ValueLiteral

case class Context(environment: PartialSolution, partialSolution: PartialSolution) {

  def apply(key: String): ValueLiteral = get(key).get
  def get(key: String): Option[ValueLiteral] = environment.get(key) orElse partialSolution.get(key)

}
