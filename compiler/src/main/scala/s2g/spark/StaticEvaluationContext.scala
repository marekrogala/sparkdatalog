package s2g.spark

import s2g.ast.declaration.Declaration

case class StaticEvaluationContext(tables: Seq[Declaration])
