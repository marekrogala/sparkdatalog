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
/* AggregateSpecifier */
    public R visit(socialite.Absyn.AggregateWith p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.NoAggregation p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.AggregateSpecifier p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* TypeName */
    public R visit(socialite.Absyn.TypeInt p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.TypeName p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Rule */
    public R visit(socialite.Absyn.RuleDef p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Rule p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Head */
    public R visit(socialite.Absyn.HeadSingle p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Head p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* RuleBody */
    public R visit(socialite.Absyn.RuleBodyDef p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.RuleBody p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Predicate */
    public R visit(socialite.Absyn.PredicateSingle p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Predicate p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Subgoal */
    public R visit(socialite.Absyn.GoalPredicate p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.GoalComparison p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.GoalAssign p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Subgoal p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Value */
    public R visit(socialite.Absyn.ValueInt p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.ValueVar p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.ValueDouble p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.ValueConst p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Value p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Variable */
    public R visit(socialite.Absyn.Var p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Variable p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Function */
    public R visit(socialite.Absyn.Func p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(socialite.Absyn.Function p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* CompOp */
    public R visit(socialite.Absyn.CompOpEq p, A arg) { return visitDefault(p, arg); }
    public R visit(socialite.Absyn.CompOpNe p, A arg) { return visitDefault(p, arg); }
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

    public R visit(socialite.Absyn.EValue p, A arg) { return visitDefault(p, arg); }

    public R visitDefault(socialite.Absyn.Exp p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
