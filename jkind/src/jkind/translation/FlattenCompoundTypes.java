package jkind.translation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import jkind.analysis.TypeChecker;
import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.CallExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
import jkind.lustre.InlinedProgram;
import jkind.lustre.IntExpr;
import jkind.lustre.Node;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.ExprMapVisitor;
import jkind.util.Util;

/**
 * Flatten compound types (records, arrays) in to base type with record or array
 * access variables.
 * 
 * Assumption: All node calls have been inlined.
 * 
 * Assumption: All user types have been inlined.
 * 
 * Assumption: All array indices are integer literals
 * 
 * Assumption: All functions have been split so they return a single value. They
 * are annotated with their output. E.g. f returning o has been renamed to f.o
 */
public class FlattenCompoundTypes extends ExprMapVisitor {
	public static InlinedProgram inlinedProgram(InlinedProgram ip) {
		FlattenCompoundTypes flattener = new FlattenCompoundTypes(ip.functions);
		List<Function> flatFunctions = flattener.visitFunctions(ip.functions);
		Node flatNode = flattener.visitNode(ip.node);
		return new InlinedProgram(flatFunctions, flatNode);
	}

	private final Map<String, Function> originalFunctionTable;
	private final TypeChecker typeChecker;

	public FlattenCompoundTypes(List<Function> functions) {
		this.originalFunctionTable = Util.getFunctionTable(functions);
		this.typeChecker = new TypeChecker(functions);
	}

	private List<Function> visitFunctions(List<Function> functions) {
		List<Function> flattenedFunctions = new ArrayList<>();
		for (Function function : functions) {
			List<VarDecl> inputs = flattenVarDecls(function.inputs);
			List<VarDecl> outputs = flattenVarDecls(function.outputs);
			for (VarDecl output : outputs) {
				String id = getBase(function.id) + "." + output.id;
				List<VarDecl> singleOutput = Collections.singletonList(output);
				flattenedFunctions.add(new Function(function.location, id, inputs, singleOutput));
			}
		}
		return flattenedFunctions;
	}

	private String getBase(String id) {
		return id.substring(0, id.indexOf('.'));
	}

	private Node visitNode(Node node) {
		typeChecker.repopulateVariableTable(node);
		List<VarDecl> inputs = flattenVarDecls(node.inputs);
		List<VarDecl> outputs = flattenVarDecls(node.outputs);
		List<VarDecl> locals = flattenVarDecls(node.locals);

		List<Equation> equations = visitEquations(node.equations);
		List<Expr> assertions = visitAll(node.assertions);
		return new Node(node.id, inputs, outputs, locals, equations, node.properties, assertions);
	}

	private List<VarDecl> flattenVarDecls(List<VarDecl> varDecls) {
		List<VarDecl> result = new ArrayList<>();
		for (VarDecl varDecl : varDecls) {
			result.addAll(flattenVarDecl(varDecl.id, varDecl.type));
		}
		return result;
	}

	private static List<VarDecl> flattenVarDecl(String id, Type type) {
		if (type instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) type;
			return flattenArrayVarDecl(id, arrayType.base, arrayType.size);
		} else if (type instanceof RecordType) {
			RecordType recordType = (RecordType) type;
			return flattenRecordVarDecl(id, recordType.fields);
		} else {
			return Collections.singletonList(new VarDecl(id, type));
		}
	}

	private static List<VarDecl> flattenArrayVarDecl(String id, Type type, int size) {
		List<VarDecl> result = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			result.addAll(flattenVarDecl(id + "[" + i + "]", type));
		}
		return result;
	}

	private static List<VarDecl> flattenRecordVarDecl(String id, Map<String, Type> fields) {
		List<VarDecl> result = new ArrayList<>();
		for (Entry<String, Type> entry : fields.entrySet()) {
			result.addAll(flattenVarDecl(id + "." + entry.getKey(), entry.getValue()));
		}
		return result;
	}

	private List<Equation> visitEquations(List<Equation> equations) {
		return flattenRightHandSide(flattenTopLevelLeftHandSide(equations));
	}

	private List<Equation> flattenTopLevelLeftHandSide(List<Equation> equations) {
		List<Equation> result = new ArrayList<>();
		for (Equation eq : equations) {
			result.addAll(flattenLeftHandSide(eq, getType(eq.lhs.get(0))));
		}
		return result;
	}

	/*
	 * We need to pass in the type of the equation variable since new variable
	 * names are being introduced and the type checker does not know about them.
	 */
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
			IdExpr subId = new IdExpr(id + "[" + i + "]");
			Expr subExpr = new ArrayAccessExpr(expr, i);
			Equation equation = new Equation(subId, subExpr);
			result.addAll(flattenLeftHandSide(equation, base));
		}
		return result;
	}

	private List<Equation> flattenLeftHandSideRecord(String id, Expr expr,
			SortedMap<String, Type> fields) {
		List<Equation> result = new ArrayList<>();
		for (Entry<String, Type> entry : fields.entrySet()) {
			IdExpr subId = new IdExpr(id + "." + entry.getKey());
			Expr subExpr = new RecordAccessExpr(expr, entry.getKey());
			Equation equation = new Equation(subId, subExpr);
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

	private Deque<Access> accesses = new ArrayDeque<>();

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
	public Expr visit(ArrayUpdateExpr e) {
		return expandConstantArrayUpdate(e).accept(this);
	}

	private Expr expandConstantArrayUpdate(ArrayUpdateExpr e) {
		int index = ((IntExpr) e.index).value.intValue();
		ArrayType type = (ArrayType) getType(e.array);
		List<Expr> elements = new ArrayList<>();

		for (int i = 0; i < type.size; i++) {
			if (i == index) {
				elements.add(e.value);
			} else {
				elements.add(new ArrayAccessExpr(e.array, i));
			}
		}

		return new ArrayExpr(elements);
	}

	@Override
	public Expr visit(CallExpr e) {
		List<Expr> splitArgs = new ArrayList<>();
		Function function = originalFunctionTable.get(e.name);

		for (int i = 0; i < function.inputs.size(); i++) {
			splitArgs.addAll(splitExpr(e.args.get(i), function.inputs.get(i).type));
		}

		// Flatten projections within arguments
		Deque<Access> saved = accesses;
		accesses = new ArrayDeque<>();
		List<Expr> flatArgs = visitAll(splitArgs);
		accesses = saved;

		return new CallExpr(e.location, getAccess(e.name), flatArgs);
	}

	private List<Expr> splitExpr(Expr expr, Type type) {
		if (type instanceof ArrayType) {
			ArrayType arrayType = (ArrayType) type;
			return splitArrayExpr(expr, arrayType.base, arrayType.size);
		} else if (type instanceof RecordType) {
			RecordType recordType = (RecordType) type;
			return splitRecordExpr(expr, recordType.fields);
		} else {
			return Collections.singletonList(expr);
		}
	}

	private List<Expr> splitArrayExpr(Expr expr, Type base, int size) {
		List<Expr> result = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			result.addAll(splitExpr(new ArrayAccessExpr(expr, i), base));
		}
		return result;
	}

	private List<Expr> splitRecordExpr(Expr expr, Map<String, Type> fields) {
		List<Expr> result = new ArrayList<>();
		for (Entry<String, Type> entry : fields.entrySet()) {
			result.addAll(splitExpr(new RecordAccessExpr(expr, entry.getKey()), entry.getValue()));
		}
		return result;
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
		return new IdExpr(getAccess(e.id));
	}

	public String getAccess(String base) {
		StringBuilder result = new StringBuilder();
		result.append(base);
		for (Access access : accesses) {
			result.append(access);
		}
		return result.toString();
	}

	@Override
	public Expr visit(IfThenElseExpr e) {
		Deque<Access> saved = accesses;
		accesses = new ArrayDeque<>();
		Expr cond = e.cond.accept(this);
		accesses = saved;

		Expr thenExpr = e.thenExpr.accept(this);
		Expr elseExpr = e.elseExpr.accept(this);
		return new IfThenElseExpr(cond, thenExpr, elseExpr);
	}

	@Override
	public Expr visit(BinaryExpr e) {
		if (e.op == BinaryOp.EQUAL || e.op == BinaryOp.NOTEQUAL) {
			Type type = getType(e.left);
			if (type instanceof ArrayType) {
				ArrayType arrayType = (ArrayType) type;
				if (e.op == BinaryOp.EQUAL) {
					Expr conjunction = flattenArrayEquality(e.left, e.right, arrayType.size);
					return conjunction.accept(this);
				} else {
					Expr disjunction = flattenArrayInequality(e.left, e.right, arrayType.size);
					return disjunction.accept(this);
				}
			} else if (type instanceof RecordType) {
				RecordType recordType = (RecordType) type;
				if (e.op == BinaryOp.EQUAL) {
					Expr conjunction = flattenRecordEquality(e.left, e.right, recordType.fields);
					return conjunction.accept(this);
				} else {
					Expr disjunction = flattenRecordInequality(e.left, e.right, recordType.fields);
					return disjunction.accept(this);
				}
			}
		}

		return super.visit(e);
	}

	private Expr flattenArrayEquality(Expr left, Expr right, int size) {
		return flattenArrayComparison(left, right, size, BinaryOp.EQUAL, BinaryOp.AND);
	}

	private Expr flattenArrayInequality(Expr left, Expr right, int size) {
		return flattenArrayComparison(left, right, size, BinaryOp.NOTEQUAL, BinaryOp.OR);
	}

	private Expr flattenRecordEquality(Expr left, Expr right, Map<String, Type> fields) {
		return flattenRecordComparison(left, right, fields, BinaryOp.EQUAL, BinaryOp.AND);
	}

	private Expr flattenRecordInequality(Expr left, Expr right, Map<String, Type> fields) {
		return flattenRecordComparison(left, right, fields, BinaryOp.NOTEQUAL, BinaryOp.OR);
	}

	private Expr flattenRecordComparison(Expr left, Expr right, Map<String, Type> fields,
			BinaryOp comparison, BinaryOp connective) {
		Expr result = null;
		for (Entry<String, Type> entry : fields.entrySet()) {
			Expr leftAccess = new RecordAccessExpr(left, entry.getKey());
			Expr rightAccess = new RecordAccessExpr(right, entry.getKey());
			Expr curr = new BinaryExpr(leftAccess, comparison, rightAccess);
			if (result == null) {
				result = curr;
			} else {
				result = new BinaryExpr(result, connective, curr);
			}
		}
		return result;
	}

	private Expr flattenArrayComparison(Expr left, Expr right, int size, BinaryOp comparison,
			BinaryOp connective) {
		Expr result = null;
		for (int i = 0; i < size; i++) {
			Expr leftAccess = new ArrayAccessExpr(left, i);
			Expr rightAccess = new ArrayAccessExpr(right, i);
			Expr curr = new BinaryExpr(leftAccess, comparison, rightAccess);
			if (result == null) {
				result = curr;
			} else {
				result = new BinaryExpr(result, connective, curr);
			}
		}
		return result;
	}

	/*
	 * We need type information to decompose equality and inequality for
	 * compound types. We do this by re-invoking the type checker. If we later
	 * run in to performance problems we can think about caching type
	 * information instead.
	 */
	private Type getType(Expr e) {
		return e.accept(typeChecker);
	}
}
