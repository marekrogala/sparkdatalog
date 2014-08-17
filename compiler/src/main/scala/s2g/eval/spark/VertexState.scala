package s2g.eval.spark

import s2g.eval.{Fact, PartialSolution, EvaluationState}

case class VertexState(evaluationState: EvaluationState, outgoingMessages: Set[Fact] = Set())
