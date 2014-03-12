package s2g.eval

class EvaluationState {
  def wasChangedInLastIteration(): Boolean = false

  def beginIteration() = {}

  override def toString: String = "state"
}
