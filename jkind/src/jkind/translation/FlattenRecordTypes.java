package jkind.translation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import jkind.analysis.TypeChecker;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.Location;
import jkind.lustre.MapVisitor;
import jkind.lustre.Node;
import jkind.lustre.ProjectionExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;

/**
 * Flatten record types in to base type with record projection variables.
 * 
 * Assumption: All node calls have been inlined.
 * 
 * Assumption: All user types have been inlined.
 */
public class FlattenRecordTypes extends MapVisitor {
	public static Node node(Node node) {
		return new FlattenRecordTypes().visitNode(node);
	}

	private final Map<String, RecordType> recordTypes = new HashMap<>();
	private final TypeChecker typeChecker = new TypeChecker();

	private Node visitNode(Node node) {
		typeChecker.repopulateVariableTable(node);
		List<VarDecl> inputs = visitVarDecls(node.inputs);
		List<VarDecl> outputs = visitVarDecls(node.outputs);
		List<VarDecl> locals = visitVarDecls(node.locals);

		List<Equation> equations = visitEquations(node.equations);
		List<Expr> assertions = visitAssertions(node.assertions);
		return new Node(node.location, node.id, inputs, outputs, locals, equations,
				node.properties, assertions);
	}

	private List<VarDecl> visitVarDecls(List<VarDecl> varDecls) {
		List<VarDecl> result = new ArrayList<>();
		for (VarDecl varDecl : varDecls) {
			if (varDecl.type instanceof RecordType) {
				RecordType recordType = (RecordType) varDecl.type;
				recordTypes.put(varDecl.id, recordType);
				result.addAll(visitVarDecl(varDecl.location, varDecl.id, recordType.fields));
			} else {
				result.add(varDecl);
			}
		}
		return result;
	}

	private static List<VarDecl> visitVarDecl(Location location, String id, Map<String, Type> fields) {
		List<VarDecl> result = new ArrayList<>();
		for (Entry<String, Type> entry : fields.entrySet()) {
			String subId = id + "." + entry.getKey();
			if (entry.getValue() instanceof RecordType) {
				RecordType recordType = (RecordType) entry.getValue();
				result.addAll(visitVarDecl(location, subId, recordType.fields));
			} else {
				result.add(new VarDecl(location, subId, entry.getValue()));
			}
		}
		return result;
	}

	private List<Equation> visitEquations(List<Equation> equations) {
		return flattenRightHandSide(flattenLeftHandSide(equations));
	}

	private List<Equation> flattenLeftHandSide(List<Equation> equations) {
		List<Equation> result = new ArrayList<>();
		for (Equation eq : equations) {
			String id = eq.lhs.get(0).id;
			if (recordTypes.containsKey(id)) {
				result.addAll(flattenLeftHandSide(eq.location, id, eq.expr,
						recordTypes.get(id).fields));
			} else {
				result.add(eq);
			}
		}
		return result;
	}

	private List<Equation> flattenLeftHandSide(Location location, String id, Expr expr,
			SortedMap<String, Type> fields) {
		List<Equation> result = new ArrayList<>();
		for (Entry<String, Type> entry : fields.entrySet()) {
			String subId = id + "." + entry.getKey();
			Expr subExpr = new ProjectionExpr(expr.location, expr, entry.getKey());
			if (entry.getValue() instanceof RecordType) {
				RecordType recordType = (RecordType) entry.getValue();
				result.addAll(flattenLeftHandSide(location, subId, subExpr, recordType.fields));
			} else {
				result.add(new Equation(location, new IdExpr(location, subId), subExpr));
			}
		}
		return result;
	}

	private List<Equation> flattenRightHandSide(List<Equation> equations) {
		List<Equation> result = new ArrayList<>();
		for (Equation eq : equations) {
			result.add(new Equation(eq.location, eq.lhs, eq.expr.accept(this)));
		}
		return result;
	}

	private List<Expr> visitAssertions(List<Expr> exprs) {
		List<Expr> result = new ArrayList<>();
		for (Expr expr : exprs) {
			result.add(expr.accept(this));
		}
		return result;
	}

	private Deque<String> projections = new ArrayDeque<>();

	@Override
	public Expr visit(ProjectionExpr e) {
		projections.push(e.field);
		Expr result = e.expr.accept(this);
		projections.pop();
		return result;
	}

	@Override
	public Expr visit(RecordExpr e) {
		String field = projections.pop();
		Expr result = e.fields.get(field).accept(this);
		projections.push(field);
		return result;
	}

	@Override
	public Expr visit(IdExpr e) {
		StringBuilder projected = new StringBuilder();
		projected.append(e.id);
		for (String field : projections) {
			projected.append(".");
			projected.append(field);
		}
		return new IdExpr(e.location, projected.toString());
	}

	@Override
	public Expr visit(IfThenElseExpr e) {
		Deque<String> saved = projections;
		projections = new ArrayDeque<>();
		Expr cond = e.cond.accept(this);
		projections = saved;
		
		Expr thenExpr = e.thenExpr.accept(this);
		Expr elseExpr = e.elseExpr.accept(this);
		return new IfThenElseExpr(e.location, cond, thenExpr, elseExpr);
	}

	/*
	 * We need type information to decompose equality and inequality for record
	 * types. We do this by re-invoking the type checker. If we later run in to
	 * performance problems we can think about caching type information instead.
	 */
	@Override
	public Expr visit(BinaryExpr e) {
		if (e.op == BinaryOp.EQUAL || e.op == BinaryOp.NOTEQUAL) {
			Type type = e.left.accept(typeChecker);
			if (type instanceof RecordType) {
				RecordType recordType = (RecordType) type;
				if (e.op == BinaryOp.EQUAL) {
					Expr conjunction = flattenEquality(e.location, e.left, e.right,
							recordType.fields);
					return conjunction.accept(this);
				} else {
					Expr disjunction = flattenInequality(e.location, e.left, e.right,
							recordType.fields);
					return disjunction.accept(this);
				}
			}
		}

		return super.visit(e);
	}

	private Expr flattenEquality(Location location, Expr left, Expr right, Map<String, Type> fields) {
		return flattenComparison(location, left, right, fields, BinaryOp.EQUAL, BinaryOp.AND);
	}

	private Expr flattenInequality(Location location, Expr left, Expr right,
			Map<String, Type> fields) {
		return flattenComparison(location, left, right, fields, BinaryOp.NOTEQUAL, BinaryOp.OR);
	}

	private Expr flattenComparison(Location location, Expr left, Expr right,
			Map<String, Type> fields, BinaryOp comparison, BinaryOp connective) {
		Expr result = null;
		for (Entry<String, Type> entry : fields.entrySet()) {
			Expr leftProjection = new ProjectionExpr(left.location, left, entry.getKey());
			Expr rightProjection = new ProjectionExpr(right.location, right, entry.getKey());
			Expr curr;
			if (entry.getValue() instanceof RecordType) {
				RecordType recordType = (RecordType) entry.getValue();
				curr = flattenComparison(location, leftProjection, rightProjection,
						recordType.fields, comparison, connective);
			} else {
				curr = new BinaryExpr(location, leftProjection, comparison, rightProjection);
			}

			if (result == null) {
				result = curr;
			} else {
				result = new BinaryExpr(location, result, connective, curr);
			}
		}

		return result;
	}
}
