package s2g.eval

case class LanguageError(msg: String) extends Exception {
  override def toString = msg
}
