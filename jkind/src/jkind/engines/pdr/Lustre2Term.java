package jkind.engines.pdr;

import java.util.ArrayList;
import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.logic.Term;
import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.EnumType;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.FunctionCallExpr;
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
import jkind.lustre.SubrangeIntType;
import jkind.lustre.TupleExpr;
import jkind.lustre.Type;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.ExprVisitor;
import jkind.solvers.smtinterpol.ScriptUser;
import jkind.util.SexpUtil;
import jkind.util.Util;

public class Lustre2Term extends ScriptUser implements ExprVisitor<Term> {
	private static final String INIT = "%init";
	private static final String ASSERTIONS = "%assertions";

	private final Node node;
	private boolean pre = false;

	public Lustre2Term(Script script, Node node) {
		super(script);
		this.node = node;
	}

	public Term getInit() {
		return term(INIT);
	}

	public Term getAssertions() {
		return term(ASSERTIONS);
	}

	public List<VarDecl> getVariables() {
		List<VarDecl> variables = new ArrayList<>();
		for (VarDecl vd : Util.getVarDecls(node)) {
			variables.add(encode(vd));
		}
		variables.add(new VarDecl(INIT, NamedType.BOOL));
		variables.add(new VarDecl(ASSERTIONS, NamedType.BOOL));
		return variables;
	}

	public static String encode(String name) {
		return "$" + name;
	}

	private VarDecl encode(VarDecl vd) {
		return new VarDecl(encode(vd.id), vd.type);
	}

	public static String decode(String encoded) {
		if (encoded.startsWith("$")) {
			return encoded.substring(1);
		} else {
			throw new IllegalArgumentException("Not an encoded name: " + encoded);
		}
	}

	public Term getTransition() {
		List<Term> conjuncts = new ArrayList<>();

		for (Equation eq : node.equations) {
			Term body = eq.expr.accept(this);
			Term head = eq.lhs.get(0).accept(this);
			conjuncts.add(term("=", head, body));
		}

		List<Term> assertions = new ArrayList<>();
		for (Expr assertion : node.assertions) {
			assertions.add(assertion.accept(this));
		}
		assertions.add(or(term(INIT), term(ASSERTIONS)));
		conjuncts.add(term("=", term(prime(ASSERTIONS)), and(assertions)));

		// Type constraints need to be included during interpolation, so we
		// include them in the transition relation
		for (VarDecl vd : Util.getVarDecls(node)) {
			Term baseConstraint = typeConstraint(encode(vd.id), vd.type);
			if (baseConstraint != null) {
				conjuncts.add(baseConstraint);
			}

			Term primeConstraint = typeConstraint(encode(prime(vd.id)), vd.type);
			if (primeConstraint != null) {
				conjuncts.add(primeConstraint);
			}
		}

		conjuncts.add(not(term(prime(INIT))));

		return and(conjuncts);
	}

	public Term encodeProperty(String property) {
		return or(term(encode(property)), not(term(ASSERTIONS)), term(INIT));
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
			return not(term("=", left, right));

		case ARROW:
			if (pre) {
				throw new IllegalArgumentException("Arrows cannot be nested under pre during translation to Term");
			}
			return ite(term(INIT), left, right);

		default:
			return term(e.op.toString(), left, right);
		}
	}

	@Override
	public Term visit(BoolExpr e) {
		return term(Boolean.toString(e.value));
	}

	@Override
	public Term visit(CastExpr e) {
		if (e.type == NamedType.REAL) {
			return term("to_real", e.expr.accept(this));
		} else if (e.type == NamedType.INT) {
			return term("to_int", e.expr.accept(this));
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Term visit(CondactExpr e) {
		throw new IllegalArgumentException("Condacts must be removed before translation to Term");
	}

	@Override
	public Term visit(FunctionCallExpr e) {
		Term[] params = new Term[e.args.size()];
		for (int i = 0; i < e.args.size(); i++) {
			params[i] = e.args.get(i).accept(this);
		}
		return term(SexpUtil.encodeFunction(e.function), params);
	}

	@Override
	public Term visit(IdExpr e) {
		String id = encode(e.id);
		return pre ? term(id) : term(prime(id));
	}

	@Override
	public Term visit(IfThenElseExpr e) {
		return ite(e.cond.accept(this), e.thenExpr.accept(this), e.elseExpr.accept(this));
	}

	@Override
	public Term visit(IntExpr e) {
		return numeral(e.value);
	}

	@Override
	public Term visit(NodeCallExpr e) {
		throw new IllegalArgumentException("Node calls must be inlined before translation to Term");
	}

	@Override
	public Term visit(RealExpr e) {
		return decimal(e.value);
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
				throw new IllegalArgumentException("Nested pres must be removed before translation to Term");
			}
			pre = true;
			Term expr = e.expr.accept(this);
			pre = false;
			return expr;

		case NEGATIVE:
			return term("-", e.expr.accept(this));

		case NOT:
			return not(e.expr.accept(this));

		default:
			throw new IllegalArgumentException("Unhandled unary operator: " + e.op);
		}
	}

	private Term typeConstraint(String id, Type type) {
		if (type instanceof SubrangeIntType) {
			return subrangeConstraint(id, (SubrangeIntType) type);
		} else if (type instanceof EnumType) {
			return enumConstraint(id, (EnumType) type);
		} else {
			return null;
		}
	}

	private Term subrangeConstraint(String id, SubrangeIntType subrange) {
		return boundConstraint(id, numeral(subrange.low), numeral(subrange.high));
	}

	private Term enumConstraint(String id, EnumType et) {
		return boundConstraint(id, numeral(0), numeral(et.values.size() - 1));
	}

	private Term boundConstraint(String id, Term low, Term high) {
		return and(lessEqual(low, term(id)), lessEqual(term(id), high));
	}

	private Term lessEqual(Term left, Term right) {
		return term("<=", left, right);
	}
}
