package s2g.ast

import s2g.ast.declaration.{DeclarationRelation, DeclarationConst, Declaration}
import s2g.ast.rule.Rule
import s2g.eval.{LanguageError, Context, PartialSolution}
import s2g.spark.Aggregation

case class Program(declarations: Seq[Declaration], rules: Set[Rule]) {

  /** Semantic analysis */
  val constants = declarations collect { case const: DeclarationConst => const }
  val environment = evaluateConstants(constants)
  val tables = declarations collect { case const: DeclarationRelation => const }
  val aggregations: Map[String, Aggregation] = Map() // TODO

  declarations.map(_.name).groupBy(l => l).filter({case (_, list) => list.length > 1}).map(_._1).foreach { name =>
    throw new SyntacticException("Duplicate declaration of relation '" + name + "'")
  }

  private def evaluateConstants(constantsDeclarations: Seq[DeclarationConst]) =
    constantsDeclarations.foldLeft(PartialSolution()) {
      (env, declaration) => env + (declaration.name -> declaration.exp.evaluate(Context(env, PartialSolution())))
    }

}
