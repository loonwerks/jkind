package jkind.translation;

import static jkind.lustre.LustreUtil.TRUE;
import static jkind.lustre.LustreUtil.and;
import static jkind.lustre.LustreUtil.arrow;
import static jkind.lustre.LustreUtil.eq;
import static jkind.lustre.LustreUtil.id;
import static jkind.lustre.LustreUtil.ite;
import static jkind.lustre.LustreUtil.not;
import static jkind.lustre.LustreUtil.or;
import static jkind.lustre.LustreUtil.pre;

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
	
	private final VarDecl PREINIT = new VarDecl("~preinit", NamedType.BOOL);
	private final VarDecl INIT = new VarDecl("~init", NamedType.BOOL);

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
		return nodeTable.computeIfAbsent(id + "~condact", condactId -> {
			Node node = nodeTable.get(id);
			typeReconstructor.setNodeContext(node);

			IdExpr clock = new IdExpr("~clock");
			node = clockArrowsAndPres(node, clock);
			node = defineBuiltins(node, clock);
			node = clockInputs(node, clock);
			node = addClockInput(node, clock);
			node = clockOutputs(node, clock);
			node = clockNodeCalls(node, clock);
			node = clockProperties(node);
			node = renameNode(node, condactId);

			nodeTable.put(node.id, node);
			resultNodes.add(node);
			return node;
		});
	}

	private Node addClockInput(Node node, IdExpr clock) {
		NodeBuilder builder = new NodeBuilder(node);
		builder.clearInputs();
		builder.addInput(new VarDecl(clock.id, NamedType.BOOL));
		builder.addInputs(node.inputs);
		return builder.build();
	}

	private Node defineBuiltins(Node node, IdExpr clock) {
		NodeBuilder builder = new NodeBuilder(node);

		// preinit = (not clock) and (true -> pre preinit)
		builder.addLocal(PREINIT);
		builder.addEquation(eq(id(PREINIT), and(not(clock), arrow(TRUE, pre(id(PREINIT))))));
		
		// init = clock -> if clock then pre preinit else pre init
		builder.addLocal(INIT);
		builder.addEquation(eq(id(INIT), arrow(clock, ite(clock, pre(id(PREINIT)), pre(id(INIT))))));
		
		return builder.build();
	}

	private Node clockInputs(Node node, IdExpr clock) {
		NodeBuilder builder = new NodeBuilder(node);
		builder.clearInputs();
		builder.addLocals(node.inputs);

		for (VarDecl input : node.inputs) {
			VarDecl ext = new VarDecl(input.id + "~external", input.type);
			builder.addInput(ext);

			// input = if clock then ext else pre input
			Equation eq = eq(id(input), ite(clock, id(ext), pre(id(input))));
			builder.addEquation(eq);
		}

		return builder.build();
	}

	private Node clockOutputs(Node node, IdExpr clock) {
		NodeBuilder builder = new NodeBuilder(node);
		builder.clearOutputs();
		builder.addLocals(node.outputs);

		for (VarDecl output : node.outputs) {
			VarDecl dflt = new VarDecl(output.id + "~default", output.type);
			builder.addInput(dflt);

			VarDecl ext = new VarDecl(output.id + "~external", output.type);
			builder.addOutput(ext);

			// ext = if clock then output else (default -> pre ext)
			Equation eq = eq(id(ext), ite(clock, id(output), arrow(id(dflt), pre(id(ext)))));
			builder.addEquation(eq);
		}

		return builder.build();
	}

	private Node clockArrowsAndPres(Node node, final IdExpr clock) {
		final List<Equation> preEquations = new ArrayList<>();
		final List<VarDecl> preLocals = new ArrayList<>();
		node = (Node) node.accept(new AstMapVisitor() {
			private int counter = 0;

			@Override
			public Expr visit(BinaryExpr e) {
				if (e.op == BinaryOp.ARROW) {
					return ite(id(INIT), e.left.accept(this), e.right.accept(this));
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
					// state = if clock then pre expr else pre state
					preEquations.add(eq(id(state), ite(clock, pre(e.expr.accept(this)), pre(id(state)))));
					return id(state);
				} else {
					return super.visit(e);
				}
			}
		});

		NodeBuilder builder = new NodeBuilder(node);
		builder.addLocals(preLocals);
		builder.addEquations(preEquations);
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
				args.add(and(e.clock.accept(this), clock));
				args.addAll(e.call.args);
				args.addAll(visitExprs(e.args));

				Node condact = createCondactNode(call.node);
				return new NodeCallExpr(condact.id, args);
			}
		});
	}

	private Node createClockedNode(String id) {
		return nodeTable.computeIfAbsent(id + "~clocked", condactId -> {
			Node node = nodeTable.get(id);
			typeReconstructor.setNodeContext(node);

			IdExpr clock = new IdExpr("~clock");
			node = clockArrowsAndPres(node, clock);
			node = addClockInput(node, clock);
			/*
			 * This is a node called within a condact so we do not need to clock
			 * the inputs or outputs since the outer condact will handle all of
			 * that.
			 */
			node = clockNodeCalls(node, clock);
			node = clockProperties(node);
			node = renameNode(node, condactId);

			nodeTable.put(node.id, node);
			resultNodes.add(node);
			return node;
		});
	}

	private Node clockProperties(Node node) {
		NodeBuilder builder = new NodeBuilder(node);
		builder.clearProperties();

		for (String property : node.properties) {
			VarDecl clocked = new VarDecl(property + "~clocked_property", NamedType.BOOL);
			builder.addLocal(clocked);
			// clocked_property = preinit or property
			builder.addEquation(eq(id(clocked), or(id(PREINIT.id), id(property))));
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
