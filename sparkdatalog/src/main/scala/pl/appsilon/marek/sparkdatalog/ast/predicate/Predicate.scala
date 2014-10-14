package pl.appsilon.marek.sparkdatalog.ast.predicate

import pl.appsilon.marek.sparkdatalog.Valuation
import pl.appsilon.marek.sparkdatalog.ast.value.{Value, ValueLiteral, ValueVar}

case class Predicate(tableName: String, args: Seq[Value]) {

  def getVariables: Set[String] = args.flatMap(_.getFreeVariables).toSet

  def analyze(variableIds: Map[String, Int]) = {
    val argIds = args.map({
      case arg: ValueLiteral => Left(arg)
      case ValueVar(varName) => Right(variableIds(varName))
    })
    AnalyzedPredicate(tableName, argIds, Valuation(variableIds.size))
  }

  override def toString = tableName + args.mkString("(", ", ", ")")
}
