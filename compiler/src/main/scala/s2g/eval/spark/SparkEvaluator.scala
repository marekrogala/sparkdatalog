package s2g.eval.spark

import org.apache.spark.graphx._
import org.apache.spark.{graphx, SparkConf, SparkContext}
import s2g.ast.Program
import s2g.ast.rule.Rule
import s2g.eval.{Fact, PartialSolution, EvaluationState, Evaluator}

class SparkEvaluator extends Evaluator {

  def prepareGraph(program: Program, n: Int, initialEvaluationState: EvaluationState): Graph[VertexState, Int] = {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val verticesIds = 0L until n
    val verticesParallelIds = sc.parallelize(verticesIds)

    val vertices = verticesParallelIds.map(vertexId => (vertexId, VertexState(initialEvaluationState.filter(_.values(0).value.asInstanceOf[Int] == vertexId))))
    val edges = verticesParallelIds.flatMap(source => verticesIds.map(Edge[Int](source, _)))

    Graph(vertices, edges, VertexState(initialEvaluationState))
  }

  def prepareFakeGraph(program: Program, n: Int, initialEvaluationState: EvaluationState): FakeSparkGraph[VertexState] = {
    val verticesIds = 0L until n
    val edges = verticesIds.flatMap(source => verticesIds.map((source, _)))

    FakeSparkGraph.fromEdgeTuples(edges, VertexState(initialEvaluationState))
  }

  override def evaluate(program: Program): String = {

    val initialEvaluationState = new EvaluationState(program.environment, program.tables)

    val graph = prepareGraph(program, 6, initialEvaluationState)

    val evaluation = graph.pregel(SparkEvaluator.makeIteration(initialEvaluationState, program.initializingRules), 3)(
      (vertexId, vertexState, incomingMessages) =>
        SparkEvaluator.vertexMethod(vertexId, vertexState, incomingMessages, program),
      triplet => SparkEvaluator.sendMethod(triplet.toTuple),
      (a, b) => a ++ b
    )
    println(evaluation.vertices.collect().mkString("\n"))
    val output = evaluation.vertices.collect().map(_._2).map(_.evaluationState.accumulatedTables).reduce(_++_)
    println("output: " + output)
    //println(evaluation.vertices.mkString("\n"))

    "done"
  }
}

object SparkEvaluator {
  private def makeIteration(state: EvaluationState, rules: Set[Rule]): Set[Fact] = rules.map(_.apply(state)).flatten

  def vertexMethod(vertexId: VertexId, vertexState: VertexState, incomingMessages: Set[Fact], program: Program) = {
    println("Vertex " + vertexId + /*" in state " + vertexState.toString + */ " received " + incomingMessages.toString)
    val evaluationState = vertexState.evaluationState.putFacts(incomingMessages)
    val generatedFacts = makeIteration(evaluationState, program.relationalRules)
    VertexState(evaluationState.toNextIteration, generatedFacts)
  }
  def sendMethod(triplet: ((VertexId, VertexState), (VertexId, VertexState), Int)) = {
    val ((srcId, srcAttr), (dstId, dstAttr), attr) = triplet
    // Send Message
    val messages = (dstId, srcAttr.outgoingMessages.filter(_.destinationNode == dstId))
    println("From " + srcId + " to " + dstId + " sending " + messages._2.toString)
    Iterator(messages)

  }

}
