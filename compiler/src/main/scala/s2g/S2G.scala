package s2g

import java.io.FileReader

import s2g.eval.IncrementalEvaluator

object S2G
{
  def main(args: Array[String]) = {
    val interpreter = new Interpreter(new SparkEvaluator)
    if (args.length == 0) {
      interpreter.parseAndInterpret(System.in)
    } else {
      interpreter.parseAndInterpret(new FileReader(args(0)))
    }
    /*Interpreter.parseAndInterpret(
      """
        |declare Edge(int a, int b, int d).
        |declare Path(int t, int d aggregate Max).
        |
        |Edge(x, y, d) :- x=1, y=2, d=1.
        |Edge(x, y, d) :- x=2, y=3, d=1.
        |Edge(x, y, d) :- x=1, y=3, d=5.
        |
        |Path(x, d) :- Edge(1, x, d).
        |Path(x, d) :- Path(y, da), Edge(y, x, db), d = da + db.
      """.stripMargin)*/
  }
}
