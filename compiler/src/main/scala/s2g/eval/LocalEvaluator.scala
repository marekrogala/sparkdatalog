package s2g.eval

import s2g.ast.Program

class LocalEvaluator extends Evaluator {

  override def evaluate(program: Program): String = {
    var iteration = 0
    var state = new EvaluationState(program.environment, program.tables)

    do {
      println("Iteration " + (iteration+1))
      state = state.toNextIteration.putFacts(makeIteration(state, program))
      println("after: ")
      println(state.toString)
      iteration += 1
    } while(state.wasChangedInLastIteration)
    "made " + iteration + " iters\n\n -- Env --\n" + state.toString()
  }

  private def makeIteration(state: EvaluationState, program: Program): Set[Fact] =
    program.rules.map(_.apply(state)).flatten
}
