package jkind.lustre;

public interface AstVisitor<T> extends ExprVisitor<T> {
	public T visit(Constant constant);
	public T visit(Equation equation);
	public T visit(Node node);
	public T visit(Program program);
	public T visit(TypeDef typeDef);
	public T visit(VarDecl varDecl);
}
