package socialite;

import socialite.Absyn.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/** BNFC-Generated Fold Visitor */
public abstract class FoldVisitor<R,A> implements AllVisitor<R,A> {
    public abstract R leaf(A arg);
    public abstract R combine(R x, R y, A arg);

/* Program */
    public R visit(socialite.Absyn.Prog p, A arg) {
      R r = leaf(arg);
      for (Declaration x : p.listdeclaration_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      for (Rule x : p.listrule_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }

/* Declaration */
    public R visit(socialite.Absyn.DeclarationDistributed p, A arg) {
      R r = leaf(arg);
      r = combine(p.typename_.accept(this, arg), r, arg);
      r = combine(p.variable_.accept(this, arg), r, arg);
      r = combine(p.range_.accept(this, arg), r, arg);
      r = combine(p.moredimensionsdeclaration_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(socialite.Absyn.DeclarationGlobal p, A arg) {
      R r = leaf(arg);
      r = combine(p.moredimensionsdeclaration_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(socialite.Absyn.DeclarationConst p, A arg) {
      R r = leaf(arg);
      r = combine(p.typename_.accept(this, arg), r, arg);
      r = combine(p.exp_.accept(this, arg), r, arg);
      return r;
    }

/* MoreDimensionsDeclaration */
    public R visit(socialite.Absyn.NoMoreDim p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(socialite.Absyn.MoreDim p, A arg) {
      R r = leaf(arg);
      for (ColumnDeclaration x : p.listcolumndeclaration_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }

/* ColumnDeclaration */
    public R visit(socialite.Absyn.ColumnDecl p, A arg) {
      R r = leaf(arg);
      r = combine(p.typename_.accept(this, arg), r, arg);
      r = combine(p.variable_.accept(this, arg), r, arg);
      return r;
    }

/* TypeName */
    public R visit(socialite.Absyn.TypeInt p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(socialite.Absyn.TypeDouble p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Range */
    public R visit(socialite.Absyn.RangeDef p, A arg) {
      R r = leaf(arg);
      r = combine(p.exp_1.accept(this, arg), r, arg);
      r = combine(p.exp_2.accept(this, arg), r, arg);
      return r;
    }

/* Rule */
    public R visit(socialite.Absyn.RuleDef p, A arg) {
      R r = leaf(arg);
      r = combine(p.predicate_.accept(this, arg), r, arg);
      for (RuleGoal x : p.listrulegoal_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }

/* RuleGoal */
    public R visit(socialite.Absyn.RuleGoalDef p, A arg) {
      R r = leaf(arg);
      for (OneGoal x : p.listonegoal_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }

/* Predicate */
    public R visit(socialite.Absyn.PredicateSingle p, A arg) {
      R r = leaf(arg);
      for (Term x : p.listterm_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }
    public R visit(socialite.Absyn.PredicateSharded p, A arg) {
      R r = leaf(arg);
      r = combine(p.value_.accept(this, arg), r, arg);
      for (Term x : p.listterm_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }

/* OneGoal */
    public R visit(socialite.Absyn.GoalPredicate p, A arg) {
      R r = leaf(arg);
      r = combine(p.predicate_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(socialite.Absyn.GoalComparison p, A arg) {
      R r = leaf(arg);
      r = combine(p.exp_1.accept(this, arg), r, arg);
      r = combine(p.compop_.accept(this, arg), r, arg);
      r = combine(p.exp_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(socialite.Absyn.GoalAssign p, A arg) {
      R r = leaf(arg);
      r = combine(p.variable_.accept(this, arg), r, arg);
      r = combine(p.exp_.accept(this, arg), r, arg);
      return r;
    }

/* Term */
    public R visit(socialite.Absyn.TermValue p, A arg) {
      R r = leaf(arg);
      r = combine(p.value_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(socialite.Absyn.TermCall p, A arg) {
      R r = leaf(arg);
      for (Term x : p.listterm_) {
        r = combine(x.accept(this,arg), r, arg);
      }
      return r;
    }

/* Value */
    public R visit(socialite.Absyn.ValueInt p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(socialite.Absyn.ValueVar p, A arg) {
      R r = leaf(arg);
      r = combine(p.variable_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(socialite.Absyn.ValueDouble p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(socialite.Absyn.ValueConst p, A arg) {
      R r = leaf(arg);
      r = combine(p.constant_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(socialite.Absyn.ValueIgnore p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Variable */
    public R visit(socialite.Absyn.Var p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Constant */
    public R visit(socialite.Absyn.Const p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* CompOp */
    public R visit(socialite.Absyn.CompOpEq p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(socialite.Absyn.CompOpGt p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(socialite.Absyn.CompOpLt p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(socialite.Absyn.CompOpGe p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(socialite.Absyn.CompOpLe p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Exp */
    public R visit(socialite.Absyn.EAdd p, A arg) {
      R r = leaf(arg);
      r = combine(p.exp_1.accept(this, arg), r, arg);
      r = combine(p.exp_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(socialite.Absyn.ESub p, A arg) {
      R r = leaf(arg);
      r = combine(p.exp_1.accept(this, arg), r, arg);
      r = combine(p.exp_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(socialite.Absyn.EMul p, A arg) {
      R r = leaf(arg);
      r = combine(p.exp_1.accept(this, arg), r, arg);
      r = combine(p.exp_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(socialite.Absyn.EDiv p, A arg) {
      R r = leaf(arg);
      r = combine(p.exp_1.accept(this, arg), r, arg);
      r = combine(p.exp_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(socialite.Absyn.EInt p, A arg) {
      R r = leaf(arg);
      r = combine(p.value_.accept(this, arg), r, arg);
      return r;
    }


}
