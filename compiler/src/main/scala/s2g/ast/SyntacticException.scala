package s2g.ast

class SyntacticException(msg: String) extends Exception {
  override def toString = msg
}
