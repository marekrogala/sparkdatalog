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
      p.aggregatespecifier_.accept(new AggregateSpecifierVisitor<R,A>(), arg);

      return null;
    }

  }
  public class AggregateSpecifierVisitor<R,A> implements AggregateSpecifier.Visitor<R,A>
  {
    public R visit(socialite.Absyn.AggregateWith p, A arg)
    {
      /* Code For AggregateWith Goes Here */

      p.function_.accept(new FunctionVisitor<R,A>(), arg);

      return null;
    }
    public R visit(socialite.Absyn.NoAggregation p, A arg)
    {
      /* Code For NoAggregation Goes Here */


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

  }
  public class RuleVisitor<R,A> implements Rule.Visitor<R,A>
  {
    public R visit(socialite.Absyn.RuleDef p, A arg)
    {
      /* Code For RuleDef Goes Here */

      p.head_.accept(new HeadVisitor<R,A>(), arg);
      p.rulebody_.accept(new RuleBodyVisitor<R,A>(), arg);

      return null;
    }

  }
  public class HeadVisitor<R,A> implements Head.Visitor<R,A>
  {
    public R visit(socialite.Absyn.HeadSingle p, A arg)
    {
      /* Code For HeadSingle Goes Here */

      //p.uident_;
      for (Variable x : p.listvariable_) {
      }

      return null;
    }

  }
  public class RuleBodyVisitor<R,A> implements RuleBody.Visitor<R,A>
  {
    public R visit(socialite.Absyn.RuleBodyDef p, A arg)
    {
      /* Code For RuleBodyDef Goes Here */

      for (Subgoal x : p.listsubgoal_) {
      }

      return null;
    }

  }
  public class PredicateVisitor<R,A> implements Predicate.Visitor<R,A>
  {
    public R visit(socialite.Absyn.PredicateSingle p, A arg)
    {
      /* Code For PredicateSingle Goes Here */

      //p.uident_;
      for (Value x : p.listvalue_) {
      }

      return null;
    }

  }
  public class SubgoalVisitor<R,A> implements Subgoal.Visitor<R,A>
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

      //p.uident_;

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
  public class FunctionVisitor<R,A> implements Function.Visitor<R,A>
  {
    public R visit(socialite.Absyn.Func p, A arg)
    {
      /* Code For Func Goes Here */

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
    public R visit(socialite.Absyn.CompOpNe p, A arg)
    {
      /* Code For CompOpNe Goes Here */


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
    public R visit(socialite.Absyn.EValue p, A arg)
    {
      /* Code For EValue Goes Here */

      p.value_.accept(new ValueVisitor<R,A>(), arg);

      return null;
    }

  }
}