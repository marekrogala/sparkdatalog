package socialite;
import socialite.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* Program */
    public R visit(socialite.Absyn.Prog p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Program p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Declaration */
    public R visit(socialite.Absyn.DeclarationDistributed p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.DeclarationGlobal p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.DeclarationConst p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Declaration p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* MoreDimensionsDeclaration */
    public R visit(socialite.Absyn.NoMoreDim p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.MoreDim p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.MoreDimensionsDeclaration p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* ColumnDeclaration */
    public R visit(socialite.Absyn.ColumnDecl p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.ColumnDeclaration p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* TypeName */
    public R visit(socialite.Absyn.TypeInt p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.TypeDouble p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.TypeName p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Range */
    public R visit(socialite.Absyn.RangeDef p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Range p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Rule */
    public R visit(socialite.Absyn.RuleDef p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Rule p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* RuleGoal */
    public R visit(socialite.Absyn.RuleGoalDef p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.RuleGoal p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Predicate */
    public R visit(socialite.Absyn.PredicateAtom p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.PredicateStruct p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Predicate p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* OneGoal */
    public R visit(socialite.Absyn.GoalPredicate p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.GoalComparison p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.GoalAssign p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.OneGoal p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Term */
    public R visit(socialite.Absyn.TermAtom p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.TermValue p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.TermCall p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Term p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Value */
    public R visit(socialite.Absyn.ValueInt p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.ValueVar p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.ValueDouble p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.ValueConst p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.ValueIgnore p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Value p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Variable */
    public R visit(socialite.Absyn.Var p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Variable p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Constant */
    public R visit(socialite.Absyn.Const p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Constant p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Atom */
    public R visit(socialite.Absyn.AtomSharded p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.AtomSingle p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Atom p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* CompOp */
    public R visit(socialite.Absyn.CompOpEq p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.CompOpGt p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.CompOpLt p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.CompOpGe p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.CompOpLe p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.CompOp p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Exp */
    public R visit(socialite.Absyn.EAdd p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.ESub p, A arg) { return visitDefault(p, arg); }

    public R visit(socialite.Absyn.EMul p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.EDiv p, A arg) { return visitDefault(p, arg); }

    public R visit(socialite.Absyn.EInt p, A arg) { return visitDefault(p, arg); }

    public R visitDefault(socialite.Absyn.Exp p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
