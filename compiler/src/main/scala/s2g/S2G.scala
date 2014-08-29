package s2g

import java.io.FileReader

import s2g.eval.{Evaluator, LocalEvaluator}

object S2G
{
  def main(args: Array[String]) = {
    val evaluator: Evaluator = new LocalEvaluator
    val interpreter = new Interpreter(evaluator)
    if (args.length == 0) {
      interpreter.parseAndInterpret(System.in)
    } else {
      interpreter.parseAndInterpret(new FileReader(args(0)))
    }
  }
}
