package s2g

import java.io.{Reader, InputStream, StringReader}

import s2g.astbuilder.Visitor
import socialite.Absyn.Program
import socialite.{parser, Yylex}

object Parser {

  private def tryToParse(lexer: Yylex): Option[Program] = try {
    val parser = new parser(lexer)
    Some(parser.pProgram())
  } catch {
    case e: Throwable =>
      System.err.println("At line " + lexer.line_num() + ", near \"" + lexer.buff() + "\" :")
      System.err.println("     " + e.getMessage)
      System.exit(1)
      None
  }

  def apply(lexer: Yylex): Option[ast.Program] = {
    val parsed = tryToParse(lexer)
    val visitor = new Visitor[Any]()
    parsed.map { program =>
      val programAst = program.accept(visitor, null)
      programAst.validate()
      programAst
    }
  }

  def apply(instream: InputStream): Option[ast.Program] = apply(new Yylex(instream))

  def apply(reader: Reader): Option[ast.Program] = apply(new Yylex(reader))

  def apply(program: String): Option[ast.Program] = apply(new StringReader(program))
}
