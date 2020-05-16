package jkind.translation;

import static jkind.lustre.LustreUtil.FALSE;
import static jkind.lustre.LustreUtil.TRUE;
import static jkind.lustre.LustreUtil.and;
import static jkind.lustre.LustreUtil.arrow;
import static jkind.lustre.LustreUtil.eq;
import static jkind.lustre.LustreUtil.id;
import static jkind.lustre.LustreUtil.implies;
import static jkind.lustre.LustreUtil.ite;
import static jkind.lustre.LustreUtil.pre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.Type;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;
import jkind.lustre.builders.ProgramBuilder;
import jkind.lustre.visitors.AstMapVisitor;
import jkind.lustre.visitors.TypeReconstructor;
import jkind.util.Util;

/**
 * Remove all condacts and replace with modified nodes and node calls.
 * 
 * We handle condacts by creating a modified node that takes the clock and
 * default values as arguments. The new node guards stateful computations (pre
 * expressions) so they are only persisted when the clock is active. The new
 * node also guards outputs so that they only change when the clock is active
 * and they default to the specified values.
 */
public class RemoveCondacts {
	public static Program program(Program program) {
		RemoveCondacts remover = new RemoveCondacts(program);
		remover.remove();
		return remover.getResult();
	}

	private final Program program;
	private final Map<String, Node> nodeTable;
	private final List<Node> resultNodes = new ArrayList<>();
	private final TypeReconstructor typeReconstructor;

	private final IdExpr INIT = id("~init");
	private final IdExpr CLOCK = id("~clock");

	private RemoveCondacts(Program program) {
		this.program = program;
		this.nodeTable = Util.getNodeTable(program.nodes);
		this.typeReconstructor = new TypeReconstructor(program);
	}

	private void remove() {
		Program simplified = removeTrivialCondacts();
		for (Node node : simplified.nodes) {
			resultNodes.add(removeCondacts(node));
		}
	}

	private Program removeTrivialCondacts() {
		return (Program) program.accept(new AstMapVisitor() {
			@Override
			public Expr visit(CondactExpr e) {
				if (isLiteralTrue(e.clock)) {
					return e.call.accept(this);
				} else {
					return super.visit(e);
				}
			}
		});
	}

	private boolean isLiteralTrue(Expr e) {
		return e instanceof BoolExpr && ((BoolExpr) e).value;
	}

	private Node removeCondacts(Node node) {
		return (Node) node.accept(new AstMapVisitor() {
			@Override
			public Expr visit(CondactExpr e) {
				NodeCallExpr call = (NodeCallExpr) e.call.accept(this);

				List<Expr> args = new ArrayList<>();
				args.add(e.clock.accept(this));
				args.addAll(e.call.args);
				args.addAll(visitExprs(e.args));

				Node condact = createCondactNode(call.node);
				return new NodeCallExpr(condact.id, args);
			}
		});
	}

	private Node createCondactNode(String id) {
		return nodeTable.computeIfAbsent(id + "~condact", condactId -> createTimedNode(id, condactId, true));
	}

	private Node createTimedNode(String id, String condactId, boolean clockOutputs) {
		Node node = nodeTable.get(id);

		typeReconstructor.setNodeContext(node);
		typeReconstructor.addVariable(new VarDecl(INIT.id, NamedType.BOOL));
		typeReconstructor.addVariable(new VarDecl(CLOCK.id, NamedType.BOOL));

		node = clockArrows(node);
		node = clockPres(node);
		node = defineInit(node);
		node = addClockInput(node);
		if (clockOutputs) {
			node = clockOutputs(node);
		}
		node = clockNodeCalls(node);
		node = clockProperties(node);
		node = renameNode(node, condactId);

		nodeTable.put(node.id, node);
		resultNodes.add(node);
		return node;
	}

	private Node clockArrows(Node node) {
		return (Node) node.accept(new AstMapVisitor() {
			@Override
			public Expr visit(BinaryExpr e) {
				if (e.op == BinaryOp.ARROW) {
					return ite(INIT, e.left.accept(this), e.right.accept(this));
				} else {
					return super.visit(e);
				}
			}
		});
	}

	private Node clockPres(Node node) {
		List<Equation> stateEquations = new ArrayList<>();
		List<VarDecl> stateLocals = new ArrayList<>();
		Map<String, Expr> cache = new HashMap<>();

		node = DistributePres.node(node);
		node = (Node) node.accept(new AstMapVisitor() {
			private int counter = 0;

			@Override
			public Expr visit(UnaryExpr e) {
				if (e.op == UnaryOp.PRE) {
					return cache.computeIfAbsent(e.expr.toString(), ignore -> {
						String state = "~state" + counter++;
						Type type = e.expr.accept(typeReconstructor);
						stateLocals.add(new VarDecl(state, type));
						// state = if clock then expr else pre state
						stateEquations.add(eq(id(state), ite(CLOCK, e.expr.accept(this), pre(id(state)))));
						return pre(id(state));
					});
				} else {
					return super.visit(e);
				}
			}
		});

		NodeBuilder builder = new NodeBuilder(node);
		builder.addLocals(stateLocals);
		builder.addEquations(stateEquations);
		return builder.build();
	}

	private Node defineInit(Node node) {
		NodeBuilder builder = new NodeBuilder(node);
		// init = true -> if pre clock then false else pre init
		builder.addLocal(new VarDecl(INIT.id, NamedType.BOOL));
		builder.addEquation(eq(INIT, arrow(TRUE, ite(pre(CLOCK), FALSE, pre(INIT)))));
		return builder.build();
	}

	private Node addClockInput(Node node) {
		NodeBuilder builder = new NodeBuilder(node);
		builder.clearInputs();
		builder.addInput(new VarDecl(CLOCK.id, NamedType.BOOL));
		builder.addInputs(node.inputs);
		return builder.build();
	}

	private Node clockOutputs(Node node) {
		NodeBuilder builder = new NodeBuilder(node);
		builder.clearOutputs();
		builder.addLocals(node.outputs);

		for (VarDecl output : node.outputs) {
			VarDecl dflt = new VarDecl(output.id + "~default", output.type);
			builder.addInput(dflt);

			VarDecl clocked = new VarDecl(output.id + "~clocked", output.type);
			builder.addOutput(clocked);

			// clocked = if clock then output else (default -> pre clocked)
			Equation eq = eq(id(clocked), ite(CLOCK, id(output), arrow(id(dflt), pre(id(clocked)))));
			builder.addEquation(eq);
		}

		return builder.build();
	}

	private Node clockNodeCalls(Node node) {
		return (Node) node.accept(new AstMapVisitor() {
			@Override
			public Expr visit(NodeCallExpr e) {
				List<Expr> args = new ArrayList<>();
				args.add(CLOCK);
				args.addAll(visitExprs(e.args));

				Node clocked = createClockedNode(e.node);
				return new NodeCallExpr(clocked.id, args);
			}

			@Override
			public Expr visit(CondactExpr e) {
				NodeCallExpr call = (NodeCallExpr) super.visit(e.call);

				List<Expr> args = new ArrayList<>();
				args.add(and(e.clock.accept(this), CLOCK));
				args.addAll(e.call.args);
				args.addAll(visitExprs(e.args));

				Node condact = createCondactNode(call.node);
				return new NodeCallExpr(condact.id, args);
			}
		});
	}

	private Node createClockedNode(String id) {
		return nodeTable.computeIfAbsent(id + "~clocked", clockedId -> createTimedNode(id, clockedId, false));
	}

	private Node clockProperties(Node node) {
		NodeBuilder builder = new NodeBuilder(node);
		builder.clearProperties();

		for (String property : node.properties) {
			VarDecl clocked = new VarDecl(property + "~clocked_property", NamedType.BOOL);
			builder.addLocal(clocked);
			// clocked_property = clock => property
			builder.addEquation(eq(id(clocked), implies(CLOCK, id(property))));
			builder.addProperty(clocked.id);
		}

		return builder.build();
	}

	private Node renameNode(Node node, String id) {
		return new NodeBuilder(node).setId(id).build();
	}

	private Program getResult() {
		return new ProgramBuilder(program).clearNodes().addNodes(resultNodes).build();
	}
}
