package jkind.translation;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.visitors.ExprVisitor;

public class Expr2FormulaVisitor implements ExprVisitor<Void> {
	private final int column;
	private final Map<String, Integer> rowAssignments;

	private final SortedSet<String> refs;
	private final StringBuilder buf;

	final private static int INITIAL_COLUMN = 1;

	public Expr2FormulaVisitor(int column, Map<String, Integer> rowAssignments) {
		this.column = column;
		this.rowAssignments = rowAssignments;

		this.refs = new TreeSet<>();
		this.buf = new StringBuilder();
	}

	@Override
	public String toString() {
		if (refs.isEmpty()) {
			return buf.toString();
		}

		StringBuilder result = new StringBuilder();
		result.append("IF(OR(");
		boolean first = true;
		for (String ref : refs) {
			if (!first) {
				result.append(",");
			}
			result.append("IF(ISERROR(" + ref + "),FALSE," + ref + "=\"\")");
			first = false;
		}
		result.append("), \"\", ");
		result.append(buf);
		result.append(")");

		return result.toString();
	}

	@Override
	public Void visit(ArrayAccessExpr e) {
		throw new IllegalArgumentException(
				"Arrays must be flattened before translation to formula");
	}

	@Override
	public Void visit(ArrayExpr e) {
		throw new IllegalArgumentException(
				"Arrays must be flattened before translation to formula");
	}

	@Override
	public Void visit(ArrayUpdateExpr e) {
		throw new IllegalArgumentException(
				"Arrays must be flattened before translation to formula");
	}

	@Override
	public Void visit(BinaryExpr e) {
		switch (e.op) {
		case ARROW:
			if (column > INITIAL_COLUMN) {
				e.right.accept(this);
			} else {
				e.left.accept(this);
			}
			return null;

		case OR:
		case AND:
			buf.append(e.op.toString().toUpperCase());
			buf.append("(");
			e.left.accept(this);
			buf.append(",");
			e.right.accept(this);
			buf.append(")");
			return null;

		case XOR:
			buf.append("(");
			e.left.accept(this);
			buf.append("<>");
			e.right.accept(this);
			buf.append(")");
			return null;

		case INT_DIVIDE:
			buf.append("INT(");
			e.left.accept(this);
			buf.append("/");
			e.right.accept(this);
			buf.append(")");
			return null;

		case MODULUS:
			buf.append("MOD(");
			e.left.accept(this);
			buf.append(",");
			e.right.accept(this);
			buf.append(")");
			return null;

		case IMPLIES:
			buf.append("OR(NOT(");
			e.left.accept(this);
			buf.append("),");
			e.right.accept(this);
			buf.append(")");
			return null;

		default:
			buf.append("(");
			e.left.accept(this);
			buf.append(e.op);
			e.right.accept(this);
			buf.append(")");
			return null;
		}
	}

	@Override
	public Void visit(BoolExpr e) {
		buf.append(e.value ? "TRUE" : "FALSE");
		return null;
	}

	@Override
	public Void visit(CastExpr e) {
		if (e.type == NamedType.REAL) {
			e.expr.accept(this);
		} else if (e.type == NamedType.INT) {
			buf.append("FLOOR(");
			e.expr.accept(this);
			buf.append(",1)");
		} else {
			throw new IllegalArgumentException();
		}
		return null;
	}

	@Override
	public Void visit(CondactExpr e) {
		throw new IllegalArgumentException("Condacts must be removed before translation to formula");
	}

	@Override
	public Void visit(IdExpr e) {
		int row = rowAssignments.get(e.id);

		// Excel uses 1-indexed rows and columns
		String cell = toExcelColumn(column + 1) + Integer.toString(row + 1);
		buf.append(cell);
		refs.add(cell);
		return null;
	}

	/**
	 * 1 -> A 2 -> B ... 26 -> Z 27 -> AA 28 -> AB ...
	 */
	public static String toExcelColumn(int col) {
		StringBuilder result = new StringBuilder();
		while (col > 0) {
			char c = (char) ('A' + (col - 1) % 26);
			result.append(c);
			col = (col - 1) / 26;
		}
		return result.reverse().toString();
	}

	@Override
	public Void visit(IfThenElseExpr e) {
		buf.append("IF(");
		e.cond.accept(this);
		buf.append(",");
		e.thenExpr.accept(this);
		buf.append(",");
		e.elseExpr.accept(this);
		buf.append(")");
		return null;
	}

	@Override
	public Void visit(IntExpr e) {
		buf.append(e.value);
		return null;
	}

	@Override
	public Void visit(NodeCallExpr e) {
		throw new IllegalArgumentException(
				"Node calls must be inlined before translation to formula");
	}

	@Override
	public Void visit(RealExpr e) {
		buf.append(e.value.toPlainString());
		return null;
	}

	@Override
	public Void visit(RecordAccessExpr e) {
		throw new IllegalArgumentException(
				"Records must be flattened before translation to formula");
	}

	@Override
	public Void visit(RecordExpr e) {
		throw new IllegalArgumentException(
				"Records must be flattened before translation to formula");
	}

	@Override
	public Void visit(UnaryExpr e) {
		switch (e.op) {
		case PRE:
			if (column == INITIAL_COLUMN) {
				// Create an error value for pre in initial step
				buf.append("(0+\"\")");
				return null;
			}

			Expr2FormulaVisitor preVisitor = new Expr2FormulaVisitor(column - 1, rowAssignments);
			e.expr.accept(preVisitor);

			buf.append("(");
			buf.append(preVisitor.buf);
			buf.append(")");

			refs.addAll(preVisitor.refs);

			return null;

		case NEGATIVE:
			buf.append("(-");
			e.expr.accept(this);
			buf.append(")");
			return null;

		case NOT:
			buf.append("NOT(");
			e.expr.accept(this);
			buf.append(")");
			return null;

		default:
			throw new IllegalArgumentException("Unknown unary operator");
		}
	}
}
