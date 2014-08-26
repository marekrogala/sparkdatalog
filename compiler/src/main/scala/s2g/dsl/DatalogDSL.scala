package s2g.dsl


object DatalogDSL {
  case class Relation(name: String){

  }
  class Arrow(receiver: Symbol) {
    def :-(value: Symbol) = {
      Seq(receiver, value)
    }
  }
  implicit def pimpArrow(arg: Symbol): Arrow = new Arrow(arg)
  /*def %(body: => Any): Map[String, Any] = {
    values.withValue(Map[String, Any]()) {
      body
      values.value
    }
  }
  def $(elements: Any*): List[Any] = {
    elements.toList
  }*/
}
