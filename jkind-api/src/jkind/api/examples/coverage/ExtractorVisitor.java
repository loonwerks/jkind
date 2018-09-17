package jkind.api.examples.coverage;

import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import jkind.lustre.Location;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.RealExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.RecordExpr;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;
import jkind.lustre.visitors.TypeAwareAstMapVisitor;
import jkind.lustre.visitors.TypeReconstructor;

public class ExtractorVisitor extends TypeAwareAstMapVisitor {
	public final static String PREFIX = "__extracted__";
	private final Map<String, ELocation> locationMap = new HashMap<>();
	private Set<String> constants;
	private int extractedCounter = 0;

	private final List<VarDecl> newLocals = new ArrayList<>();
	private final List<Equation> newEquations = new ArrayList<>();
	private final List<String> newIvc = new ArrayList<>();

	public Map<String, ELocation> getLocationMap() {
		return locationMap;
	}

	@Override
	public Program visit(Program e) {
		boolean enumsAsInts = false;
		typeReconstructor = new TypeReconstructor(e, enumsAsInts);
		constants = e.constants.stream().map(c -> c.id).collect(toSet());
		return new Program(e.location, e.types, e.constants, visitFunctions(e.functions), visitNodes(e.nodes), e.main);
	}

	@Override
	public Node visit(Node e) {
		newLocals.clear();
		newEquations.clear();
		newIvc.clear();

		e = super.visit(e);

		NodeBuilder builder = new NodeBuilder(e);
		builder.addLocals(newLocals);
		builder.addEquations(newEquations);
		builder.clearIvc().addIvcs(newIvc);
		return builder.build();
	}

	private Expr makeVar(Location location, Expr e) {
		if (!(location instanceof ELocation)) {
			throw new IllegalArgumentException("Unknown location for: " + e);
		}

		String name = PREFIX + extractedCounter++;
		locationMap.put(name, (ELocation) location);
		IdExpr var = new IdExpr(name);
		VarDecl varDecl = new VarDecl(name, getType(e));
		typeReconstructor.addVariable(varDecl);
		newLocals.add(varDecl);
		newEquations.add(new Equation(var, e));
		newIvc.add(name);
		return var;
	}

	private Expr makeVar(Expr e) {
		return makeVar(e.location, e);
	}

	@Override
	public Expr visit(ArrayAccessExpr e) {
		return makeVar(super.visit(e));
	}

	@Override
	public Expr visit(ArrayExpr e) {
		return makeVar(super.visit(e));
	}

	@Override
	public Expr visit(ArrayUpdateExpr e) {
		return makeVar(super.visit(e));
	}

	@Override
	public Expr visit(BinaryExpr e) {
		// Avoid creating non-linearities
		switch (e.op) {
		case MULTIPLY:
			if (isConstant(e.left)) {
				return makeVar(new BinaryExpr(e.location, e.left, e.op, e.right.accept(this)));
			} else if (isConstant(e.right)) {
				return makeVar(new BinaryExpr(e.location, e.left.accept(this), e.op, e.right));
			} else {
				System.err.println("Unable to handle expression: " + e);
				System.exit(-1);
				return null;
			}

		case DIVIDE:
		case INT_DIVIDE:
		case MODULUS:
			return makeVar(new BinaryExpr(e.location, e.left.accept(this), e.op, e.right));

		default:
			return makeVar(super.visit(e));
		}
	}

	private boolean isConstant(Expr expr) {
		if (expr instanceof RealExpr || expr instanceof IntExpr) {
			return true;
		}
		if (expr instanceof IdExpr) {
			IdExpr idExpr = (IdExpr) expr;
			return constants.contains(idExpr.id);
		}
		return false;
	}

	@Override
	public Expr visit(BoolExpr e) {
		return makeVar(super.visit(e));
	}

	@Override
	public Expr visit(CastExpr e) {
		return makeVar(super.visit(e));
	}

	@Override
	public Expr visit(CondactExpr e) {
		if (e.clock instanceof BoolExpr && ((BoolExpr) e.clock).value) {
			return makeVar(e.location, e.call);
		} else {
			return unsupported(e);
		}
	}

	@Override
	public Expr visit(IdExpr e) {
		return makeVar(super.visit(e));
	}

	@Override
	public Expr visit(IfThenElseExpr e) {
		return makeVar(super.visit(e));
	}

	@Override
	public Expr visit(IntExpr e) {
		return makeVar(super.visit(e));
	}

	@Override
	public Expr visit(NodeCallExpr e) {
		return makeVar(super.visit(e));
	}

	@Override
	public Expr visit(RealExpr e) {
		return makeVar(super.visit(e));
	}

	@Override
	public Expr visit(RecordAccessExpr e) {
		return makeVar(super.visit(e));
	}

	@Override
	public Expr visit(RecordExpr e) {
		return makeVar(super.visit(e));
	}

	@Override
	public Expr visit(RecordUpdateExpr e) {
		return makeVar(super.visit(e));
	}

	@Override
	public Expr visit(TupleExpr e) {
		// Disabled expressions due to the way IVCs interact with flattening
		return unsupported(e);
	}

	@Override
	public Expr visit(UnaryExpr e) {
		return makeVar(super.visit(e));
	}

	private Expr unsupported(Object e) {
		System.err.println(e.getClass().getSimpleName() + " is not supported");
		System.exit(-1);
		return null;
	}
}
