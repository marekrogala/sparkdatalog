package s2g.eval

import s2g.ast.Program

class NaiveEvaluation {
  var state = new EvaluationState()

  def eval(program: Program) = {
    var iteration = 0;
    do {
      println("Iteration " + (iteration+1));
      makeIteration(program);
      iteration += 1;
    } while(state.wasChangedInLastIteration);
    "made " + iteration + " iters\n\n -- Env --\n" + state.toString() + "";
  }

  private def makeIteration(program: Program) {
    state.beginIteration();
    program.rules foreach { _.apply(state); }
  }

}
