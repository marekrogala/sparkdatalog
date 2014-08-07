package s2g.eval

import s2g.ast.Program

class IncrementalEvaluator {

  def eval(program: Program) = {
    var iteration = 0
    var state = new EvaluationState(program.environment, program.tables)

    do {
      println("Iteration " + (iteration+1))
      state = makeIteration(state, program)
      println("after: ")
      println(state.toString)
      iteration += 1
    } while(state.wasChangedInLastIteration)
    "made " + iteration + " iters\n\n -- Env --\n" + state.toString()
  }

  private def makeIteration(state: EvaluationState, program: Program): EvaluationState =
    program.rules.foldLeft(state.toNextIteration) { case (acc, rule) => rule.apply(state, acc)}

}
