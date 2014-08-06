package s2g.ast

import s2g.ast.declaration.{DeclarationGlobal, DeclarationConst, Declaration}
import s2g.ast.rule.Rule
import s2g.eval.{LanguageError, Context, PartialSolution}

case class Program(declarations: Seq[Declaration], rules: Seq[Rule]) {

  val environment = evaluateConstants(extractConstants)

  private def extractConstants = declarations collect { case const: DeclarationConst => const }

  def tables = declarations collect { case const: DeclarationGlobal => const }

  private def evaluateConstants(constantsDeclarations: Seq[DeclarationConst]) =
    constantsDeclarations.foldLeft(PartialSolution()) {
      (env, declaration) => env + (declaration.name -> declaration.exp.evaluate(Context(env, PartialSolution())))
    }

  def validate(): Unit = {
    // Declarations

    // Duplicate names
    val names = declarations.map(_.name)
    names.groupBy(l => l).filter({case (_, list) => list.length > 1}).map(_._1).foreach { name =>
      throw new LanguageError("Duplicate declaration of " + name)
    }
    declarations.foreach(_.validate)

    // Rules
  }

}
