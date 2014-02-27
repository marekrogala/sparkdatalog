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
  public static String print(socialite.Absyn.Range foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Range foo)
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
  public static String print(socialite.Absyn.RuleGoal foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.RuleGoal foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.ListRuleGoal foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.ListRuleGoal foo)
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
  public static String print(socialite.Absyn.OneGoal foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.OneGoal foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.ListOneGoal foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.ListOneGoal foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.Term foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Term foo)
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
  public static String print(socialite.Absyn.ListTerm foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.ListTerm foo)
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
  public static String print(socialite.Absyn.Constant foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Constant foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.Atom foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Atom foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.Structure foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Structure foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(socialite.Absyn.Equation foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(socialite.Absyn.Equation foo)
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
    if (foo instanceof socialite.Absyn.DeclarationDistributed)
    {
       socialite.Absyn.DeclarationDistributed _declarationdistributed = (socialite.Absyn.DeclarationDistributed) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("declare");
       pp(_declarationdistributed.uident_, 0);
       render("[");
       pp(_declarationdistributed.typename_, 0);
       pp(_declarationdistributed.variable_, 0);
       render(":");
       pp(_declarationdistributed.range_, 0);
       render("]");
       pp(_declarationdistributed.moredimensionsdeclaration_, 0);
       render(".");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.DeclarationGlobal)
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

  private static void pp(socialite.Absyn.TypeName foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.TypeInt)
    {
       socialite.Absyn.TypeInt _typeint = (socialite.Absyn.TypeInt) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("int");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.TypeDouble)
    {
       socialite.Absyn.TypeDouble _typedouble = (socialite.Absyn.TypeDouble) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("double");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.Range foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.RangeDef)
    {
       socialite.Absyn.RangeDef _rangedef = (socialite.Absyn.RangeDef) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_rangedef.exp_1, 0);
       render("..");
       pp(_rangedef.exp_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.Rule foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.RuleDef)
    {
       socialite.Absyn.RuleDef _ruledef = (socialite.Absyn.RuleDef) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_ruledef.predicate_, 0);
       pp(_ruledef.listrulegoal_, 0);
       render(".");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.RuleGoal foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.RuleGoalDef)
    {
       socialite.Absyn.RuleGoalDef _rulegoaldef = (socialite.Absyn.RuleGoalDef) foo;
       if (_i_ > 0) render(_L_PAREN);
       render(":-");
       pp(_rulegoaldef.listonegoal_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.ListRuleGoal foo, int _i_)
  {
     for (java.util.Iterator<RuleGoal> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(";");
       } else {
         render("");
       }
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

  private static void pp(socialite.Absyn.Predicate foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.PredicateAtom)
    {
       socialite.Absyn.PredicateAtom _predicateatom = (socialite.Absyn.PredicateAtom) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_predicateatom.atom_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.PredicateStruct)
    {
       socialite.Absyn.PredicateStruct _predicatestruct = (socialite.Absyn.PredicateStruct) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_predicatestruct.structure_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.OneGoal foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.GoalPredicate)
    {
       socialite.Absyn.GoalPredicate _goalpredicate = (socialite.Absyn.GoalPredicate) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_goalpredicate.predicate_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.GoalEquation)
    {
       socialite.Absyn.GoalEquation _goalequation = (socialite.Absyn.GoalEquation) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_goalequation.equation_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.ListOneGoal foo, int _i_)
  {
     for (java.util.Iterator<OneGoal> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), 0);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }
  }

  private static void pp(socialite.Absyn.Term foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.TermAtom)
    {
       socialite.Absyn.TermAtom _termatom = (socialite.Absyn.TermAtom) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_termatom.atom_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.TermValue)
    {
       socialite.Absyn.TermValue _termvalue = (socialite.Absyn.TermValue) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_termvalue.value_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.TermCall)
    {
       socialite.Absyn.TermCall _termcall = (socialite.Absyn.TermCall) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_termcall.dident_, 0);
       render("(");
       pp(_termcall.listterm_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
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
       pp(_valueconst.constant_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.ValueIgnore)
    {
       socialite.Absyn.ValueIgnore _valueignore = (socialite.Absyn.ValueIgnore) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("_");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.ListTerm foo, int _i_)
  {
     for (java.util.Iterator<Term> it = foo.iterator(); it.hasNext();)
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

  private static void pp(socialite.Absyn.Constant foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.Const)
    {
       socialite.Absyn.Const _const = (socialite.Absyn.Const) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_const.uident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.Atom foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.AtomSharded)
    {
       socialite.Absyn.AtomSharded _atomsharded = (socialite.Absyn.AtomSharded) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_atomsharded.uident_, 0);
       render("[");
       pp(_atomsharded.value_, 0);
       render("]");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof socialite.Absyn.AtomSingle)
    {
       socialite.Absyn.AtomSingle _atomsingle = (socialite.Absyn.AtomSingle) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_atomsingle.uident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.Structure foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.Struct)
    {
       socialite.Absyn.Struct _struct = (socialite.Absyn.Struct) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_struct.atom_, 0);
       render("(");
       pp(_struct.listterm_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.Equation foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.Comparison)
    {
       socialite.Absyn.Comparison _comparison = (socialite.Absyn.Comparison) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_comparison.exp_1, 0);
       pp(_comparison.compop_, 0);
       pp(_comparison.exp_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(socialite.Absyn.CompOp foo, int _i_)
  {
    if (foo instanceof socialite.Absyn.CompOpEq)
    {
       socialite.Absyn.CompOpEq _compopeq = (socialite.Absyn.CompOpEq) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("=");
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
    else     if (foo instanceof socialite.Absyn.EInt)
    {
       socialite.Absyn.EInt _eint = (socialite.Absyn.EInt) foo;
       if (_i_ > 2) render(_L_PAREN);
       pp(_eint.value_, 0);
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
    if (foo instanceof socialite.Absyn.DeclarationDistributed)
    {
       socialite.Absyn.DeclarationDistributed _declarationdistributed = (socialite.Absyn.DeclarationDistributed) foo;
       render("(");
       render("DeclarationDistributed");
       sh(_declarationdistributed.uident_);
       sh(_declarationdistributed.typename_);
       sh(_declarationdistributed.variable_);
       sh(_declarationdistributed.range_);
       sh(_declarationdistributed.moredimensionsdeclaration_);
       render(")");
    }
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

  private static void sh(socialite.Absyn.TypeName foo)
  {
    if (foo instanceof socialite.Absyn.TypeInt)
    {
       socialite.Absyn.TypeInt _typeint = (socialite.Absyn.TypeInt) foo;
       render("TypeInt");
    }
    if (foo instanceof socialite.Absyn.TypeDouble)
    {
       socialite.Absyn.TypeDouble _typedouble = (socialite.Absyn.TypeDouble) foo;
       render("TypeDouble");
    }
  }

  private static void sh(socialite.Absyn.Range foo)
  {
    if (foo instanceof socialite.Absyn.RangeDef)
    {
       socialite.Absyn.RangeDef _rangedef = (socialite.Absyn.RangeDef) foo;
       render("(");
       render("RangeDef");
       sh(_rangedef.exp_1);
       sh(_rangedef.exp_2);
       render(")");
    }
  }

  private static void sh(socialite.Absyn.Rule foo)
  {
    if (foo instanceof socialite.Absyn.RuleDef)
    {
       socialite.Absyn.RuleDef _ruledef = (socialite.Absyn.RuleDef) foo;
       render("(");
       render("RuleDef");
       sh(_ruledef.predicate_);
       render("[");
       sh(_ruledef.listrulegoal_);
       render("]");
       render(")");
    }
  }

  private static void sh(socialite.Absyn.RuleGoal foo)
  {
    if (foo instanceof socialite.Absyn.RuleGoalDef)
    {
       socialite.Absyn.RuleGoalDef _rulegoaldef = (socialite.Absyn.RuleGoalDef) foo;
       render("(");
       render("RuleGoalDef");
       render("[");
       sh(_rulegoaldef.listonegoal_);
       render("]");
       render(")");
    }
  }

  private static void sh(socialite.Absyn.ListRuleGoal foo)
  {
     for (java.util.Iterator<RuleGoal> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
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

  private static void sh(socialite.Absyn.Predicate foo)
  {
    if (foo instanceof socialite.Absyn.PredicateAtom)
    {
       socialite.Absyn.PredicateAtom _predicateatom = (socialite.Absyn.PredicateAtom) foo;
       render("(");
       render("PredicateAtom");
       sh(_predicateatom.atom_);
       render(")");
    }
    if (foo instanceof socialite.Absyn.PredicateStruct)
    {
       socialite.Absyn.PredicateStruct _predicatestruct = (socialite.Absyn.PredicateStruct) foo;
       render("(");
       render("PredicateStruct");
       sh(_predicatestruct.structure_);
       render(")");
    }
  }

  private static void sh(socialite.Absyn.OneGoal foo)
  {
    if (foo instanceof socialite.Absyn.GoalPredicate)
    {
       socialite.Absyn.GoalPredicate _goalpredicate = (socialite.Absyn.GoalPredicate) foo;
       render("(");
       render("GoalPredicate");
       sh(_goalpredicate.predicate_);
       render(")");
    }
    if (foo instanceof socialite.Absyn.GoalEquation)
    {
       socialite.Absyn.GoalEquation _goalequation = (socialite.Absyn.GoalEquation) foo;
       render("(");
       render("GoalEquation");
       sh(_goalequation.equation_);
       render(")");
    }
  }

  private static void sh(socialite.Absyn.ListOneGoal foo)
  {
     for (java.util.Iterator<OneGoal> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(socialite.Absyn.Term foo)
  {
    if (foo instanceof socialite.Absyn.TermAtom)
    {
       socialite.Absyn.TermAtom _termatom = (socialite.Absyn.TermAtom) foo;
       render("(");
       render("TermAtom");
       sh(_termatom.atom_);
       render(")");
    }
    if (foo instanceof socialite.Absyn.TermValue)
    {
       socialite.Absyn.TermValue _termvalue = (socialite.Absyn.TermValue) foo;
       render("(");
       render("TermValue");
       sh(_termvalue.value_);
       render(")");
    }
    if (foo instanceof socialite.Absyn.TermCall)
    {
       socialite.Absyn.TermCall _termcall = (socialite.Absyn.TermCall) foo;
       render("(");
       render("TermCall");
       sh(_termcall.dident_);
       render("[");
       sh(_termcall.listterm_);
       render("]");
       render(")");
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
       sh(_valueconst.constant_);
       render(")");
    }
    if (foo instanceof socialite.Absyn.ValueIgnore)
    {
       socialite.Absyn.ValueIgnore _valueignore = (socialite.Absyn.ValueIgnore) foo;
       render("ValueIgnore");
    }
  }

  private static void sh(socialite.Absyn.ListTerm foo)
  {
     for (java.util.Iterator<Term> it = foo.iterator(); it.hasNext();)
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

  private static void sh(socialite.Absyn.Constant foo)
  {
    if (foo instanceof socialite.Absyn.Const)
    {
       socialite.Absyn.Const _const = (socialite.Absyn.Const) foo;
       render("(");
       render("Const");
       sh(_const.uident_);
       render(")");
    }
  }

  private static void sh(socialite.Absyn.Atom foo)
  {
    if (foo instanceof socialite.Absyn.AtomSharded)
    {
       socialite.Absyn.AtomSharded _atomsharded = (socialite.Absyn.AtomSharded) foo;
       render("(");
       render("AtomSharded");
       sh(_atomsharded.uident_);
       sh(_atomsharded.value_);
       render(")");
    }
    if (foo instanceof socialite.Absyn.AtomSingle)
    {
       socialite.Absyn.AtomSingle _atomsingle = (socialite.Absyn.AtomSingle) foo;
       render("(");
       render("AtomSingle");
       sh(_atomsingle.uident_);
       render(")");
    }
  }

  private static void sh(socialite.Absyn.Structure foo)
  {
    if (foo instanceof socialite.Absyn.Struct)
    {
       socialite.Absyn.Struct _struct = (socialite.Absyn.Struct) foo;
       render("(");
       render("Struct");
       sh(_struct.atom_);
       render("[");
       sh(_struct.listterm_);
       render("]");
       render(")");
    }
  }

  private static void sh(socialite.Absyn.Equation foo)
  {
    if (foo instanceof socialite.Absyn.Comparison)
    {
       socialite.Absyn.Comparison _comparison = (socialite.Absyn.Comparison) foo;
       render("(");
       render("Comparison");
       sh(_comparison.exp_1);
       sh(_comparison.compop_);
       sh(_comparison.exp_2);
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
    if (foo instanceof socialite.Absyn.EInt)
    {
       socialite.Absyn.EInt _eint = (socialite.Absyn.EInt) foo;
       render("(");
       render("EInt");
       sh(_eint.value_);
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

