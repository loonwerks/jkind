package jkind.pdr;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.ExprVisitor;
import de.uni_freiburg.informatik.ultimate.logic.Term;

public class Lustre2Term implements ExprVisitor<Term> {
	public static final String INIT = "%init";
	private final PdrSolver solver;
	private boolean pre = false;
	
	public Lustre2Term(PdrSolver solver) {
		this.solver = solver;
	}

	public VarDecl getInitVariable() {
		return new VarDecl(INIT, NamedType.BOOL);
	}

	public Term getInit() {
		return solver.term(INIT);
	}

	public Term getTransition(Node node) {
		List<Term> conjuncts = new ArrayList<>();

		for (Equation eq : node.equations) {
			Term body = eq.expr.accept(this);
			Term head = eq.lhs.get(0).accept(this);
			conjuncts.add(solver.term("=", head, body));
		}

		for (Expr assertion : node.assertions) {
			conjuncts.add(assertion.accept(this));
		}
		
		conjuncts.add(solver.not(solver.term(prime(INIT))));

		return solver.and(conjuncts);
	}

	public Term getProperty(Node node) {
		// TODO: Multi-property?
		return solver.term(node.properties.get(0));
	}
	
	private String prime(String str) {
		return str + "'";
	}

	@Override
	public Term visit(ArrayAccessExpr e) {
		throw new IllegalArgumentException("Arrays must be flattened before translation to Term");
	}

	@Override
	public Term visit(ArrayExpr e) {
		throw new IllegalArgumentException("Arrays must be flattened before translation to Term");
	}

	@Override
	public Term visit(ArrayUpdateExpr e) {
		throw new IllegalArgumentException("Arrays must be flattened before translation to Term");
	}

	@Override
	public Term visit(BinaryExpr e) {
		Term left = e.left.accept(this);
		Term right = e.right.accept(this);

		switch (e.op) {
		case NOTEQUAL:
		case XOR:
			return solver.term("not", solver.term("=", left, right));

		case ARROW:
			if (pre) {
				throw new IllegalArgumentException(
						"Arrows cannot be nested under pre during translation to Term");
			}
			return solver.term("ite", solver.term(INIT), left, right);

		default:
			return solver.term(e.op.toString(), left, right);
		}
	}

	@Override
	public Term visit(BoolExpr e) {
		return solver.term(Boolean.toString(e.value));
	}

	@Override
	public Term visit(CastExpr e) {
		if (e.type == NamedType.REAL) {
			return solver.term("to_real", e.expr.accept(this));
		} else if (e.type == NamedType.INT) {
			return solver.term("to_int", e.expr.accept(this));
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Term visit(CondactExpr e) {
		throw new IllegalArgumentException("Condacts must be removed before translation to Term");
	}

	@Override
	public Term visit(IdExpr e) {
		return pre ? solver.term(e.id) : solver.term(prime(e.id));
	}

	@Override
	public Term visit(IfThenElseExpr e) {
		return solver.term("ite", e.cond.accept(this), e.thenExpr.accept(this),
				e.elseExpr.accept(this));
	}

	@Override
	public Term visit(IntExpr e) {
		return solver.numeral(e.value);
	}

	@Override
	public Term visit(NodeCallExpr e) {
		throw new IllegalArgumentException("Node calls must be inlined before translation to Term");
	}

	@Override
	public Term visit(RealExpr e) {
		return solver.decimal(e.value);
	}

	@Override
	public Term visit(RecordAccessExpr e) {
		throw new IllegalArgumentException("Records must be flattened before translation to Term");
	}

	@Override
	public Term visit(RecordExpr e) {
		throw new IllegalArgumentException("Records must be flattened before translation to Term");
	}

	@Override
	public Term visit(RecordUpdateExpr e) {
		throw new IllegalArgumentException("Records must be flattened before translation to Term");
	}

	@Override
	public Term visit(TupleExpr e) {
		throw new IllegalArgumentException("Tuples must be flattened before translation to Term");
	}

	@Override
	public Term visit(UnaryExpr e) {
		switch (e.op) {
		case PRE:
			if (pre) {
				throw new IllegalArgumentException(
						"Nested pres must be removed before translation to Term");
			}
			pre = true;
			Term expr = e.expr.accept(this);
			pre = false;
			return expr;

		case NEGATIVE:
			return solver.term("-", e.expr.accept(this));

		default:
			return solver.term(e.op.toString(), e.expr.accept(this));
		}
	}
}
