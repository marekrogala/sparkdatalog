package s2g.ast

import s2g.ast.declaration.{DeclarationConst, Declaration}
import s2g.ast.rule.Rule
import s2g.eval.{Context, PartialSolution}

case class Program(declarations: Seq[Declaration], rules: Seq[Rule]) {
  val environment = evaluateConstants(extractConstants)

  private def extractConstants = declarations collect { case const: DeclarationConst => const }

  private def evaluateConstants(constantsDeclarations: Seq[DeclarationConst]) =
    constantsDeclarations.foldLeft(PartialSolution()) {
      (env, declaration) => env + (declaration.name -> declaration.exp.evaluate(Context(env, PartialSolution())))
    }

}
