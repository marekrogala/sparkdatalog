package pl.appsilon.marek.sparkdatalog.ast

class SyntacticException(msg: String) extends Exception {
  override def toString = msg
}
