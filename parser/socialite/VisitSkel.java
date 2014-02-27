package socialite;
import socialite.Absyn.*;
/*** BNFC-Generated Visitor Design Pattern Skeleton. ***/
/* This implements the common visitor design pattern.
   Tests show it to be slightly less efficient than the
   instanceof method, but easier to use. 
   Replace the R and A parameters with the desired return
   and context types.*/

public class VisitSkel
{
  public class ProgramVisitor<R,A> implements Program.Visitor<R,A>
  {
    public R visit(socialite.Absyn.Prog p, A arg)
    {
      /* Code For Prog Goes Here */

      for (Declaration x : p.listdeclaration_) {
      }
      for (Rule x : p.listrule_) {
      }

      return null;
    }

  }
  public class DeclarationVisitor<R,A> implements Declaration.Visitor<R,A>
  {
    public R visit(socialite.Absyn.DeclarationDistributed p, A arg)
    {
      /* Code For DeclarationDistributed Goes Here */

      //p.uident_;
      p.typename_.accept(new TypeNameVisitor<R,A>(), arg);
      p.variable_.accept(new VariableVisitor<R,A>(), arg);
      p.range_.accept(new RangeVisitor<R,A>(), arg);
      p.moredimensionsdeclaration_.accept(new MoreDimensionsDeclarationVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.DeclarationGlobal p, A arg)
    {
      /* Code For DeclarationGlobal Goes Here */

      //p.uident_;
      p.moredimensionsdeclaration_.accept(new MoreDimensionsDeclarationVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.DeclarationConst p, A arg)
    {
      /* Code For DeclarationConst Goes Here */

      p.typename_.accept(new TypeNameVisitor<R,A>(), arg);
      //p.uident_;
      p.exp_.accept(new ExpVisitor<R,A>(), arg);

      return null;
    }

  }
  public class MoreDimensionsDeclarationVisitor<R,A> implements MoreDimensionsDeclaration.Visitor<R,A>
  {
    public R visit(socialite.Absyn.NoMoreDim p, A arg)
    {
      /* Code For NoMoreDim Goes Here */


      return null;
    }
    public R visit(socialite.Absyn.MoreDim p, A arg)
    {
      /* Code For MoreDim Goes Here */

      for (ColumnDeclaration x : p.listcolumndeclaration_) {
      }

      return null;
    }

  }
  public class ColumnDeclarationVisitor<R,A> implements ColumnDeclaration.Visitor<R,A>
  {
    public R visit(socialite.Absyn.ColumnDecl p, A arg)
    {
      /* Code For ColumnDecl Goes Here */

      p.typename_.accept(new TypeNameVisitor<R,A>(), arg);
      p.variable_.accept(new VariableVisitor<R,A>(), arg);

      return null;
    }

  }
  public class TypeNameVisitor<R,A> implements TypeName.Visitor<R,A>
  {
    public R visit(socialite.Absyn.TypeInt p, A arg)
    {
      /* Code For TypeInt Goes Here */


      return null;
    }
    public R visit(socialite.Absyn.TypeDouble p, A arg)
    {
      /* Code For TypeDouble Goes Here */


      return null;
    }

  }
  public class RangeVisitor<R,A> implements Range.Visitor<R,A>
  {
    public R visit(socialite.Absyn.RangeDef p, A arg)
    {
      /* Code For RangeDef Goes Here */

      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);

      return null;
    }

  }
  public class RuleVisitor<R,A> implements Rule.Visitor<R,A>
  {
    public R visit(socialite.Absyn.RuleDef p, A arg)
    {
      /* Code For RuleDef Goes Here */

      p.predicate_.accept(new PredicateVisitor<R,A>(), arg);
      for (RuleGoal x : p.listrulegoal_) {
      }

      return null;
    }

  }
  public class RuleGoalVisitor<R,A> implements RuleGoal.Visitor<R,A>
  {
    public R visit(socialite.Absyn.RuleGoalDef p, A arg)
    {
      /* Code For RuleGoalDef Goes Here */

      for (OneGoal x : p.listonegoal_) {
      }

      return null;
    }

  }
  public class PredicateVisitor<R,A> implements Predicate.Visitor<R,A>
  {
    public R visit(socialite.Absyn.PredicateAtom p, A arg)
    {
      /* Code For PredicateAtom Goes Here */

      p.atom_.accept(new AtomVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.PredicateStruct p, A arg)
    {
      /* Code For PredicateStruct Goes Here */

      p.atom_.accept(new AtomVisitor<R,A>(), arg);
      for (Term x : p.listterm_) {
      }

      return null;
    }

  }
  public class OneGoalVisitor<R,A> implements OneGoal.Visitor<R,A>
  {
    public R visit(socialite.Absyn.GoalPredicate p, A arg)
    {
      /* Code For GoalPredicate Goes Here */

      p.predicate_.accept(new PredicateVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.GoalComparison p, A arg)
    {
      /* Code For GoalComparison Goes Here */

      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.compop_.accept(new CompOpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.GoalAssign p, A arg)
    {
      /* Code For GoalAssign Goes Here */

      p.variable_.accept(new VariableVisitor<R,A>(), arg);
      p.exp_.accept(new ExpVisitor<R,A>(), arg);

      return null;
    }

  }
  public class TermVisitor<R,A> implements Term.Visitor<R,A>
  {
    public R visit(socialite.Absyn.TermAtom p, A arg)
    {
      /* Code For TermAtom Goes Here */

      p.atom_.accept(new AtomVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.TermValue p, A arg)
    {
      /* Code For TermValue Goes Here */

      p.value_.accept(new ValueVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.TermCall p, A arg)
    {
      /* Code For TermCall Goes Here */

      //p.dident_;
      for (Term x : p.listterm_) {
      }

      return null;
    }

  }
  public class ValueVisitor<R,A> implements Value.Visitor<R,A>
  {
    public R visit(socialite.Absyn.ValueInt p, A arg)
    {
      /* Code For ValueInt Goes Here */

      //p.integer_;

      return null;
    }
    public R visit(socialite.Absyn.ValueVar p, A arg)
    {
      /* Code For ValueVar Goes Here */

      p.variable_.accept(new VariableVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.ValueDouble p, A arg)
    {
      /* Code For ValueDouble Goes Here */

      //p.double_;

      return null;
    }
    public R visit(socialite.Absyn.ValueConst p, A arg)
    {
      /* Code For ValueConst Goes Here */

      p.constant_.accept(new ConstantVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.ValueIgnore p, A arg)
    {
      /* Code For ValueIgnore Goes Here */


      return null;
    }

  }
  public class VariableVisitor<R,A> implements Variable.Visitor<R,A>
  {
    public R visit(socialite.Absyn.Var p, A arg)
    {
      /* Code For Var Goes Here */

      //p.lident_;

      return null;
    }

  }
  public class ConstantVisitor<R,A> implements Constant.Visitor<R,A>
  {
    public R visit(socialite.Absyn.Const p, A arg)
    {
      /* Code For Const Goes Here */

      //p.uident_;

      return null;
    }

  }
  public class AtomVisitor<R,A> implements Atom.Visitor<R,A>
  {
    public R visit(socialite.Absyn.AtomSharded p, A arg)
    {
      /* Code For AtomSharded Goes Here */

      //p.uident_;
      p.value_.accept(new ValueVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.AtomSingle p, A arg)
    {
      /* Code For AtomSingle Goes Here */

      //p.uident_;

      return null;
    }

  }
  public class CompOpVisitor<R,A> implements CompOp.Visitor<R,A>
  {
    public R visit(socialite.Absyn.CompOpEq p, A arg)
    {
      /* Code For CompOpEq Goes Here */


      return null;
    }
    public R visit(socialite.Absyn.CompOpGt p, A arg)
    {
      /* Code For CompOpGt Goes Here */


      return null;
    }
    public R visit(socialite.Absyn.CompOpLt p, A arg)
    {
      /* Code For CompOpLt Goes Here */


      return null;
    }
    public R visit(socialite.Absyn.CompOpGe p, A arg)
    {
      /* Code For CompOpGe Goes Here */


      return null;
    }
    public R visit(socialite.Absyn.CompOpLe p, A arg)
    {
      /* Code For CompOpLe Goes Here */


      return null;
    }

  }
  public class ExpVisitor<R,A> implements Exp.Visitor<R,A>
  {
    public R visit(socialite.Absyn.EAdd p, A arg)
    {
      /* Code For EAdd Goes Here */

      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.ESub p, A arg)
    {
      /* Code For ESub Goes Here */

      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.EMul p, A arg)
    {
      /* Code For EMul Goes Here */

      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.EDiv p, A arg)
    {
      /* Code For EDiv Goes Here */

      p.exp_1.accept(new ExpVisitor<R,A>(), arg);
      p.exp_2.accept(new ExpVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.EInt p, A arg)
    {
      /* Code For EInt Goes Here */

      p.value_.accept(new ValueVisitor<R,A>(), arg);

      return null;
    }

  }
}