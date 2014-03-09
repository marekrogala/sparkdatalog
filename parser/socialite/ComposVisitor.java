package socialite;
import socialite.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  socialite.Absyn.Program.Visitor<socialite.Absyn.Program,A>,
  socialite.Absyn.Declaration.Visitor<socialite.Absyn.Declaration,A>,
  socialite.Absyn.MoreDimensionsDeclaration.Visitor<socialite.Absyn.MoreDimensionsDeclaration,A>,
  socialite.Absyn.ColumnDeclaration.Visitor<socialite.Absyn.ColumnDeclaration,A>,
  socialite.Absyn.TypeName.Visitor<socialite.Absyn.TypeName,A>,
  socialite.Absyn.Range.Visitor<socialite.Absyn.Range,A>,
  socialite.Absyn.Rule.Visitor<socialite.Absyn.Rule,A>,
  socialite.Absyn.RuleGoal.Visitor<socialite.Absyn.RuleGoal,A>,
  socialite.Absyn.Predicate.Visitor<socialite.Absyn.Predicate,A>,
  socialite.Absyn.OneGoal.Visitor<socialite.Absyn.OneGoal,A>,
  socialite.Absyn.Term.Visitor<socialite.Absyn.Term,A>,
  socialite.Absyn.Value.Visitor<socialite.Absyn.Value,A>,
  socialite.Absyn.Variable.Visitor<socialite.Absyn.Variable,A>,
  socialite.Absyn.Constant.Visitor<socialite.Absyn.Constant,A>,
  socialite.Absyn.CompOp.Visitor<socialite.Absyn.CompOp,A>,
  socialite.Absyn.Exp.Visitor<socialite.Absyn.Exp,A>
{
/* Program */
    public Program visit(socialite.Absyn.Prog p, A arg)
    {
      ListDeclaration listdeclaration_ = new ListDeclaration();
      for (Declaration x : p.listdeclaration_) {
        listdeclaration_.add(x.accept(this,arg));
      }
      ListRule listrule_ = new ListRule();
      for (Rule x : p.listrule_) {
        listrule_.add(x.accept(this,arg));
      }

      return new socialite.Absyn.Prog(listdeclaration_, listrule_);
    }

/* Declaration */
    public Declaration visit(socialite.Absyn.DeclarationDistributed p, A arg)
    {
      String uident_ = p.uident_;
      TypeName typename_ = p.typename_.accept(this, arg);
      Variable variable_ = p.variable_.accept(this, arg);
      Range range_ = p.range_.accept(this, arg);
      MoreDimensionsDeclaration moredimensionsdeclaration_ = p.moredimensionsdeclaration_.accept(this, arg);

      return new socialite.Absyn.DeclarationDistributed(uident_, typename_, variable_, range_, moredimensionsdeclaration_);
    }
    public Declaration visit(socialite.Absyn.DeclarationGlobal p, A arg)
    {
      String uident_ = p.uident_;
      MoreDimensionsDeclaration moredimensionsdeclaration_ = p.moredimensionsdeclaration_.accept(this, arg);

      return new socialite.Absyn.DeclarationGlobal(uident_, moredimensionsdeclaration_);
    }
    public Declaration visit(socialite.Absyn.DeclarationConst p, A arg)
    {
      TypeName typename_ = p.typename_.accept(this, arg);
      String uident_ = p.uident_;
      Exp exp_ = p.exp_.accept(this, arg);

      return new socialite.Absyn.DeclarationConst(typename_, uident_, exp_);
    }

/* MoreDimensionsDeclaration */
    public MoreDimensionsDeclaration visit(socialite.Absyn.NoMoreDim p, A arg)
    {

      return new socialite.Absyn.NoMoreDim();
    }
    public MoreDimensionsDeclaration visit(socialite.Absyn.MoreDim p, A arg)
    {
      ListColumnDeclaration listcolumndeclaration_ = new ListColumnDeclaration();
      for (ColumnDeclaration x : p.listcolumndeclaration_) {
        listcolumndeclaration_.add(x.accept(this,arg));
      }

      return new socialite.Absyn.MoreDim(listcolumndeclaration_);
    }

/* ColumnDeclaration */
    public ColumnDeclaration visit(socialite.Absyn.ColumnDecl p, A arg)
    {
      TypeName typename_ = p.typename_.accept(this, arg);
      Variable variable_ = p.variable_.accept(this, arg);

      return new socialite.Absyn.ColumnDecl(typename_, variable_);
    }

/* TypeName */
    public TypeName visit(socialite.Absyn.TypeInt p, A arg)
    {

      return new socialite.Absyn.TypeInt();
    }
    public TypeName visit(socialite.Absyn.TypeDouble p, A arg)
    {

      return new socialite.Absyn.TypeDouble();
    }

/* Range */
    public Range visit(socialite.Absyn.RangeDef p, A arg)
    {
      Exp exp_1 = p.exp_1.accept(this, arg);
      Exp exp_2 = p.exp_2.accept(this, arg);

      return new socialite.Absyn.RangeDef(exp_1, exp_2);
    }

/* Rule */
    public Rule visit(socialite.Absyn.RuleDef p, A arg)
    {
      Predicate predicate_ = p.predicate_.accept(this, arg);
      ListRuleGoal listrulegoal_ = new ListRuleGoal();
      for (RuleGoal x : p.listrulegoal_) {
        listrulegoal_.add(x.accept(this,arg));
      }

      return new socialite.Absyn.RuleDef(predicate_, listrulegoal_);
    }

/* RuleGoal */
    public RuleGoal visit(socialite.Absyn.RuleGoalDef p, A arg)
    {
      ListOneGoal listonegoal_ = new ListOneGoal();
      for (OneGoal x : p.listonegoal_) {
        listonegoal_.add(x.accept(this,arg));
      }

      return new socialite.Absyn.RuleGoalDef(listonegoal_);
    }

/* Predicate */
    public Predicate visit(socialite.Absyn.PredicateSingle p, A arg)
    {
      String uident_ = p.uident_;
      ListTerm listterm_ = new ListTerm();
      for (Term x : p.listterm_) {
        listterm_.add(x.accept(this,arg));
      }

      return new socialite.Absyn.PredicateSingle(uident_, listterm_);
    }
    public Predicate visit(socialite.Absyn.PredicateSharded p, A arg)
    {
      String uident_ = p.uident_;
      Value value_ = p.value_.accept(this, arg);
      ListTerm listterm_ = new ListTerm();
      for (Term x : p.listterm_) {
        listterm_.add(x.accept(this,arg));
      }

      return new socialite.Absyn.PredicateSharded(uident_, value_, listterm_);
    }

/* OneGoal */
    public OneGoal visit(socialite.Absyn.GoalPredicate p, A arg)
    {
      Predicate predicate_ = p.predicate_.accept(this, arg);

      return new socialite.Absyn.GoalPredicate(predicate_);
    }
    public OneGoal visit(socialite.Absyn.GoalComparison p, A arg)
    {
      Exp exp_1 = p.exp_1.accept(this, arg);
      CompOp compop_ = p.compop_.accept(this, arg);
      Exp exp_2 = p.exp_2.accept(this, arg);

      return new socialite.Absyn.GoalComparison(exp_1, compop_, exp_2);
    }
    public OneGoal visit(socialite.Absyn.GoalAssign p, A arg)
    {
      Variable variable_ = p.variable_.accept(this, arg);
      Exp exp_ = p.exp_.accept(this, arg);

      return new socialite.Absyn.GoalAssign(variable_, exp_);
    }

/* Term */
    public Term visit(socialite.Absyn.TermValue p, A arg)
    {
      Value value_ = p.value_.accept(this, arg);

      return new socialite.Absyn.TermValue(value_);
    }
    public Term visit(socialite.Absyn.TermCall p, A arg)
    {
      String dident_ = p.dident_;
      ListTerm listterm_ = new ListTerm();
      for (Term x : p.listterm_) {
        listterm_.add(x.accept(this,arg));
      }

      return new socialite.Absyn.TermCall(dident_, listterm_);
    }

/* Value */
    public Value visit(socialite.Absyn.ValueInt p, A arg)
    {
      Integer integer_ = p.integer_;

      return new socialite.Absyn.ValueInt(integer_);
    }
    public Value visit(socialite.Absyn.ValueVar p, A arg)
    {
      Variable variable_ = p.variable_.accept(this, arg);

      return new socialite.Absyn.ValueVar(variable_);
    }
    public Value visit(socialite.Absyn.ValueDouble p, A arg)
    {
      Double double_ = p.double_;

      return new socialite.Absyn.ValueDouble(double_);
    }
    public Value visit(socialite.Absyn.ValueConst p, A arg)
    {
      Constant constant_ = p.constant_.accept(this, arg);

      return new socialite.Absyn.ValueConst(constant_);
    }
    public Value visit(socialite.Absyn.ValueIgnore p, A arg)
    {

      return new socialite.Absyn.ValueIgnore();
    }

/* Variable */
    public Variable visit(socialite.Absyn.Var p, A arg)
    {
      String lident_ = p.lident_;

      return new socialite.Absyn.Var(lident_);
    }

/* Constant */
    public Constant visit(socialite.Absyn.Const p, A arg)
    {
      String uident_ = p.uident_;

      return new socialite.Absyn.Const(uident_);
    }

/* CompOp */
    public CompOp visit(socialite.Absyn.CompOpEq p, A arg)
    {

      return new socialite.Absyn.CompOpEq();
    }
    public CompOp visit(socialite.Absyn.CompOpGt p, A arg)
    {

      return new socialite.Absyn.CompOpGt();
    }
    public CompOp visit(socialite.Absyn.CompOpLt p, A arg)
    {

      return new socialite.Absyn.CompOpLt();
    }
    public CompOp visit(socialite.Absyn.CompOpGe p, A arg)
    {

      return new socialite.Absyn.CompOpGe();
    }
    public CompOp visit(socialite.Absyn.CompOpLe p, A arg)
    {

      return new socialite.Absyn.CompOpLe();
    }

/* Exp */
    public Exp visit(socialite.Absyn.EAdd p, A arg)
    {
      Exp exp_1 = p.exp_1.accept(this, arg);
      Exp exp_2 = p.exp_2.accept(this, arg);

      return new socialite.Absyn.EAdd(exp_1, exp_2);
    }
    public Exp visit(socialite.Absyn.ESub p, A arg)
    {
      Exp exp_1 = p.exp_1.accept(this, arg);
      Exp exp_2 = p.exp_2.accept(this, arg);

      return new socialite.Absyn.ESub(exp_1, exp_2);
    }
    public Exp visit(socialite.Absyn.EMul p, A arg)
    {
      Exp exp_1 = p.exp_1.accept(this, arg);
      Exp exp_2 = p.exp_2.accept(this, arg);

      return new socialite.Absyn.EMul(exp_1, exp_2);
    }
    public Exp visit(socialite.Absyn.EDiv p, A arg)
    {
      Exp exp_1 = p.exp_1.accept(this, arg);
      Exp exp_2 = p.exp_2.accept(this, arg);

      return new socialite.Absyn.EDiv(exp_1, exp_2);
    }
    public Exp visit(socialite.Absyn.EInt p, A arg)
    {
      Value value_ = p.value_.accept(this, arg);

      return new socialite.Absyn.EInt(value_);
    }

}