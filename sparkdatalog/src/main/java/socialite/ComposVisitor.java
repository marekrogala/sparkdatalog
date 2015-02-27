package socialite;
import socialite.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  socialite.Absyn.Program.Visitor<socialite.Absyn.Program,A>,
  socialite.Absyn.Declaration.Visitor<socialite.Absyn.Declaration,A>,
  socialite.Absyn.MoreDimensionsDeclaration.Visitor<socialite.Absyn.MoreDimensionsDeclaration,A>,
  socialite.Absyn.ColumnDeclaration.Visitor<socialite.Absyn.ColumnDeclaration,A>,
  socialite.Absyn.AggregateSpecifier.Visitor<socialite.Absyn.AggregateSpecifier,A>,
  socialite.Absyn.TypeName.Visitor<socialite.Absyn.TypeName,A>,
  socialite.Absyn.Rule.Visitor<socialite.Absyn.Rule,A>,
  socialite.Absyn.Head.Visitor<socialite.Absyn.Head,A>,
  socialite.Absyn.RuleBody.Visitor<socialite.Absyn.RuleBody,A>,
  socialite.Absyn.Predicate.Visitor<socialite.Absyn.Predicate,A>,
  socialite.Absyn.Subgoal.Visitor<socialite.Absyn.Subgoal,A>,
  socialite.Absyn.Value.Visitor<socialite.Absyn.Value,A>,
  socialite.Absyn.Variable.Visitor<socialite.Absyn.Variable,A>,
  socialite.Absyn.Function.Visitor<socialite.Absyn.Function,A>,
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
      AggregateSpecifier aggregatespecifier_ = p.aggregatespecifier_.accept(this, arg);

      return new socialite.Absyn.ColumnDecl(typename_, variable_, aggregatespecifier_);
    }

/* AggregateSpecifier */
    public AggregateSpecifier visit(socialite.Absyn.AggregateWith p, A arg)
    {
      Function function_ = p.function_.accept(this, arg);

      return new socialite.Absyn.AggregateWith(function_);
    }
    public AggregateSpecifier visit(socialite.Absyn.NoAggregation p, A arg)
    {

      return new socialite.Absyn.NoAggregation();
    }

/* TypeName */
    public TypeName visit(socialite.Absyn.TypeInt p, A arg)
    {

      return new socialite.Absyn.TypeInt();
    }

/* Rule */
    public Rule visit(socialite.Absyn.RuleDef p, A arg)
    {
      Head head_ = p.head_.accept(this, arg);
      RuleBody rulebody_ = p.rulebody_.accept(this, arg);

      return new socialite.Absyn.RuleDef(head_, rulebody_);
    }

/* Head */
    public Head visit(socialite.Absyn.HeadSingle p, A arg)
    {
      String uident_ = p.uident_;
      ListVariable listvariable_ = new ListVariable();
      for (Variable x : p.listvariable_) {
        listvariable_.add(x.accept(this,arg));
      }

      return new socialite.Absyn.HeadSingle(uident_, listvariable_);
    }

/* RuleBody */
    public RuleBody visit(socialite.Absyn.RuleBodyDef p, A arg)
    {
      ListSubgoal listsubgoal_ = new ListSubgoal();
      for (Subgoal x : p.listsubgoal_) {
        listsubgoal_.add(x.accept(this,arg));
      }

      return new socialite.Absyn.RuleBodyDef(listsubgoal_);
    }

/* Predicate */
    public Predicate visit(socialite.Absyn.PredicateSingle p, A arg)
    {
      String uident_ = p.uident_;
      ListValue listvalue_ = new ListValue();
      for (Value x : p.listvalue_) {
        listvalue_.add(x.accept(this,arg));
      }

      return new socialite.Absyn.PredicateSingle(uident_, listvalue_);
    }

/* Subgoal */
    public Subgoal visit(socialite.Absyn.GoalPredicate p, A arg)
    {
      Predicate predicate_ = p.predicate_.accept(this, arg);

      return new socialite.Absyn.GoalPredicate(predicate_);
    }
    public Subgoal visit(socialite.Absyn.GoalComparison p, A arg)
    {
      Exp exp_1 = p.exp_1.accept(this, arg);
      CompOp compop_ = p.compop_.accept(this, arg);
      Exp exp_2 = p.exp_2.accept(this, arg);

      return new socialite.Absyn.GoalComparison(exp_1, compop_, exp_2);
    }
    public Subgoal visit(socialite.Absyn.GoalAssign p, A arg)
    {
      Variable variable_ = p.variable_.accept(this, arg);
      Exp exp_ = p.exp_.accept(this, arg);

      return new socialite.Absyn.GoalAssign(variable_, exp_);
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
      String uident_ = p.uident_;

      return new socialite.Absyn.ValueConst(uident_);
    }

/* Variable */
    public Variable visit(socialite.Absyn.Var p, A arg)
    {
      String lident_ = p.lident_;

      return new socialite.Absyn.Var(lident_);
    }

/* Function */
    public Function visit(socialite.Absyn.Func p, A arg)
    {
      String uident_ = p.uident_;

      return new socialite.Absyn.Func(uident_);
    }

/* CompOp */
    public CompOp visit(socialite.Absyn.CompOpEq p, A arg)
    {

      return new socialite.Absyn.CompOpEq();
    }
    public CompOp visit(socialite.Absyn.CompOpNe p, A arg)
    {

      return new socialite.Absyn.CompOpNe();
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
    public Exp visit(socialite.Absyn.EValue p, A arg)
    {
      Value value_ = p.value_.accept(this, arg);

      return new socialite.Absyn.EValue(value_);
    }

}