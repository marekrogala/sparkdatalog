package s2g.astbuilder

import s2g.ast
import s2g.ast.comparisonoperator._
import s2g.ast.declaration
import socialite.Absyn._
import scala.collection.JavaConversions._

class Visitor[A]
  extends socialite.Absyn.Program.Visitor[ast.Program, A]
  with socialite.Absyn.Declaration.Visitor[ast.declaration.Declaration, A]
  with socialite.Absyn.MoreDimensionsDeclaration.Visitor[Seq[ast.declaration.ColumnDeclaration], A]
  with socialite.Absyn.ColumnDeclaration.Visitor[ast.declaration.ColumnDeclaration, A]
  with socialite.Absyn.TypeName.Visitor[ast.types.Type, A]
  with socialite.Absyn.Rule.Visitor[ast.rule.Rule, A]
  with socialite.Absyn.Head.Visitor[ast.rule.Head, A]
  with socialite.Absyn.RuleBody.Visitor[ast.rule.RuleBody, A]
  with socialite.Absyn.Predicate.Visitor[ast.predicate.Predicate, A]
  with socialite.Absyn.Subgoal.Visitor[ast.subgoal.Subgoal, A]
  with socialite.Absyn.Value.Visitor[ast.value.Value, A]
  with socialite.Absyn.Variable.Visitor[String, A]
  with socialite.Absyn.Exp.Visitor[ast.exp.Exp, A]
  with socialite.Absyn.CompOp.Visitor[ast.comparisonoperator.ComparisonOperator, A]
  with socialite.Absyn.AggregateSpecifier.Visitor[Option[ast.declaration.Function], A]
  with socialite.Absyn.Function.Visitor[ast.declaration.Function, A] {

  override def visit(p: Prog, arg: A): ast.Program =
    ast.Program(p.listdeclaration_.toList map (_.accept(this, arg)),
                p.listrule_.toList.map(_.accept(this, arg)).toSet)

  override def visit(p: MoreDim, arg: A): Seq[ast.declaration.ColumnDeclaration] =
    p.listcolumndeclaration_.toList map (_.accept(this, arg))

  override def visit(p: NoMoreDim, arg: A): Seq[ast.declaration.ColumnDeclaration] = Seq()

  override def visit(p: DeclarationGlobal, arg: A): ast.declaration.Declaration =
    ast.declaration.DeclarationGlobal(p.uident_, p.moredimensionsdeclaration_.accept(this, arg))

  override def visit(p: DeclarationConst, arg: A): ast.declaration.Declaration =
    ast.declaration.DeclarationConst(p.typename_.accept(this, arg), p.uident_, p.exp_.accept(this, arg))

  override def visit(p: Var, arg: A): String = p.lident_

  override def visit(p: ValueDouble, arg: A): ast.value.Value = ast.value.ValueLiteral(new ast.types.TypeDouble(), p.double_)

  override def visit(p: ValueVar, arg: A): ast.value.Value = ast.value.ValueVar(p.variable_.accept(this, arg))

  override def visit(p: ValueInt, arg: A): ast.value.Value = ast.value.ValueLiteral(new ast.types.TypeInt(), p.integer_)

  override def visit(p: ValueConst, arg: A): ast.value.Value = ast.value.ValueVar(p.uident_)

  override def visit(p: GoalAssign, arg: A): ast.subgoal.Subgoal =
    ast.subgoal.GoalAssign(p.variable_.accept(this, arg), p.exp_.accept(this, arg))

  override def visit(p: GoalPredicate, arg: A): ast.subgoal.Subgoal = ast.subgoal.GoalPredicate(p.predicate_.accept(this, arg))

  override def visit(p: GoalComparison, arg: A): ast.subgoal.Subgoal =
    ast.subgoal.GoalComparison(p.exp_1.accept(this, arg), p.exp_2.accept(this, arg), p.compop_.accept(this, arg))

  override def visit(p: PredicateSingle, arg: A): ast.predicate.Predicate =
    ast.predicate.PredicateSingle(p.uident_, p.listvalue_.toList map (_.accept(this, arg)))

  override def visit(p: RuleBodyDef, arg: A): ast.rule.RuleBody = ast.rule.RuleBody(p.listsubgoal_.toList map (_.accept(this, arg)))

  override def visit(p: ColumnDecl, arg: A): ast.declaration.ColumnDeclaration =
    ast.declaration.ColumnDeclaration(p.typename_.accept(this, arg), p.variable_.accept(this, arg), p.aggregatespecifier_.accept(this, arg))

  override def visit(p: TypeDouble, arg: A): ast.types.Type = new ast.types.TypeDouble()

  override def visit(p: TypeInt, arg: A): ast.types.Type = new ast.types.TypeInt()

  override def visit(p: RuleDef, arg: A): ast.rule.Rule =
    ast.rule.Rule(p.head_.accept(this, arg), p.listrulebody_.toList.map(_.accept(this, arg)).toSet)

  override def visit(p: HeadSingle, arg: A): ast.rule.Head =
    ast.rule.HeadSingle(p.uident_, p.listvariable_.toList map (_.accept(this, arg)))

  override def visit(p: EValue, arg: A): ast.exp.Exp = ast.exp.EValue(p.value_.accept(this, arg))

  override def visit(p: EDiv, arg: A): ast.exp.Exp = ???

  override def visit(p: EMul, arg: A): ast.exp.Exp = ???

  override def visit(p: ESub, arg: A): ast.exp.Exp = ???

  override def visit(p: EAdd, arg: A): ast.exp.Exp = ast.exp.EBinaryOp(p.exp_1.accept(this, arg), p.exp_2.accept(this, arg), ast.binaryoperator.BinaryAdd())

  override def visit(p: CompOpLe, arg: A): ComparisonOperator = CompareLe()

  override def visit(p: CompOpGe, arg: A): ComparisonOperator = CompareGe()

  override def visit(p: CompOpLt, arg: A): ComparisonOperator = CompareLt()

  override def visit(p: CompOpGt, arg: A): ComparisonOperator = CompareGt()

  override def visit(p: CompOpEq, arg: A): ComparisonOperator = CompareEq()

  override def visit(p: CompOpNe, arg: A): ComparisonOperator = CompareNe()

  override def visit(p: Func, arg: A): declaration.Function = declaration.Function(p.uident_)

  override def visit(p: AggregateWith, arg: A): Option[declaration.Function] = Some(p.function_.accept(this, arg))

  override def visit(p: NoAggregation, arg: A): Option[declaration.Function] = None
}
