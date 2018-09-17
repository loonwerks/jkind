package jkind.lustre.visitors;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.FunctionCallExpr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;

public interface ExprVisitor<T> {
	public T visit(ArrayAccessExpr e);
	public T visit(ArrayExpr e);
	public T visit(ArrayUpdateExpr e);
	public T visit(BinaryExpr e);
	public T visit(BoolExpr e);
	public T visit(CastExpr e);
	public T visit(CondactExpr e);
	public T visit(FunctionCallExpr e);
	public T visit(IdExpr e);
	public T visit(IfThenElseExpr e);
	public T visit(IntExpr e);
	public T visit(NodeCallExpr e);
	public T visit(RealExpr e);
	public T visit(RecordAccessExpr e);
	public T visit(RecordExpr e);
	public T visit(RecordUpdateExpr e);
	public T visit(TupleExpr e);
	public T visit(UnaryExpr e);
}
