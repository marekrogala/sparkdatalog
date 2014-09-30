package pl.appsilon.marek.sparkdatalog.ast.subgoal

trait Subgoal {
  def getInVariables: Set[String]
  def getOutVariables: Set[String]

  def analyze(variableIds: Map[String, Int], boundVariables: Set[Int]): AnalyzedSubgoal
}
