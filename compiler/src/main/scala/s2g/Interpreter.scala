package s2g

import java.io.{StringReader, InputStream, Reader}

import s2g.astbuilder.Visitor
import s2g.eval.IncrementalEvaluator
import socialite.{Yylex, parser}
import socialite.Absyn.Program

object Interpreter {
  def interpret(e: Program): String = {
    val visitor = new Visitor[Any]()
    val program = e.accept(visitor, null)
    program.validate()
    val eval = new IncrementalEvaluator()
    eval.eval(program)
  }

  private def parse(lexer: Yylex) = try {
    val parser = new parser(lexer)
    Some(parser.pProgram())
  } catch {
    case e: Throwable =>
      System.err.println("At line " + lexer.line_num() + ", near \"" + lexer.buff() + "\" :")
      System.err.println("     " + e.getMessage)
      System.exit(1)
      None
  }

  private def parseAndInterpret(lexer: Yylex): Unit = {
    parse(lexer) foreach { parsed =>
      System.out.println(Interpreter.interpret(parsed));
    }
  }

  def parseAndInterpret(instream: InputStream): Unit = parseAndInterpret(new Yylex(instream))

  def parseAndInterpret(reader: Reader): Unit = parseAndInterpret(new Yylex(reader))

  def parseAndInterpret(program: String): Unit = parseAndInterpret(new Yylex(new StringReader(program)))

}
