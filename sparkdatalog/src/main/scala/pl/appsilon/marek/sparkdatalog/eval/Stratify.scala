package pl.appsilon.marek.sparkdatalog.eval

import pl.appsilon.marek.sparkdatalog.ast.Program
import pl.appsilon.marek.sparkdatalog.ast.rule.Rule

import scala.collection.mutable

object Stratify {

  def transpose(graph: Map[String, Seq[String]]) = {
    val grapht = mutable.Map[String, mutable.ArrayBuffer[String]]()
    for (
      (s, ts) <- graph;
      t <- ts
    ) {
      if (grapht.contains(t)) {
        grapht(t).append(s)
      } else {
        grapht(t) = mutable.ArrayBuffer[String](s)
      }
    }
    val result: Map[String, Seq[String]] = grapht.mapValues(_.toSeq).toMap
    result
  }

  def dfsVisit1(
      v: String,
      graph: Map[String, Seq[String]],
      visited: mutable.Set[String],
      sccOrder: mutable.ArrayBuffer[String]): Unit = {
    visited += v
    for (u <- graph.getOrElse(v, Seq()) if !visited.contains(u)) {
      dfsVisit1(u, graph, visited, sccOrder)
    }
    sccOrder += v
  }

  def dfsVisit2(
      v: String,
      graph: Map[String, Seq[String]],
      visited: mutable.Set[String]): Seq[String] = {
    visited += v

    val subtree = (for (u <- graph.getOrElse(v, Seq()) if !visited.contains(u)) yield {
      dfsVisit2(u, graph, visited)
    }).flatten

    v +: subtree
  }

  def stronglyConnectedComponents(graph: Map[String, Seq[String]]): Seq[Seq[String]] = {
    val vertices = graph.keys

    val visited = mutable.Set[String]()
    val sccOrder = mutable.ArrayBuffer[String]()
    for(v <- vertices if !visited.contains(v))
      dfsVisit1(v, graph, visited, sccOrder)

    visited.clear()
    val grapht = transpose(graph)
    val sccs = for(v <- sccOrder.reverse.toSeq if !visited.contains(v)) yield {
      dfsVisit2(v, grapht, visited)
    }

    sccs
  }

  def apply(program: Program): Seq[Seq[Rule]] = {
    val idbRelations = program.rules.map(_.head.name).toSet
    val graph = program.rules.groupBy(_.head.name).mapValues(_.flatMap(_.refferedRelations).toSet.toSeq.filter(idbRelations.contains))
    val sccs = stronglyConnectedComponents(graph)
    val strata = sccs.map(scc => program.rules.filter(rule => scc.contains(rule.head.name))).reverse
    strata
  }
}
