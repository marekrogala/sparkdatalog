package pl.appsilon.marek.sparkdatalog.ast.subgoal

trait Subgoal {
  val evaluationPriority: Int

  def getInVariables: Set[String]
  def getOutVariables: Set[String]
  def isRelational: Boolean

  def analyze(variableIds: Map[String, Int], boundVariables: Set[Int]): AnalyzedSubgoal
}
