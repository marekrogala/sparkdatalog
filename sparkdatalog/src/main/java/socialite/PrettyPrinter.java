package socialite;
import socialite.Absyn.*;

public class PrettyPrinter
{
  //For certain applications increasing the initial size of the buffer may improve performance.
  private static final int INITIAL_BUFFER_SIZE = 128;
  //You may wish to change the parentheses used in precedence.
  private static final String _L_PAREN = new String("(");
  private static final String _R_PAREN = new String(")");
  //You may wish to change render
  private static void render(String s)
  {
    if (s.equals("{"))
    {
       buf_.append("\n");
       indent();
       buf_.append(s);
       _n_ = _n_ + 2;
       buf_.append("\n");
       indent();
    }
    else if (s.equals("(") || s.equals("["))
       buf_.append(s);
    else if (s.equals(")") || s.equals("]"))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals("}"))
    {
       _n_ = _n_ - 2;
       backup();
       backup();
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals(","))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals(";"))
    {
       backup();
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals("")) return;
    else
    {
       buf_.append(s);
       buf_.append(" ");
    }
  }


  //  print and show methods are defined for each category.
  public static String print(socialite.Absyn.Program foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Program foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.Declaration foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Declaration foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.ListDeclaration foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.ListDeclaration foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.MoreDimensionsDeclaration foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.MoreDimensionsDeclaration foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.ColumnDeclaration foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.ColumnDeclaration foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.ListColumnDeclaration foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.ListColumnDeclaration foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.AggregateSpecifier foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.AggregateSpecifier foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.TypeName foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.TypeName foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.Rule foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Rule foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.ListRule foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.ListRule foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.Head foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Head foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.RuleBody foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.RuleBody foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.Predicate foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Predicate foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.Subgoal foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Subgoal foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.ListSubgoal foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.ListSubgoal foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.Value foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Value foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.ListValue foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.ListValue foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.Variable foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Variable foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.ListVariable foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.ListVariable foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.Function foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Function foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.CompOp foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.CompOp foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.Exp foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Exp foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(socialite.Absyn.Program foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.Prog)
    {
       socialite.Absyn.Prog _prog = (socialite.Absyn.Prog) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_prog.listdeclaration_, 0);
       pp(_prog.listrule_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.Declaration foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.DeclarationGlobal)
    {
       socialite.Absyn.DeclarationGlobal _declarationglobal = (socialite.Absyn.DeclarationGlobal) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("declare");
       pp(_declarationglobal.uident_, 0);
       pp(_declarationglobal.moredimensionsdeclaration_, 0);
       render(".");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.DeclarationConst)
    {
       socialite.Absyn.DeclarationConst _declarationconst = (socialite.Absyn.DeclarationConst) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("const");
       pp(_declarationconst.typename_, 0);
       pp(_declarationconst.uident_, 0);
       render("=");
       pp(_declarationconst.exp_, 0);
       render(".");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.ListDeclaration foo, int _i_)
  {
     for (java.util.Iterator<Declaration> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(socialite.Absyn.MoreDimensionsDeclaration foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.NoMoreDim)
    {
       socialite.Absyn.NoMoreDim _nomoredim = (socialite.Absyn.NoMoreDim) foo;
       if (_i_ > 0) render(_L_PAREN);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.MoreDim)
    {
       socialite.Absyn.MoreDim _moredim = (socialite.Absyn.MoreDim) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_moredim.listcolumndeclaration_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.ColumnDeclaration foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.ColumnDecl)
    {
       socialite.Absyn.ColumnDecl _columndecl = (socialite.Absyn.ColumnDecl) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_columndecl.typename_, 0);
       pp(_columndecl.variable_, 0);
       pp(_columndecl.aggregatespecifier_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.ListColumnDeclaration foo, int _i_)
  {
     for (java.util.Iterator<ColumnDeclaration> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(socialite.Absyn.AggregateSpecifier foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.AggregateWith)
    {
       socialite.Absyn.AggregateWith _aggregatewith = (socialite.Absyn.AggregateWith) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("aggregate");
       pp(_aggregatewith.function_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.NoAggregation)
    {
       socialite.Absyn.NoAggregation _noaggregation = (socialite.Absyn.NoAggregation) foo;
       if (_i_ > 0) render(_L_PAREN);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.TypeName foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.TypeInt)
    {
       socialite.Absyn.TypeInt _typeint = (socialite.Absyn.TypeInt) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("int");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.Rule foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.RuleDef)
    {
       socialite.Absyn.RuleDef _ruledef = (socialite.Absyn.RuleDef) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_ruledef.head_, 0);
       pp(_ruledef.rulebody_, 0);
       render(".");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.ListRule foo, int _i_)
  {
     for (java.util.Iterator<Rule> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render("");
       } else {
         render("");
       }
     }
  }

  private static void pp(socialite.Absyn.Head foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.HeadSingle)
    {
       socialite.Absyn.HeadSingle _headsingle = (socialite.Absyn.HeadSingle) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_headsingle.uident_, 0);
       render("(");
       pp(_headsingle.listvariable_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.RuleBody foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.RuleBodyDef)
    {
       socialite.Absyn.RuleBodyDef _rulebodydef = (socialite.Absyn.RuleBodyDef) foo;
       if (_i_ > 0) render(_L_PAREN);
       render(":-");
       pp(_rulebodydef.listsubgoal_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.Predicate foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.PredicateSingle)
    {
       socialite.Absyn.PredicateSingle _predicatesingle = (socialite.Absyn.PredicateSingle) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_predicatesingle.uident_, 0);
       render("(");
       pp(_predicatesingle.listvalue_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.Subgoal foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.GoalPredicate)
    {
       socialite.Absyn.GoalPredicate _goalpredicate = (socialite.Absyn.GoalPredicate) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_goalpredicate.predicate_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.GoalComparison)
    {
       socialite.Absyn.GoalComparison _goalcomparison = (socialite.Absyn.GoalComparison) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_goalcomparison.exp_1, 0);
       pp(_goalcomparison.compop_, 0);
       pp(_goalcomparison.exp_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.GoalAssign)
    {
       socialite.Absyn.GoalAssign _goalassign = (socialite.Absyn.GoalAssign) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_goalassign.variable_, 0);
       render("=");
       pp(_goalassign.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.ListSubgoal foo, int _i_)
  {
     for (java.util.Iterator<Subgoal> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(socialite.Absyn.Value foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.ValueInt)
    {
       socialite.Absyn.ValueInt _valueint = (socialite.Absyn.ValueInt) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_valueint.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.ValueVar)
    {
       socialite.Absyn.ValueVar _valuevar = (socialite.Absyn.ValueVar) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_valuevar.variable_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.ValueDouble)
    {
       socialite.Absyn.ValueDouble _valuedouble = (socialite.Absyn.ValueDouble) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_valuedouble.double_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.ValueConst)
    {
       socialite.Absyn.ValueConst _valueconst = (socialite.Absyn.ValueConst) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_valueconst.uident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.ListValue foo, int _i_)
  {
     for (java.util.Iterator<Value> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(socialite.Absyn.Variable foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.Var)
    {
       socialite.Absyn.Var _var = (socialite.Absyn.Var) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_var.lident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.ListVariable foo, int _i_)
  {
     for (java.util.Iterator<Variable> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(socialite.Absyn.Function foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.Func)
    {
       socialite.Absyn.Func _func = (socialite.Absyn.Func) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_func.uident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.CompOp foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.CompOpEq)
    {
       socialite.Absyn.CompOpEq _compopeq = (socialite.Absyn.CompOpEq) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("==");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.CompOpNe)
    {
       socialite.Absyn.CompOpNe _compopne = (socialite.Absyn.CompOpNe) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("!=");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.CompOpGt)
    {
       socialite.Absyn.CompOpGt _compopgt = (socialite.Absyn.CompOpGt) foo;
       if (_i_ > 0) render(_L_PAREN);
       render(">");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.CompOpLt)
    {
       socialite.Absyn.CompOpLt _compoplt = (socialite.Absyn.CompOpLt) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("<");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.CompOpGe)
    {
       socialite.Absyn.CompOpGe _compopge = (socialite.Absyn.CompOpGe) foo;
       if (_i_ > 0) render(_L_PAREN);
       render(">=");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.CompOpLe)
    {
       socialite.Absyn.CompOpLe _compople = (socialite.Absyn.CompOpLe) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("<=");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.Exp foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.EAdd)
    {
       socialite.Absyn.EAdd _eadd = (socialite.Absyn.EAdd) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_eadd.exp_1, 0);
       render("+");
       pp(_eadd.exp_2, 1);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.ESub)
    {
       socialite.Absyn.ESub _esub = (socialite.Absyn.ESub) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_esub.exp_1, 0);
       render("-");
       pp(_esub.exp_2, 1);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.EMul)
    {
       socialite.Absyn.EMul _emul = (socialite.Absyn.EMul) foo;
       if (_i_ > 1) render(_L_PAREN);
       pp(_emul.exp_1, 1);
       render("*");
       pp(_emul.exp_2, 2);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.EDiv)
    {
       socialite.Absyn.EDiv _ediv = (socialite.Absyn.EDiv) foo;
       if (_i_ > 1) render(_L_PAREN);
       pp(_ediv.exp_1, 1);
       render("/");
       pp(_ediv.exp_2, 2);
       if (_i_ > 1) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.EValue)
    {
       socialite.Absyn.EValue _evalue = (socialite.Absyn.EValue) foo;
       if (_i_ > 2) render(_L_PAREN);
       pp(_evalue.value_, 0);
       if (_i_ > 2) render(_R_PAREN);
    }
  }


  private static void sh(socialite.Absyn.Program foo)
  {
    if (foo instanceof socialite.Absyn.Prog)
    {
       socialite.Absyn.Prog _prog = (socialite.Absyn.Prog) foo;
       render("(");
       render("Prog");
       render("[");
       sh(_prog.listdeclaration_);
       render("]");
       render("[");
       sh(_prog.listrule_);
       render("]");
       render(")");
    }
  }

  private static void sh(socialite.Absyn.Declaration foo)
  {
    if (foo instanceof socialite.Absyn.DeclarationGlobal)
    {
       socialite.Absyn.DeclarationGlobal _declarationglobal = (socialite.Absyn.DeclarationGlobal) foo;
       render("(");
       render("DeclarationGlobal");
       sh(_declarationglobal.uident_);
       sh(_declarationglobal.moredimensionsdeclaration_);
       render(")");
    }
    if (foo instanceof socialite.Absyn.DeclarationConst)
    {
       socialite.Absyn.DeclarationConst _declarationconst = (socialite.Absyn.DeclarationConst) foo;
       render("(");
       render("DeclarationConst");
       sh(_declarationconst.typename_);
       sh(_declarationconst.uident_);
       sh(_declarationconst.exp_);
       render(")");
    }
  }

  private static void sh(socialite.Absyn.ListDeclaration foo)
  {
     for (java.util.Iterator<Declaration> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(socialite.Absyn.MoreDimensionsDeclaration foo)
  {
    if (foo instanceof socialite.Absyn.NoMoreDim)
    {
       socialite.Absyn.NoMoreDim _nomoredim = (socialite.Absyn.NoMoreDim) foo;
       render("NoMoreDim");
    }
    if (foo instanceof socialite.Absyn.MoreDim)
    {
       socialite.Absyn.MoreDim _moredim = (socialite.Absyn.MoreDim) foo;
       render("(");
       render("MoreDim");
       render("[");
       sh(_moredim.listcolumndeclaration_);
       render("]");
       render(")");
    }
  }

  private static void sh(socialite.Absyn.ColumnDeclaration foo)
  {
    if (foo instanceof socialite.Absyn.ColumnDecl)
    {
       socialite.Absyn.ColumnDecl _columndecl = (socialite.Absyn.ColumnDecl) foo;
       render("(");
       render("ColumnDecl");
       sh(_columndecl.typename_);
       sh(_columndecl.variable_);
       sh(_columndecl.aggregatespecifier_);
       render(")");
    }
  }

  private static void sh(socialite.Absyn.ListColumnDeclaration foo)
  {
     for (java.util.Iterator<ColumnDeclaration> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(socialite.Absyn.AggregateSpecifier foo)
  {
    if (foo instanceof socialite.Absyn.AggregateWith)
    {
       socialite.Absyn.AggregateWith _aggregatewith = (socialite.Absyn.AggregateWith) foo;
       render("(");
       render("AggregateWith");
       sh(_aggregatewith.function_);
       render(")");
    }
    if (foo instanceof socialite.Absyn.NoAggregation)
    {
       socialite.Absyn.NoAggregation _noaggregation = (socialite.Absyn.NoAggregation) foo;
       render("NoAggregation");
    }
  }

  private static void sh(socialite.Absyn.TypeName foo)
  {
    if (foo instanceof socialite.Absyn.TypeInt)
    {
       socialite.Absyn.TypeInt _typeint = (socialite.Absyn.TypeInt) foo;
       render("TypeInt");
    }
  }

  private static void sh(socialite.Absyn.Rule foo)
  {
    if (foo instanceof socialite.Absyn.RuleDef)
    {
       socialite.Absyn.RuleDef _ruledef = (socialite.Absyn.RuleDef) foo;
       render("(");
       render("RuleDef");
       sh(_ruledef.head_);
       sh(_ruledef.rulebody_);
       render(")");
    }
  }

  private static void sh(socialite.Absyn.ListRule foo)
  {
     for (java.util.Iterator<Rule> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(socialite.Absyn.Head foo)
  {
    if (foo instanceof socialite.Absyn.HeadSingle)
    {
       socialite.Absyn.HeadSingle _headsingle = (socialite.Absyn.HeadSingle) foo;
       render("(");
       render("HeadSingle");
       sh(_headsingle.uident_);
       render("[");
       sh(_headsingle.listvariable_);
       render("]");
       render(")");
    }
  }

  private static void sh(socialite.Absyn.RuleBody foo)
  {
    if (foo instanceof socialite.Absyn.RuleBodyDef)
    {
       socialite.Absyn.RuleBodyDef _rulebodydef = (socialite.Absyn.RuleBodyDef) foo;
       render("(");
       render("RuleBodyDef");
       render("[");
       sh(_rulebodydef.listsubgoal_);
       render("]");
       render(")");
    }
  }

  private static void sh(socialite.Absyn.Predicate foo)
  {
    if (foo instanceof socialite.Absyn.PredicateSingle)
    {
       socialite.Absyn.PredicateSingle _predicatesingle = (socialite.Absyn.PredicateSingle) foo;
       render("(");
       render("PredicateSingle");
       sh(_predicatesingle.uident_);
       render("[");
       sh(_predicatesingle.listvalue_);
       render("]");
       render(")");
    }
  }

  private static void sh(socialite.Absyn.Subgoal foo)
  {
    if (foo instanceof socialite.Absyn.GoalPredicate)
    {
       socialite.Absyn.GoalPredicate _goalpredicate = (socialite.Absyn.GoalPredicate) foo;
       render("(");
       render("GoalPredicate");
       sh(_goalpredicate.predicate_);
       render(")");
    }
    if (foo instanceof socialite.Absyn.GoalComparison)
    {
       socialite.Absyn.GoalComparison _goalcomparison = (socialite.Absyn.GoalComparison) foo;
       render("(");
       render("GoalComparison");
       sh(_goalcomparison.exp_1);
       sh(_goalcomparison.compop_);
       sh(_goalcomparison.exp_2);
       render(")");
    }
    if (foo instanceof socialite.Absyn.GoalAssign)
    {
       socialite.Absyn.GoalAssign _goalassign = (socialite.Absyn.GoalAssign) foo;
       render("(");
       render("GoalAssign");
       sh(_goalassign.variable_);
       sh(_goalassign.exp_);
       render(")");
    }
  }

  private static void sh(socialite.Absyn.ListSubgoal foo)
  {
     for (java.util.Iterator<Subgoal> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(socialite.Absyn.Value foo)
  {
    if (foo instanceof socialite.Absyn.ValueInt)
    {
       socialite.Absyn.ValueInt _valueint = (socialite.Absyn.ValueInt) foo;
       render("(");
       render("ValueInt");
       sh(_valueint.integer_);
       render(")");
    }
    if (foo instanceof socialite.Absyn.ValueVar)
    {
       socialite.Absyn.ValueVar _valuevar = (socialite.Absyn.ValueVar) foo;
       render("(");
       render("ValueVar");
       sh(_valuevar.variable_);
       render(")");
    }
    if (foo instanceof socialite.Absyn.ValueDouble)
    {
       socialite.Absyn.ValueDouble _valuedouble = (socialite.Absyn.ValueDouble) foo;
       render("(");
       render("ValueDouble");
       sh(_valuedouble.double_);
       render(")");
    }
    if (foo instanceof socialite.Absyn.ValueConst)
    {
       socialite.Absyn.ValueConst _valueconst = (socialite.Absyn.ValueConst) foo;
       render("(");
       render("ValueConst");
       sh(_valueconst.uident_);
       render(")");
    }
  }

  private static void sh(socialite.Absyn.ListValue foo)
  {
     for (java.util.Iterator<Value> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(socialite.Absyn.Variable foo)
  {
    if (foo instanceof socialite.Absyn.Var)
    {
       socialite.Absyn.Var _var = (socialite.Absyn.Var) foo;
       render("(");
       render("Var");
       sh(_var.lident_);
       render(")");
    }
  }

  private static void sh(socialite.Absyn.ListVariable foo)
  {
     for (java.util.Iterator<Variable> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(socialite.Absyn.Function foo)
  {
    if (foo instanceof socialite.Absyn.Func)
    {
       socialite.Absyn.Func _func = (socialite.Absyn.Func) foo;
       render("(");
       render("Func");
       sh(_func.uident_);
       render(")");
    }
  }

  private static void sh(socialite.Absyn.CompOp foo)
  {
    if (foo instanceof socialite.Absyn.CompOpEq)
    {
       socialite.Absyn.CompOpEq _compopeq = (socialite.Absyn.CompOpEq) foo;
       render("CompOpEq");
    }
    if (foo instanceof socialite.Absyn.CompOpNe)
    {
       socialite.Absyn.CompOpNe _compopne = (socialite.Absyn.CompOpNe) foo;
       render("CompOpNe");
    }
    if (foo instanceof socialite.Absyn.CompOpGt)
    {
       socialite.Absyn.CompOpGt _compopgt = (socialite.Absyn.CompOpGt) foo;
       render("CompOpGt");
    }
    if (foo instanceof socialite.Absyn.CompOpLt)
    {
       socialite.Absyn.CompOpLt _compoplt = (socialite.Absyn.CompOpLt) foo;
       render("CompOpLt");
    }
    if (foo instanceof socialite.Absyn.CompOpGe)
    {
       socialite.Absyn.CompOpGe _compopge = (socialite.Absyn.CompOpGe) foo;
       render("CompOpGe");
    }
    if (foo instanceof socialite.Absyn.CompOpLe)
    {
       socialite.Absyn.CompOpLe _compople = (socialite.Absyn.CompOpLe) foo;
       render("CompOpLe");
    }
  }

  private static void sh(socialite.Absyn.Exp foo)
  {
    if (foo instanceof socialite.Absyn.EAdd)
    {
       socialite.Absyn.EAdd _eadd = (socialite.Absyn.EAdd) foo;
       render("(");
       render("EAdd");
       sh(_eadd.exp_1);
       sh(_eadd.exp_2);
       render(")");
    }
    if (foo instanceof socialite.Absyn.ESub)
    {
       socialite.Absyn.ESub _esub = (socialite.Absyn.ESub) foo;
       render("(");
       render("ESub");
       sh(_esub.exp_1);
       sh(_esub.exp_2);
       render(")");
    }
    if (foo instanceof socialite.Absyn.EMul)
    {
       socialite.Absyn.EMul _emul = (socialite.Absyn.EMul) foo;
       render("(");
       render("EMul");
       sh(_emul.exp_1);
       sh(_emul.exp_2);
       render(")");
    }
    if (foo instanceof socialite.Absyn.EDiv)
    {
       socialite.Absyn.EDiv _ediv = (socialite.Absyn.EDiv) foo;
       render("(");
       render("EDiv");
       sh(_ediv.exp_1);
       sh(_ediv.exp_2);
       render(")");
    }
    if (foo instanceof socialite.Absyn.EValue)
    {
       socialite.Absyn.EValue _evalue = (socialite.Absyn.EValue) foo;
       render("(");
       render("EValue");
       sh(_evalue.value_);
       render(")");
    }
  }


  private static void pp(Integer n, int _i_) { buf_.append(n); buf_.append(" "); }
  private static void pp(Double d, int _i_) { buf_.append(d); buf_.append(" "); }
  private static void pp(String s, int _i_) { buf_.append(s); buf_.append(" "); }
  private static void pp(Character c, int _i_) { buf_.append("'" + c.toString() + "'"); buf_.append(" "); }
  private static void sh(Integer n) { render(n.toString()); }
  private static void sh(Double d) { render(d.toString()); }
  private static void sh(Character c) { render(c.toString()); }
  private static void sh(String s) { printQuoted(s); }
  private static void printQuoted(String s) { render("\"" + s + "\""); }
  private static void indent()
  {
    int n = _n_;
    while (n > 0)
    {
      buf_.append(" ");
      n--;
    }
  }
  private static void backup()
  {
     if (buf_.charAt(buf_.length() - 1) == ' ') {
      buf_.setLength(buf_.length() - 1);
    }
  }
  private static void trim()
  {
     while (buf_.length() > 0 && buf_.charAt(0) == ' ')
        buf_.deleteCharAt(0); 
    while (buf_.length() > 0 && buf_.charAt(buf_.length()-1) == ' ')
        buf_.deleteCharAt(buf_.length()-1);
  }
  private static int _n_ = 0;
  private static StringBuilder buf_ = new StringBuilder(INITIAL_BUFFER_SIZE);
}

