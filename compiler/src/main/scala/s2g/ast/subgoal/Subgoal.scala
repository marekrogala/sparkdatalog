package s2g.ast.subgoal

import org.apache.spark.rdd.RDD
import s2g.eval.{TableStates, Context, PartialSolution}
import s2g.spark.{Database, Valuation}

trait Subgoal {
  def join(valuations: RDD[Valuation], boundVariables: Set[String], database: Database): Option[RDD[Valuation]]
  def select(initialValuations: Set[Valuation], boundVariables: Set[String], database: Database): Option[RDD[Valuation]]
  def evaluateStatic(valuation: Valuation): Set[Valuation]
  def getInVariables: Set[String]
  def getOutVariables: Set[String]


  def solveOn(context: Context, tableStates: TableStates): Set[PartialSolution]
  def getInputs(context: Context): Set[String]
  def getOutputs(context: Context): Set[String]
}
