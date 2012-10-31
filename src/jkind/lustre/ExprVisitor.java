package jkind.lustre;

public interface ExprVisitor<T> {
	public T visit(BinaryExpr e);
	public T visit(BoolExpr e);
	public T visit(IdExpr e);
	public T visit(IfThenElseExpr e);
	public T visit(IntExpr e);
	public T visit(NodeCallExpr e);
	public T visit(RealExpr e);
	public T visit(UnaryExpr e);
}
