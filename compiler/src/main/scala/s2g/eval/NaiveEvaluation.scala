package s2g.eval

import s2g.ast.Program

class NaiveEvaluation {

  def eval(program: Program) = {
    var iteration = 0
    val state = new EvaluationState(program.environment)

    do {
      println("Iteration " + (iteration+1))
      makeIteration(state, program)
      iteration += 1
    } while(state.wasChangedInLastIteration)
    "made " + iteration + " iters\n\n -- Env --\n" + state.toString()
  }

  private def makeIteration(state: EvaluationState, program: Program) {
    state.beginIteration()
    program.rules foreach { _.apply(state) }
  }

}
