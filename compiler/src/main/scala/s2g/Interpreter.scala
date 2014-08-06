package s2g

import s2g.astbuilder.Visitor
import socialite.Absyn.Program
import s2g.eval.IncrementalEvaluator

object Interpreter {
  def interpret(e: Program): String = {
    val visitor = new Visitor[Any]()
    val program = e.accept(visitor, null)
    val eval = new IncrementalEvaluator()
    eval.eval(program)
  }
}
