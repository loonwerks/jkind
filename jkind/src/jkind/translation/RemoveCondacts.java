package jkind.translation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.BoolExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.IfThenElseExpr;
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
		String condactId = id + "~condact";
		if (nodeTable.containsKey(condactId)) {
			return nodeTable.get(condactId);
		}

		Node node = nodeTable.get(id);
		typeReconstructor.setNodeContext(node);
		IdExpr clock = new IdExpr("~clock");
		node = addClock(node, clock);
		node = clockArrowsAndPres(node, clock);
		node = clockOutputs(node, clock);
		node = clockNodeCalls(node, clock);
		node = clockProperties(node, clock);
		node = renameNode(node, condactId);

		nodeTable.put(node.id, node);
		resultNodes.add(node);
		return node;
	}

	private Node addClock(Node node, IdExpr clock) {
		NodeBuilder builder = new NodeBuilder(node);
		builder.clearInputs();
		builder.addInput(new VarDecl(clock.id, NamedType.BOOL));
		builder.addInputs(node.inputs);
		return builder.build();
	}

	private Node clockOutputs(Node node, IdExpr clock) {
		NodeBuilder builder = new NodeBuilder(node);
		builder.clearOutputs();
		builder.addLocals(node.outputs);

		for (VarDecl output : node.outputs) {
			VarDecl dflt = new VarDecl(output.id + "~default", output.type);
			builder.addInput(dflt);

			VarDecl clocked = new VarDecl(output.id + "~clocked", output.type);
			builder.addOutput(clocked);

			// clocked = if clock then output else (default -> pre clocked)
			Equation eq = new Equation(new IdExpr(clocked.id), new IfThenElseExpr(clock,
					new IdExpr(output.id), new BinaryExpr(new IdExpr(dflt.id), BinaryOp.ARROW,
							new UnaryExpr(UnaryOp.PRE, new IdExpr(clocked.id)))));
			builder.addEquation(eq);
		}

		return builder.build();
	}

	private Node clockArrowsAndPres(Node node, final IdExpr clock) {
		final VarDecl init = new VarDecl("~init", NamedType.BOOL);
		final List<Equation> preEquations = new ArrayList<>();
		final List<VarDecl> preLocals = new ArrayList<>();
		node = (Node) node.accept(new AstMapVisitor() {
			private int counter = 0;

			@Override
			public Expr visit(BinaryExpr e) {
				if (e.op == BinaryOp.ARROW) {
					return new IfThenElseExpr(new IdExpr(init.id), e.left.accept(this), e.right
							.accept(this));
				} else {
					return super.visit(e);
				}
			}

			@Override
			public Expr visit(UnaryExpr e) {
				if (e.op == UnaryOp.PRE) {
					String state = "~state" + counter++;
					Type type = e.expr.accept(typeReconstructor);
					preLocals.add(new VarDecl(state, type));
					// state = if clock then expr else pre state
					preEquations.add(new Equation(new IdExpr(state), new IfThenElseExpr(clock,
							e.expr, new UnaryExpr(UnaryOp.PRE, new IdExpr(state)))));
					return new UnaryExpr(UnaryOp.PRE, new IdExpr(state));
				} else {
					return super.visit(e);
				}
			}
		});

		NodeBuilder builder = new NodeBuilder(node);
		builder.addLocals(preLocals);
		builder.addLocal(init);
		builder.addEquations(preEquations);
		// init = true -> if pre clock then false else pre init
		builder.addEquation(new Equation(new IdExpr(init.id), new BinaryExpr(new BoolExpr(true),
				BinaryOp.ARROW, new IfThenElseExpr(new UnaryExpr(UnaryOp.PRE, clock), new BoolExpr(
						false), new UnaryExpr(UnaryOp.PRE, new IdExpr(init.id))))));
		return builder.build();
	}

	private Node clockNodeCalls(Node node, final IdExpr clock) {
		return (Node) node.accept(new AstMapVisitor() {
			@Override
			public Expr visit(NodeCallExpr e) {
				List<Expr> args = new ArrayList<>();
				args.add(clock);
				args.addAll(visitExprs(e.args));

				Node clocked = createClockedNode(e.node);
				return new NodeCallExpr(clocked.id, args);
			}

			@Override
			public Expr visit(CondactExpr e) {
				NodeCallExpr call = (NodeCallExpr) super.visit(e.call);

				List<Expr> args = new ArrayList<>();
				args.add(new BinaryExpr(e.clock.accept(this), BinaryOp.AND, clock));
				args.addAll(e.call.args);
				args.addAll(visitExprs(e.args));

				Node condact = createCondactNode(call.node);
				return new NodeCallExpr(condact.id, args);
			}
		});
	}

	private Node createClockedNode(String id) {
		String condactId = id + "~clocked";
		if (nodeTable.containsKey(condactId)) {
			return nodeTable.get(condactId);
		}

		Node node = nodeTable.get(id);
		typeReconstructor.setNodeContext(node);
		IdExpr clock = new IdExpr("~clock");
		node = addClock(node, clock);
		node = clockArrowsAndPres(node, clock);
		// Because this is for a node call within a condact, we do not need to
		// clock the outputs. The outer condact will ignore outputs when the
		// clock is false.
		node = clockNodeCalls(node, clock);
		node = clockProperties(node, clock);
		node = renameNode(node, condactId);

		nodeTable.put(node.id, node);
		resultNodes.add(node);
		return node;
	}

	private Node clockProperties(Node node, final IdExpr clock) {
		NodeBuilder builder = new NodeBuilder(node);
		builder.clearProperties();

		for (String property : node.properties) {
			VarDecl clocked = new VarDecl(property + "~clocked_property", NamedType.BOOL);
			builder.addLocal(clocked);
			// clocked_property = clock => property
			builder.addEquation(new Equation(new IdExpr(clocked.id), new BinaryExpr(clock,
					BinaryOp.IMPLIES, new IdExpr(property))));
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
