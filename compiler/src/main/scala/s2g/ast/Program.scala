package s2g.ast

import s2g.ast.declaration.Declaration
import s2g.ast.rule.Rule

case class Program(declarations: Seq[Declaration], rules: Seq[Rule]) {

}
