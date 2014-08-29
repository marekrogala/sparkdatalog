package pl.appsilon.marek.sparkdatalog.ast

class SemanticException(msg: String) extends Exception {
  override def toString = msg
}
