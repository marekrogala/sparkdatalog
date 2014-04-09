package s2g.eval

import s2g.ast.Program

class IncrementalEvaluator {

  def eval(program: Program) = {
    var iteration = 0
    var state = new EvaluationState(program.environment)

    do {
      println("Iteration " + (iteration+1))
      state = makeIteration(state, program)
      iteration += 1
    } while(state.wasChangedInLastIteration)
    "made " + iteration + " iters\n\n -- Env --\n" + state.toString()
  }

  private def makeIteration(state: EvaluationState, program: Program): EvaluationState = {
    val evaluated = program.rules.foldLeft(state){ case (acc, rule) => rule(acc) }
    evaluated.toNextIteration
  }

}
