package pl.appsilon.marek.sparkdatalog.ast.declaration

import pl.appsilon.marek.sparkdatalog.ast.SemanticException
import pl.appsilon.marek.sparkdatalog.ast.value.ValueLiteral

case class Function(name: String) {

  def toScalaIntFunction: (Int, Int) => Int = {
    name match {
      case "Max" => math.max
      case "Min" => math.min
      case "Sum" => _ + _ // TODO: error if used in recurence
      case _ => throw new SemanticException("Unknown aggregate function '%s', available functions are 'Min', 'Max' and 'Sum'".format(name))
    }
  }

}
