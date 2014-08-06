package s2g

import socialite.{Yylex, parser}
import java.io.FileReader

object S2G
{

  def parse(parser: parser, lexer: Yylex) = try {
    Some(parser.pProgram())
  } catch {
    case e: Throwable =>
      System.err.println("At line " + lexer.line_num() + ", near \"" + lexer.buff() + "\" :")
      System.err.println("     " + e.getMessage)
      System.exit(1)
      None
  }

  def main(args: Array[String]) = {
    val l = if (args.length == 0) new Yylex(System.in)
            else new Yylex(new FileReader(args(0)))

    val p = new parser(l)
    val parsedOption = parse(p, l)

    parsedOption foreach { parsed =>
      System.out.println(Interpreter.interpret(parsed));
    }
  }
}
