package socialite.Absyn; // Java Package generated by the BNF Converter.

public class RuleGoalDef extends RuleGoal {
  public final ListOneGoal listonegoal_;

  public RuleGoalDef(ListOneGoal p1) { listonegoal_ = p1; }

  public <R,A> R accept(socialite.Absyn.RuleGoal.Visitor<R,A> v, A arg) { return v.visit(this, arg); }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof socialite.Absyn.RuleGoalDef) {
      socialite.Absyn.RuleGoalDef x = (socialite.Absyn.RuleGoalDef)o;
      return this.listonegoal_.equals(x.listonegoal_);
    }
    return false;
  }

  public int hashCode() {
    return this.listonegoal_.hashCode();
  }


}