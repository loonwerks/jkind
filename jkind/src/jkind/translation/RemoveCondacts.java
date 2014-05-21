package jkind.translation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jkind.analysis.TypeReconstructor;
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
import jkind.lustre.visitors.AstMapVisitor;
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
		List<VarDecl> inputs = new ArrayList<>();
		inputs.add(new VarDecl(clock.id, NamedType.BOOL));
		inputs.addAll(node.inputs);
		return new Node(node.id, inputs, node.outputs, node.locals, node.equations,
				node.properties, node.assertions);
	}

	private Node clockOutputs(Node node, IdExpr clock) {
		List<VarDecl> inputs = new ArrayList<>(node.inputs);
		List<VarDecl> outputs = new ArrayList<>();
		List<VarDecl> locals = new ArrayList<>(node.locals);
		locals.addAll(node.outputs);
		List<Equation> equations = new ArrayList<>(node.equations);

		for (VarDecl output : node.outputs) {
			VarDecl dflt = new VarDecl(output.id + "~default", output.type);
			inputs.add(dflt);

			VarDecl clocked = new VarDecl(output.id + "~clocked", output.type);
			outputs.add(clocked);

			// clocked = if clock then output else (default -> pre clocked)
			Equation eq = new Equation(new IdExpr(clocked.id), new IfThenElseExpr(clock,
					new IdExpr(output.id), new BinaryExpr(new IdExpr(dflt.id), BinaryOp.ARROW,
							new UnaryExpr(UnaryOp.PRE, new IdExpr(clocked.id)))));
			equations.add(eq);
		}

		return new Node(node.id, inputs, outputs, locals, equations, node.properties,
				node.assertions);
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

		List<VarDecl> locals = new ArrayList<>(node.locals);
		locals.addAll(preLocals);
		locals.add(init);
		List<Equation> equations = new ArrayList<>(node.equations);
		equations.addAll(preEquations);
		// init = true -> if pre clock then false else pre init
		equations.add(new Equation(new IdExpr(init.id), new BinaryExpr(new BoolExpr(true),
				BinaryOp.ARROW, new IfThenElseExpr(new UnaryExpr(UnaryOp.PRE, clock), new BoolExpr(
						false), new UnaryExpr(UnaryOp.PRE, new IdExpr(init.id))))));
		return new Node(node.id, node.inputs, node.outputs, locals, equations, node.properties,
				node.assertions);
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
		List<VarDecl> locals = new ArrayList<>(node.locals);
		List<Equation> equations = new ArrayList<>(node.equations);
		List<String> properties = new ArrayList<>();

		for (String property : node.properties) {
			VarDecl clocked = new VarDecl(property + "~clocked_property", NamedType.BOOL);
			locals.add(clocked);
			// clocked_property = clock => property
			equations.add(new Equation(new IdExpr(clocked.id), new BinaryExpr(clock,
					BinaryOp.IMPLIES, new IdExpr(property))));
			properties.add(clocked.id);
		}

		return new Node(node.id, node.inputs, node.outputs, locals, equations, properties,
				node.assertions);
	}

	private Node renameNode(Node node, String id) {
		return new Node(id, node.inputs, node.outputs, node.locals, node.equations,
				node.properties, node.assertions);
	}

	private Program getResult() {
		return new Program(program.location, program.types, program.constants, resultNodes,
				program.main);
	}
}
