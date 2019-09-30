package jkind.lustre.visitors;

import jkind.lustre.*;

public interface AstVisitor<T, S extends T> extends ExprVisitor<S> {
	public T visit(Constant constant);
	public T visit(Equation equation);
	public T visit(Function function);
	public T visit(Node node);
	public T visit(RepairNode repairNode);
	public T visit(Program program);
	public T visit(TypeDef typeDef);
	public T visit(VarDecl varDecl);
	public T visit(Contract contract);
}
