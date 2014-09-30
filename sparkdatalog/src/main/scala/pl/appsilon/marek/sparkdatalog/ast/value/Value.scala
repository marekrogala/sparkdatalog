package pl.appsilon.marek.sparkdatalog.ast.value

trait Value {
  def analyze(variableIds: Map[String, Int]): AnalyzedValue
  def getFreeVariables: Set[String]
}
