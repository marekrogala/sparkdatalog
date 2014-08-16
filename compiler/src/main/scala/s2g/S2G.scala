package s2g

import socialite.{Yylex, parser}
import java.io.FileReader

object S2G
{
  def main(args: Array[String]) = {
    if (args.length == 0) {
      Interpreter.parseAndInterpret(System.in)
    } else {
      Interpreter.parseAndInterpret(new FileReader(args(0)))
    }
  }
}
