package s2g

import java.io.{InputStream, Reader, StringReader}

import s2g.astbuilder.Visitor
import s2g.eval.Evaluator
import socialite.{Yylex, parser}
import socialite.Absyn.Program

class Interpreter(evaluator: Evaluator) {
  def interpret(program: ast.Program): String = {
    evaluator.evaluate(program)
  }

  private def parseAndInterpret(lexer: Yylex): Unit = {
    Parser(lexer) foreach { parsed =>
      System.out.println(interpret(parsed));
    }
  }

  def parseAndInterpret(instream: InputStream): Unit = parseAndInterpret(new Yylex(instream))

  def parseAndInterpret(reader: Reader): Unit = parseAndInterpret(new Yylex(reader))

  def parseAndInterpret(program: String): Unit = parseAndInterpret(new Yylex(new StringReader(program)))


}
