package socialite.Absyn; // Java Package generated by the BNF Converter.

public abstract class MoreDimensionsDeclaration implements java.io.Serializable {
  public abstract <R,A> R accept(MoreDimensionsDeclaration.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(socialite.Absyn.NoMoreDim p, A arg);
    public R visit(socialite.Absyn.MoreDim p, A arg);

  }

}