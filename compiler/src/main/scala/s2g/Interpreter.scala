package s2g

import s2g.astbuilder.Visitor
import socialite.Absyn.Program
import s2g.eval.NaiveEvaluation

object Interpreter {
  def interpret(e: Program): String = {
    val visitor = new Visitor[Any]();
    val program = e.accept(visitor, null);
    val eval = new NaiveEvaluation();
    eval.eval(program);
  }
}
