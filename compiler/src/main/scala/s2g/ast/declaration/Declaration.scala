package s2g.ast.declaration

trait Declaration {
  def name: String
  def validate(): Unit
}