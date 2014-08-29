package pl.appsilon.marek.sparkdatalog.astbuilder

import java.io.{InputStream, Reader, StringReader}

import pl.appsilon.marek.sparkdatalog.ast
import pl.appsilon.marek.sparkdatalog.ast.SyntacticException
import socialite.Absyn.Program
import socialite.{Yylex, parser}

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
