package jkind.translation.compound;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.Node;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.ExprMapVisitor;
import jkind.translation.Access;
import jkind.translation.ArrayAccess;
import jkind.translation.RecordAccess;

/**
 * Flatten array and record variables to scalars variables
 * 
 * Assumption: All node calls have been inlined.
 * 
 * Assumption: All array indices are integer literals
 */
public class FlattenCompoundVariables extends ExprMapVisitor {
	public static Node node(Node node) {
		return new FlattenCompoundVariables().visitNode(node);
	}
	
	private final Map<String, Type> originalTypes = new HashMap<>();

	private Node visitNode(Node node) {
		addOriginalTypes(node.inputs);
		addOriginalTypes(node.outputs);
		addOriginalTypes(node.locals);
		
		List<VarDecl> inputs = flattenVarDecls(node.inputs);
		List<VarDecl> outputs = flattenVarDecls(node.outputs);
		List<VarDecl> locals = flattenVarDecls(node.locals);

		List<Equation> equations = visitEquations(node.equations);
		List<Expr> assertions = visitAll(node.assertions);
		return new Node(node.id, inputs, outputs, locals, equations, node.properties,
				assertions);
	}

	private void addOriginalTypes(List<VarDecl> varDecls) {
		for (VarDecl varDecl : varDecls) {
			originalTypes.put(varDecl.id, varDecl.type);
		}
	}

	private List<VarDecl> flattenVarDecls(List<VarDecl> varDecls) {
		List<VarDecl> result = new ArrayList<>();
		for (VarDecl varDecl : varDecls) {
			IdExpr id = new IdExpr(varDecl.id);
			for (ExprType et : CompoundUtil.flattenExpr(id, varDecl.type)) {
				result.add(new VarDecl(et.expr.toString(), et.type));
			}
		}
		return result;
	}

	private List<Equation> visitEquations(List<Equation> equations) {
		return flattenRightHandSide(flattenTopLevelLeftHandSide(equations));
	}

	private List<Equation> flattenTopLevelLeftHandSide(List<Equation> equations) {
		List<Equation> result = new ArrayList<>();
		for (Equation eq : equations) {
			Type type = originalTypes.get(eq.lhs.get(0).id);
			result.addAll(flattenLeftHandSide(eq, type));
		}
		return result;
	}

	private List<Equation> flattenLeftHandSide(Equation eq, Type type) {
		String id = eq.lhs.get(0).id;
		if (type instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) type;
			return flattenLeftHandSideArray(id, eq.expr, arrayType.base, arrayType.size);
		} else if (type instanceof RecordType) {
			RecordType recordType = (RecordType) type;
			return flattenLeftHandSideRecord(id, eq.expr, recordType.fields);
		} else {
			return Collections.singletonList(eq);
		}
	}

	private List<Equation> flattenLeftHandSideArray(String id, Expr expr, Type base, int size) {
		List<Equation> result = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			IdExpr accessId = new IdExpr(id + "[" + i + "]");
			Expr accessExpr = new ArrayAccessExpr(expr, i);
			Equation equation = new Equation(accessId, accessExpr);
			result.addAll(flattenLeftHandSide(equation, base));
		}
		return result;
	}

	private List<Equation> flattenLeftHandSideRecord(String id, Expr expr,
			SortedMap<String, Type> fields) {
		List<Equation> result = new ArrayList<>();
		for (Entry<String, Type> entry : fields.entrySet()) {
			IdExpr accessId = new IdExpr(id + "." + entry.getKey());
			Expr accessExpr = new RecordAccessExpr(expr, entry.getKey());
			Equation equation = new Equation(accessId, accessExpr);
			result.addAll(flattenLeftHandSide(equation, entry.getValue()));
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

	private final Deque<Access> accesses = new ArrayDeque<>();

	@Override
	public Expr visit(ArrayAccessExpr e) {
		IntExpr intExpr = (IntExpr) e.index;
		accesses.push(new ArrayAccess(intExpr.value));
		Expr result = e.array.accept(this);
		accesses.pop();
		return result;
	}

	@Override
	public Expr visit(ArrayExpr e) {
		if (accesses.isEmpty()) {
			return super.visit(e);
		} else {
			ArrayAccess arrayAccess = (ArrayAccess) accesses.pop();
			Expr result = e.elements.get(arrayAccess.index.intValue()).accept(this);
			accesses.push(arrayAccess);
			return result;
		}
	}

	@Override
	public Expr visit(RecordAccessExpr e) {
		accesses.push(new RecordAccess(e.field));
		Expr result = e.record.accept(this);
		accesses.pop();
		return result;
	}

	@Override
	public Expr visit(RecordExpr e) {
		if (accesses.isEmpty()) {
			return super.visit(e);
		} else {
			RecordAccess recordAccess = (RecordAccess) accesses.pop();
			Expr result = e.fields.get(recordAccess.field).accept(this);
			accesses.push(recordAccess);
			return result;
		}
	}

	@Override
	public Expr visit(IdExpr e) {
		StringBuilder projected = new StringBuilder();
		projected.append(e.id);
		for (Access access : accesses) {
			projected.append(access);
		}
		return new IdExpr(projected.toString());
	}

	@Override
	public Expr visit(IfThenElseExpr e) {
		Expr cond = e.cond.accept(new FlattenCompoundVariables());
		Expr thenExpr = e.thenExpr.accept(this);
		Expr elseExpr = e.elseExpr.accept(this);
		return new IfThenElseExpr(cond, thenExpr, elseExpr);
	}
}
