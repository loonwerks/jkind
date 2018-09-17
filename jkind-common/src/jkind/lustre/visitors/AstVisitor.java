package jkind.lustre.visitors;

import jkind.lustre.Constant;
import jkind.lustre.Contract;
import jkind.lustre.Equation;
import jkind.lustre.Function;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.TypeDef;
import jkind.lustre.VarDecl;

public interface AstVisitor<T, S extends T> extends ExprVisitor<S> {
	public T visit(Constant constant);
	public T visit(Equation equation);
	public T visit(Function function);
	public T visit(Node node);
	public T visit(Program program);
	public T visit(TypeDef typeDef);
	public T visit(VarDecl varDecl);
	public T visit(Contract contract);
}
