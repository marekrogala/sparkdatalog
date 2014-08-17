package s2g.eval

case class Fact(relationName: String, instance: Instance) {
  def destinationNode = instance.values(0).value.asInstanceOf[Int]
  // TODO: Make sure all instances have at least arity 1 and first column has type int.
}
