package s2g.eval

import s2g.ast.Program

trait Evaluator {
  def evaluate(program: Program): String
}
