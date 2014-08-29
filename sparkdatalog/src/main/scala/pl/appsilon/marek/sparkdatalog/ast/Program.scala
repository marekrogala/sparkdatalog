package pl.appsilon.marek.sparkdatalog.ast

import pl.appsilon.marek.sparkdatalog.Aggregation
import pl.appsilon.marek.sparkdatalog.ast.declaration.{Declaration, DeclarationConst, DeclarationRelation}
import pl.appsilon.marek.sparkdatalog.ast.rule.Rule

case class Program(declarations: Seq[Declaration], rules: Set[Rule]) {

  /** Semantic analysis */
  val constants = declarations collect { case const: DeclarationConst => const }
//  val environment = evaluateConstants(constants)
  val tables = declarations collect { case const: DeclarationRelation => const }
  val aggregations: Map[String, Aggregation] = Map() ++ (tables.map { table =>
      table.aggregateColumnAndFunction.map(Function.tupled(Aggregation)).map(table.name -> _)
    } flatten)

  declarations.map(_.name).groupBy(l => l).filter({case (_, list) => list.length > 1}).map(_._1).foreach { name =>
    throw new SyntacticException("Duplicate declaration of relation '" + name + "'")
  }
//
//  private def evaluateConstants(constantsDeclarations: Seq[DeclarationConst]) =
//    constantsDeclarations.foldLeft(PartialSolution()) {
//      (env, declaration) => env + (declaration.name -> declaration.exp.evaluate(Context(env, PartialSolution())))
//    }

}
