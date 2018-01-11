package jkind.translation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.ExprVisitor;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;
import jkind.sexp.Symbol;
import jkind.util.LinkedBiMap;
import jkind.util.SexpUtil;
import jkind.util.StreamIndex;
import jkind.util.Util;

public class Lustre2Sexp implements ExprVisitor<Sexp> {
	public static final Symbol INIT = new Symbol("%init");
	private final int index;
	private boolean pre = false;

	public Lustre2Sexp(int index) {
		this.index = index;
	}

	public static Relation constructTransitionRelation(Node node) {
		return constructGeneralTransitionRelation(node, Collections.emptyList());
	}

	public static Relation constructIvcTransitionRelation(Node node) {
		return constructGeneralTransitionRelation(node, node.ivc);
	}

	private static Relation constructGeneralTransitionRelation(Node node, List<String> ivc) {
		Lustre2Sexp visitor = new Lustre2Sexp(1);
		List<Sexp> conjuncts = new ArrayList<>();

		LinkedBiMap<String, Symbol> ivcMap = createIvcMap(ivc);

		for (Equation eq : node.equations) {
			Sexp body = eq.expr.accept(visitor);
			Sexp head = eq.lhs.get(0).accept(visitor);
			Sexp sexp = new Cons("=", head, body);

			String id = eq.lhs.get(0).id;
			if (ivcMap.containsKey(id)) {
				sexp = new Cons("=>", ivcMap.get(id), sexp);
			}
			conjuncts.add(sexp);
		}

		for (Expr assertion : node.assertions) {
			conjuncts.add(assertion.accept(visitor));
		}

		List<VarDecl> inputs = new ArrayList<>();
		inputs.add(new VarDecl(INIT.str, NamedType.BOOL));
		inputs.addAll(visitor.pre(Util.getVarDecls(node)));
		inputs.addAll(visitor.curr(Util.getVarDecls(node)));
		return new Relation(Relation.T, inputs, SexpUtil.conjoin(conjuncts));
	}

	public static LinkedBiMap<String, Symbol> createIvcMap(List<String> ivc) {
		LinkedBiMap<String, Symbol> ivcMap = new LinkedBiMap<>();
		int id = 0;
		for (String s : ivc) {
			ivcMap.put(s, new Symbol("ivc" + id++));
		}
		return ivcMap;
	}

	private Symbol curr(String id) {
		return new StreamIndex(id, index).getEncoded();
	}

	private Symbol pre(String id) {
		return new StreamIndex(id, index - 1).getEncoded();
	}

	private VarDecl curr(VarDecl vd) {
		return new VarDecl(curr(vd.id).str, vd.type);
	}

	private VarDecl pre(VarDecl vd) {
		return new VarDecl(pre(vd.id).str, vd.type);
	}

	private List<VarDecl> curr(List<VarDecl> varDecls) {
		List<VarDecl> result = new ArrayList<>();
		for (VarDecl vd : varDecls) {
			result.add(curr(vd));
		}
		return result;
	}

	private List<VarDecl> pre(List<VarDecl> varDecls) {
		List<VarDecl> result = new ArrayList<>();
		for (VarDecl vd : varDecls) {
			result.add(pre(vd));
		}
		return result;
	}

	@Override
	public Sexp visit(ArrayAccessExpr e) {
		throw new IllegalArgumentException("Arrays must be flattened before translation to sexp");
	}

	@Override
	public Sexp visit(ArrayExpr e) {
		throw new IllegalArgumentException("Arrays must be flattened before translation to sexp");
	}

	@Override
	public Sexp visit(ArrayUpdateExpr e) {
		throw new IllegalArgumentException("Arrays must be flattened before translation to sexp");
	}

	@Override
	public Sexp visit(FunctionCallExpr e) {
		if (e.args.isEmpty()) {
			return new Symbol(SexpUtil.encodeFunction(e.function));
		}

		List<Sexp> args = new ArrayList<>();
		for (Expr expr : e.args) {
			args.add(expr.accept(this));
		}
		return new Cons(SexpUtil.encodeFunction(e.function), args);
	}

	@Override
	public Sexp visit(BinaryExpr e) {
		Sexp left = e.left.accept(this);
		Sexp right = e.right.accept(this);

		switch (e.op) {
		case NOTEQUAL:
		case XOR:
			return new Cons("not", new Cons("=", left, right));

		case ARROW:
			if (pre) {
				throw new IllegalArgumentException("Arrows cannot be nested under pre during translation to sexp");
			}
			return new Cons("ite", INIT, left, right);

		default:
			return new Cons(e.op.toString(), left, right);
		}
	}

	@Override
	public Sexp visit(BoolExpr e) {
		return Sexp.fromBoolean(e.value);
	}

	@Override
	public Sexp visit(CastExpr e) {
		if (e.type == NamedType.REAL) {
			return new Cons("to_real", e.expr.accept(this));
		} else if (e.type == NamedType.INT) {
			return new Cons("to_int", e.expr.accept(this));
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Sexp visit(CondactExpr e) {
		throw new IllegalArgumentException("Condacts must be removed before translation to sexp");
	}

	@Override
	public Sexp visit(IdExpr e) {
		return pre ? pre(e.id) : curr(e.id);
	}

	@Override
	public Sexp visit(IfThenElseExpr e) {
		return new Cons("ite", e.cond.accept(this), e.thenExpr.accept(this), e.elseExpr.accept(this));
	}

	@Override
	public Sexp visit(IntExpr e) {
		return Sexp.fromBigInt(e.value);
	}

	@Override
	public Sexp visit(NodeCallExpr e) {
		throw new IllegalArgumentException("Node calls must be inlined before translation to sexp");
	}

	@Override
	public Sexp visit(RealExpr e) {
		Sexp numerator = Sexp.fromBigInt(e.value.unscaledValue());
		Sexp denominator = Sexp.fromBigInt(BigDecimal.TEN.pow(e.value.scale()).toBigInteger());
		return new Cons("/", numerator, denominator);
	}

	@Override
	public Sexp visit(RecordAccessExpr e) {
		throw new IllegalArgumentException("Records must be flattened before translation to sexp");
	}

	@Override
	public Sexp visit(RecordExpr e) {
		throw new IllegalArgumentException("Records must be flattened before translation to sexp");
	}

	@Override
	public Sexp visit(RecordUpdateExpr e) {
		throw new IllegalArgumentException("Records must be flattened before translation to sexp");
	}

	@Override
	public Sexp visit(TupleExpr e) {
		throw new IllegalArgumentException("Tuples must be flattened before translation to sexp");
	}

	@Override
	public Sexp visit(UnaryExpr e) {
		switch (e.op) {
		case PRE:
			if (pre) {
				throw new IllegalArgumentException("Nested pres must be removed before translation to sexp");
			}
			pre = true;
			Sexp expr = e.expr.accept(this);
			pre = false;
			return expr;

		case NEGATIVE:
			return new Cons("-", new Symbol("0"), e.expr.accept(this));

		default:
			return new Cons(e.op.toString(), e.expr.accept(this));
		}
	}
}
