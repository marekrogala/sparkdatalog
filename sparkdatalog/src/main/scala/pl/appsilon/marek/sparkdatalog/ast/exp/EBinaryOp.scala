package pl.appsilon.marek.sparkdatalog.ast.exp

import pl.appsilon.marek.sparkdatalog.Valuation
import pl.appsilon.marek.sparkdatalog.ast.binaryoperator.BinaryOperator

case class EBinaryOp(left: Exp, right: Exp, op: BinaryOperator) extends Exp {
  override def getFreeVariables: Set[String] = left.getFreeVariables ++ right.getFreeVariables
  override def analyze(variableIds: Map[String, Int]): AnalyzedExp = AnalyzedEBinaryOp(left.analyze(variableIds), right.analyze(variableIds), op)
}
