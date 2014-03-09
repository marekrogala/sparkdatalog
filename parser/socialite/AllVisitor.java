package socialite;

import socialite.Absyn.*;

/** BNFC-Generated All Visitor */
public interface AllVisitor<R,A> extends
  socialite.Absyn.Program.Visitor<R,A>,
  socialite.Absyn.Declaration.Visitor<R,A>,
  socialite.Absyn.MoreDimensionsDeclaration.Visitor<R,A>,
  socialite.Absyn.ColumnDeclaration.Visitor<R,A>,
  socialite.Absyn.TypeName.Visitor<R,A>,
  socialite.Absyn.Range.Visitor<R,A>,
  socialite.Absyn.Rule.Visitor<R,A>,
  socialite.Absyn.RuleGoal.Visitor<R,A>,
  socialite.Absyn.Predicate.Visitor<R,A>,
  socialite.Absyn.OneGoal.Visitor<R,A>,
  socialite.Absyn.Term.Visitor<R,A>,
  socialite.Absyn.Value.Visitor<R,A>,
  socialite.Absyn.Variable.Visitor<R,A>,
  socialite.Absyn.Constant.Visitor<R,A>,
  socialite.Absyn.CompOp.Visitor<R,A>,
  socialite.Absyn.Exp.Visitor<R,A>
{}
