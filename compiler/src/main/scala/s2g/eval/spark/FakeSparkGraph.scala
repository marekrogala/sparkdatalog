package s2g.eval.spark

import scala.reflect.ClassTag

import org.apache.spark.graphx._

class Triplet[VD: ClassTag](triplet: ((VertexId, VD), (VertexId, VD), Int)){
  def toTuple = triplet
}

class FakeSparkGraph[VD: ClassTag](val vertices: Map[VertexId, VD], edges: Seq[Edge[Int]])  {

  def mapVertices[VD2: ClassTag](f: (VertexId, VD) => VD2): FakeSparkGraph[VD2] = {
    val newVertices = vertices.map(v => (v._1, f(v._1, v._2)))
    new FakeSparkGraph[VD2](newVertices, edges)
  }
  def mapReduceTriplets[A: ClassTag](
    mapFunc: Triplet[VD] => Iterator[(VertexId, A)],
    reduceFunc: (A, A) => A): Map[VertexId, A] = {
    val triplets = edges map { case Edge(s, d, ed) => new Triplet(((s, vertices(s)), (d, vertices(d)), ed)) }
    val mapped: Seq[(VertexId, A)] = (triplets map mapFunc).flatten
    val reduced = mapped.groupBy(_._1).map({case (v, msgs) => (v, msgs.map(_._2).reduce(reduceFunc))})
    Map() ++ reduced
  }
  def innerJoin[U: ClassTag, VD2: ClassTag](other: Map[VertexId, U])
                                              (f: (VertexId, VD, U) => VD2): Map[VertexId, VD2] = {
    val verts = vertices.keys.toSet.union(other.keys.toSet)
    Map() ++ verts.map(vertexId => (vertexId, f(vertexId, vertices(vertexId), other(vertexId))))
  }

  def outerJoinVertices[U: ClassTag, VD2: ClassTag](other: Map[VertexId, U])
                                                   (mapFunc: (VertexId, VD, Option[U]) => VD2): FakeSparkGraph[VD2] = {
    val newVertices = Map() ++ vertices.keys.map(vertexId => (vertexId, mapFunc(vertexId, vertices(vertexId), other.get(vertexId))))
    new FakeSparkGraph(newVertices, edges)
  }

  def pregel[A: ClassTag]
  (initialMsg: A,
   maxIter: Int = Int.MaxValue,
   activeDir: EdgeDirection = EdgeDirection.Out)
  (vprog: (VertexId, VD, A) => VD,
   sendMsg: Triplet[VD] => Iterator[(VertexId, A)],
   mergeMsg: (A, A) => A)
  : FakeSparkGraph[VD] = {
    // Receive the initial message at each vertex
    var g = mapVertices( (vid, vdata) => vprog(vid, vdata, initialMsg) )
    // compute the messages
    var messages = g.mapReduceTriplets(sendMsg, mergeMsg)
    var activeMessages = messages.size
    // Loop until no messages remain or maxIterations is achieved
    var i = 0
    while (activeMessages > 0 && i < maxIter) {
      // Receive the messages: -----------------------------------------------------------------------
      // Run the vertex program on all vertices that receive messages
      val newVerts = g.innerJoin(messages)(vprog)
      // Merge the new vertex values back into the graph
      g = g.outerJoinVertices(newVerts) { (vid, old, newOpt) => newOpt.getOrElse(old) }
      // Send Messages: ------------------------------------------------------------------------------
      // Vertices that didn't receive a message above don't appear in newVerts and therefore don't
      // get to send messages.  More precisely the map phase of mapReduceTriplets is only invoked
      // on edges in the activeDir of vertices in newVerts
      messages = g.mapReduceTriplets(sendMsg, mergeMsg/*, Some((newVerts, activeDir))*/)
      activeMessages = messages.size
      i += 1
    }
    g
  }
}

object FakeSparkGraph {
  def fromEdgeTuples[VD: ClassTag](rawEdges: Seq[(VertexId, VertexId)], defaultValue: VD) = {
    val edges = rawEdges.map(p => Edge(p._1, p._2, 1))
    val vertices = Map[VertexId, VD]() ++ (rawEdges.map(_._1) ++ rawEdges.map(_._2)).distinct.map((_, defaultValue))
    new FakeSparkGraph(vertices, edges)
  }
}
