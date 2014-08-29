package s2g

import java.io.{Reader, InputStream, StringReader}

import s2g.ast.SyntacticException
import s2g.astbuilder.Visitor
import socialite.Absyn.Program
import socialite.{parser, Yylex}

object Parser {

  private def parse(lexer: Yylex): Program = try {
    val parser = new parser(lexer)
    parser.pProgram()
  } catch {
    case e: Throwable =>
      throw new SyntacticException("At line " + lexer.line_num() + ", near \"" + lexer.buff() + "\" :\n     " + e.getMessage)
  }

  def apply(lexer: Yylex): ast.Program = {
    parse(lexer).accept(new Visitor[Any](), null)
  }

  def apply(instream: InputStream): ast.Program = apply(new Yylex(instream))

  def apply(reader: Reader): ast.Program = apply(new Yylex(reader))

  def apply(program: String): ast.Program = apply(new StringReader(program))
}
