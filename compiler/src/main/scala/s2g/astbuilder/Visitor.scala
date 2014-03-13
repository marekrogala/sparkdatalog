package s2g.astbuilder

import s2g.ast
import socialite.Absyn._
import scala.collection.JavaConversions._

class Visitor[A]
  extends socialite.Absyn.Program.Visitor[ast.Program, A]
  with socialite.Absyn.Declaration.Visitor[ast.Declaration, A]
  with socialite.Absyn.MoreDimensionsDeclaration.Visitor[Seq[ast.ColumnDeclaration], A]
  with socialite.Absyn.ColumnDeclaration.Visitor[ast.ColumnDeclaration, A]
  with socialite.Absyn.TypeName.Visitor[ast.Type, A]
  with socialite.Absyn.Rule.Visitor[ast.Rule, A]
  with socialite.Absyn.Head.Visitor[ast.Head, A]
  with socialite.Absyn.RuleBody.Visitor[ast.RuleBody, A]
  with socialite.Absyn.Predicate.Visitor[ast.Predicate, A]
  with socialite.Absyn.Subgoal.Visitor[ast.Subgoal, A]
  with socialite.Absyn.Value.Visitor[ast.Value, A]
  with socialite.Absyn.Variable.Visitor[String, A]
  with socialite.Absyn.Exp.Visitor[ast.Exp, A]{


  override def visit(p: Prog, arg: A): ast.Program =
    ast.Program(p.listdeclaration_.toList map (_.accept(this, arg)),
                p.listrule_.toList map (_.accept(this, arg)))

  override def visit(p: MoreDim, arg: A): Seq[ast.ColumnDeclaration] =
    p.listcolumndeclaration_.toList map (_.accept(this, arg))

  override def visit(p: NoMoreDim, arg: A): Seq[ast.ColumnDeclaration] = Seq()

  override def visit(p: DeclarationGlobal, arg: A): ast.Declaration =
    ast.DeclarationGlobal(p.uident_, p.moredimensionsdeclaration_.accept(this, arg))

  override def visit(p: Var, arg: A): String = p.lident_

  override def visit(p: ValueDouble, arg: A): ast.Value = ast.ValueLiteral(new ast.TypeDouble(), p.double_)

  override def visit(p: ValueVar, arg: A): ast.Value = ast.ValueVar(p.variable_.accept(this, arg))

  override def visit(p: ValueInt, arg: A): ast.Value = ast.ValueLiteral(new ast.TypeInt(), p.integer_)

  override def visit(p: GoalAssign, arg: A): ast.Subgoal =
    ast.GoalAssign(p.variable_.accept(this, arg), p.exp_.accept(this, arg)
  )

  override def visit(p: GoalPredicate, arg: A): ast.Subgoal = ast.GoalPredicate(p.predicate_.accept(this, arg))

  override def visit(p: PredicateSingle, arg: A): ast.Predicate =
    ast.PredicateSingle(p.uident_, p.listvalue_.toList map (_.accept(this, arg)))

  override def visit(p: RuleBodyDef, arg: A): ast.RuleBody = ast.RuleBody(p.listsubgoal_.toList map (_.accept(this, arg)))

  override def visit(p: ColumnDecl, arg: A): ast.ColumnDeclaration =
    ast.ColumnDeclaration(p.typename_.accept(this, arg), p.variable_.accept(this, arg))

  override def visit(p: TypeDouble, arg: A): ast.Type = new ast.TypeDouble()

  override def visit(p: TypeInt, arg: A): ast.Type = new ast.TypeInt()

  override def visit(p: RuleDef, arg: A): ast.Rule =
    ast.Rule(p.head_.accept(this, arg), p.listrulebody_.toList map (_.accept(this, arg)))

  override def visit(p: HeadSingle, arg: A): ast.Head =
    ast.HeadSingle(p.uident_, p.listvariable_.toList map (_.accept(this, arg)))

  override def visit(p: EValue, arg: A): ast.Exp = ast.EValue(p.value_.accept(this, arg))

  override def visit(p: EDiv, arg: A): ast.Exp = ???

  override def visit(p: EMul, arg: A): ast.Exp = ???

  override def visit(p: ESub, arg: A): ast.Exp = ???

  override def visit(p: EAdd, arg: A): ast.Exp = ast.EBinaryOp(p.exp_1.accept(this, arg), p.exp_2.accept(this, arg), ast.BinaryAdd())
}
