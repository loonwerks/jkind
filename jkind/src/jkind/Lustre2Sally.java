package jkind;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.Ast;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Constant;
import jkind.lustre.Contract;
import jkind.lustre.EnumType;
import jkind.lustre.Equation;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.TupleExpr;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstVisitor;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.util.BigFraction;
import jkind.util.SexpUtil;
import jkind.util.Util;

public class Lustre2Sally implements AstVisitor<Sexp, Sexp> {
	private static final String INIT = "i%init";

	private boolean pre = false;

	/**
	 * <pre>
	 * ;; A definition of a state type called "my_state_type" with variables
	 * ;; x and y of type Real. 
	 * (define-state-type my_state_type 
	 *   ((x Real) (y Real))
	 * )
	 * </pre>
	 */
	public static Sexp createStateType(Node node) {
		List<Sexp> states = varDecls(Util.getVarDecls(node));
		Sexp init = varDecl(new VarDecl(INIT, NamedType.BOOL));
		return new Cons("define-state-type", new Symbol("state"), new Cons(init, states));
	}

	/**
	 * <pre>
	 * ;; Directly define a simple counter system that increases x and y
	 * (define-transition-system T1 my_state_type
	 *   ;; Initial states 
	 *   (and (= x 0) (= y 0))
	 *   ;; Transition 
	 *   (and (= next.x (+ state.x 1)) (= next.y (+ state.y 1)))
	 * )
	 * </pre>
	 */
	public static Sexp createTransitionSystem(Node node) {
		Sexp initial = new Symbol(quote(INIT));
		Sexp transition = node.accept(new Lustre2Sally());
		return new Cons("define-transition-system", new Symbol("T"), new Symbol("state"), initial,
				transition);
	}

	/**
	 * <pre>
	 * ;; Check whether x = y in T1
	 * (query T1 (= x y))
	 * </pre>
	 */
	public static List<Sexp> createQueries(Node node) {
		List<Sexp> queries = new ArrayList<>();
		for (String prop : node.properties) {
			Sexp body = new Cons("or", new Symbol(quote(prop)), new Symbol(quote(INIT)));
			queries.add(new Cons("query", new Symbol("T"), body));
		}
		return queries;
	}

	private static List<Sexp> varDecls(List<VarDecl> varDecls) {
		return varDecls.stream().map(Lustre2Sally::varDecl).collect(toList());
	}

	private static Sexp varDecl(VarDecl vd) {
		return new Cons(quote(vd.id), new Symbol(type(vd.type)));
	}

	private static String quote(String id) {
		// return "|" + id + "|";
		id = id.replaceAll("[.%~\\[\\]]", "__");
		if (id.startsWith("_")) {
			id = "q" + id;
		}
		return id;
	}

	private static String type(Type type) {
		if (type == NamedType.BOOL) {
			return "Bool";
		} else if (type == NamedType.INT) {
			return "Int";
		} else if (type == NamedType.REAL) {
			return "Real";
		} else if (type instanceof SubrangeIntType || type instanceof EnumType) {
			return "Int";
		} else {
			throw new UnsupportedOperationException("Type not supported: " + type);
		}
	}

	@Override
	public Sexp visit(Node node) {
		Sexp equations = SexpUtil.conjoin(map(node.equations));
		Sexp assertions = SexpUtil.conjoin(map(node.assertions));
		Sexp typeConstraints = createTypeConstraints(node);
		Sexp notInit = new Cons("not", curr(INIT));

		return new Cons("and", equations, assertions, typeConstraints, notInit);
	}

	private Sexp createTypeConstraints(Node node) {
		List<Sexp> conjuncts = new ArrayList<>();
		for (VarDecl vd : Util.getVarDecls(node)) {
			Sexp constraint = createTypeConstraint(vd);
			if (constraint != null) {
				conjuncts.add(constraint);
			}
		}
		return SexpUtil.conjoin(conjuncts);
	}

	private Sexp createTypeConstraint(VarDecl vd) {
		if (vd.type instanceof SubrangeIntType) {
			SubrangeIntType st = (SubrangeIntType) vd.type;
			return boundConstraint(vd.id, Sexp.fromBigInt(st.low), Sexp.fromBigInt(st.high));
		} else if (vd.type instanceof EnumType) {
			EnumType et = (EnumType) vd.type;
			return boundConstraint(vd.id, Sexp.fromInt(0), Sexp.fromInt(et.values.size() - 1));
		} else {
			return null;
		}
	}

	private Sexp boundConstraint(String id, Sexp low, Sexp high) {
		return new Cons("and", new Cons("<=", low, curr(id)), new Cons("<=", curr(id), high));
	}

	@Override
	public Sexp visit(Equation eq) {
		Sexp head = eq.lhs.get(0).accept(this);
		Sexp body = eq.expr.accept(this);
		return new Cons("=", head, body);
	}

	@Override
	public Sexp visit(IdExpr e) {
		return pre ? pre(e.id) : curr(e.id);
	}

	private Sexp pre(String id) {
		return new Symbol("state." + quote(id));
	}

	private Sexp curr(String id) {
		return new Symbol("next." + quote(id));
	}

	@Override
	public Sexp visit(IfThenElseExpr e) {
		return new Cons("ite", e.cond.accept(this), e.thenExpr.accept(this),
				e.elseExpr.accept(this));
	}

	@Override
	public Sexp visit(BinaryExpr e) {
		Sexp left = e.left.accept(this);
		Sexp right = e.right.accept(this);

		switch (e.op) {
		case NOTEQUAL:
		case XOR:
			return new Cons("not", (new Cons("=", left, right)));

		case ARROW:
			if (pre) {
				throw new IllegalArgumentException(
						"Arrows cannot be nested under pre during translation to Sally");
			}
			return new Cons("ite", pre(INIT), left, right);

		default:
			return new Cons(e.op.toString(), left, right);
		}
	}

	@Override
	public Sexp visit(UnaryExpr e) {
		switch (e.op) {
		case PRE:
			if (pre) {
				throw new IllegalArgumentException(
						"Nested pres must be removed before translation to Sally");
			}
			pre = true;
			Sexp expr = e.expr.accept(this);
			pre = false;
			return expr;

		case NEGATIVE:
			return new Cons("-", e.expr.accept(this));

		case NOT:
			return new Cons("not", e.expr.accept(this));

		default:
			throw new IllegalArgumentException("Unhandled unary operator: " + e.op);
		}
	}

	@Override
	public Sexp visit(IntExpr e) {
		return Sexp.fromBigInt(e.value);
	}

	@Override
	public Sexp visit(BoolExpr e) {
		return new Symbol(e.value ? "true" : "false");
	}

	@Override
	public Sexp visit(RealExpr e) {
		BigFraction bf = BigFraction.valueOf(e.value);
		return new Cons("/", Sexp.fromBigInt(bf.getNumerator()), Sexp.fromBigInt(bf
				.getDenominator()));
	}

	protected List<Sexp> map(List<? extends Ast> xs) {
		return xs.stream().map(e -> e.accept(this)).collect(toList());
	}

	/**
	 * Unsupported operations
	 */

	@Override
	public Sexp visit(ArrayAccessExpr e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sexp visit(ArrayExpr e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sexp visit(ArrayUpdateExpr e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sexp visit(CastExpr e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sexp visit(CondactExpr e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sexp visit(NodeCallExpr e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sexp visit(RecordAccessExpr e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sexp visit(RecordExpr e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sexp visit(RecordUpdateExpr e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sexp visit(TupleExpr e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sexp visit(Constant constant) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sexp visit(Program program) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sexp visit(TypeDef typeDef) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sexp visit(Contract contract) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sexp visit(VarDecl varDecl) {
		throw new UnsupportedOperationException();
	}
}