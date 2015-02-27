package socialite;

import socialite.Absyn.*;

/** BNFC-Generated All Visitor */
public interface AllVisitor<R,A> extends
  socialite.Absyn.Program.Visitor<R,A>,
  socialite.Absyn.Declaration.Visitor<R,A>,
  socialite.Absyn.MoreDimensionsDeclaration.Visitor<R,A>,
  socialite.Absyn.ColumnDeclaration.Visitor<R,A>,
  socialite.Absyn.AggregateSpecifier.Visitor<R,A>,
  socialite.Absyn.TypeName.Visitor<R,A>,
  socialite.Absyn.Rule.Visitor<R,A>,
  socialite.Absyn.Head.Visitor<R,A>,
  socialite.Absyn.RuleBody.Visitor<R,A>,
  socialite.Absyn.Predicate.Visitor<R,A>,
  socialite.Absyn.Subgoal.Visitor<R,A>,
  socialite.Absyn.Value.Visitor<R,A>,
  socialite.Absyn.Variable.Visitor<R,A>,
  socialite.Absyn.Function.Visitor<R,A>,
  socialite.Absyn.CompOp.Visitor<R,A>,
  socialite.Absyn.Exp.Visitor<R,A>
{}
