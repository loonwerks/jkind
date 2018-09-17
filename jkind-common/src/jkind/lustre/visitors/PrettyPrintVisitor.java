package jkind.lustre.visitors;

import static java.util.stream.Collectors.joining;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Constant;
import jkind.lustre.Contract;
import jkind.lustre.EnumType;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.FunctionCallExpr;
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
import jkind.lustre.RecordType;
import jkind.lustre.RecordUpdateExpr;
import jkind.lustre.TupleExpr;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;

public class PrettyPrintVisitor implements AstVisitor<Void, Void> {
	private StringBuilder sb = new StringBuilder();
	private String main;

	@Override
	public String toString() {
		return sb.toString();
	}

	protected void write(Object o) {
		sb.append(o);
	}

	private static final String seperator = System.getProperty("line.separator");

	private void newline() {
		write(seperator);
	}

	@Override
	public Void visit(Program program) {
		main = program.main;

		if (!program.types.isEmpty()) {
			for (TypeDef typeDef : program.types) {
				typeDef.accept(this);
				newline();
			}
			newline();
		}

		if (!program.constants.isEmpty()) {
			for (Constant constant : program.constants) {
				constant.accept(this);
				newline();
			}
			newline();
		}

		if (!program.functions.isEmpty()) {
			for (Function function : program.functions) {
				function.accept(this);
				newline();
			}
			newline();
		}

		Iterator<Node> iterator = program.nodes.iterator();
		while (iterator.hasNext()) {
			iterator.next().accept(this);
			newline();
			if (iterator.hasNext()) {
				newline();
			}
		}

		return null;
	}

	@Override
	public Void visit(TypeDef typeDef) {
		write("type ");
		write(typeDef.id);
		write(" = ");
		writeType(typeDef.type);
		write(";");
		return null;
	}

	private void writeType(Type type) {
		if (type instanceof RecordType) {
			RecordType recordType = (RecordType) type;
			write("struct {");
			Iterator<Entry<String, Type>> iterator = recordType.fields.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Type> entry = iterator.next();
				write(entry.getKey());
				write(" : ");
				write(entry.getValue());
				if (iterator.hasNext()) {
					write("; ");
				}
			}
			write("}");
		} else if (type instanceof EnumType) {
			EnumType enumType = (EnumType) type;
			write("enum {");
			Iterator<String> iterator = enumType.values.iterator();
			while (iterator.hasNext()) {
				write(iterator.next());
				if (iterator.hasNext()) {
					write(", ");
				}
			}
			write("}");
		} else {
			write(type);
		}
	}

	@Override
	public Void visit(Constant constant) {
		write("const ");
		write(constant.id);
		write(" = ");
		expr(constant.expr);
		write(";");
		return null;
	}

	@Override
	public Void visit(Function function) {
		write("function ");
		write(function.id);
		write("(");
		newline();
		varDecls(function.inputs);
		newline();
		write(") returns (");
		newline();
		varDecls(function.outputs);
		newline();
		write(");");
		newline();
		return null;
	}

	@Override
	public Void visit(Node node) {
		write("node ");
		write(node.id);
		write("(");
		newline();
		varDecls(node.inputs);
		newline();
		write(") returns (");
		newline();
		varDecls(node.outputs);
		newline();
		write(");");
		newline();

		if (node.contract != null) {
			node.contract.accept(this);
		}

		if (!node.locals.isEmpty()) {
			write("var");
			newline();
			varDecls(node.locals);
			write(";");
			newline();
		}
		write("let");
		newline();

		if (node.id.equals(main)) {
			write("  --%MAIN;");
			newline();
		}

		for (Equation equation : node.equations) {
			write("  ");
			equation.accept(this);
			newline();
			newline();
		}

		for (Expr assertion : node.assertions) {
			assertion(assertion);
			newline();
		}

		if (!node.properties.isEmpty()) {
			for (String property : node.properties) {
				property(property);
			}
			newline();
		}

		if (node.realizabilityInputs != null) {
			write("  --%REALIZABLE ");
			write(node.realizabilityInputs.stream().collect(joining(", ")));
			write(";");
			newline();
			newline();
		}

		if (!node.ivc.isEmpty()) {
			write("  --%IVC ");
			write(node.ivc.stream().collect(joining(", ")));
			write(";");
			newline();
			newline();
		}

		write("tel;");
		return null;
	}

	private void varDecls(List<VarDecl> varDecls) {
		Iterator<VarDecl> iterator = varDecls.iterator();
		while (iterator.hasNext()) {
			write("  ");
			iterator.next().accept(this);
			if (iterator.hasNext()) {
				write(";");
				newline();
			}
		}
	}

	@Override
	public Void visit(VarDecl varDecl) {
		write(varDecl.id);
		write(" : ");
		write(varDecl.type);
		return null;
	}

	@Override
	public Void visit(Equation equation) {
		if (equation.lhs.isEmpty()) {
			write("()");
		} else {
			Iterator<IdExpr> iterator = equation.lhs.iterator();
			while (iterator.hasNext()) {
				write(iterator.next().id);
				if (iterator.hasNext()) {
					write(", ");
				}
			}
		}

		write(" = ");
		expr(equation.expr);
		write(";");
		return null;
	}

	private void assertion(Expr assertion) {
		write("  assert ");
		expr(assertion);
		write(";");
		newline();
	}

	protected void property(String s) {
		write("  --%PROPERTY ");
		write(s);
		write(";");
		newline();
	}

	public void expr(Expr e) {
		e.accept(this);
	}

	@Override
	public Void visit(ArrayAccessExpr e) {
		expr(e.array);
		write("[");
		expr(e.index);
		write("]");
		return null;
	}

	@Override
	public Void visit(ArrayExpr e) {
		Iterator<Expr> iterator = e.elements.iterator();
		write("[");
		expr(iterator.next());
		while (iterator.hasNext()) {
			write(", ");
			expr(iterator.next());
		}
		write("]");
		return null;
	}

	@Override
	public Void visit(ArrayUpdateExpr e) {
		expr(e.array);
		write("[");
		expr(e.index);
		write(" := ");
		expr(e.value);
		write("]");
		return null;
	}

	@Override
	public Void visit(BinaryExpr e) {
		write("(");
		expr(e.left);
		write(" ");
		write(e.op);
		write(" ");
		expr(e.right);
		write(")");
		return null;
	}

	@Override
	public Void visit(BoolExpr e) {
		write(Boolean.toString(e.value));
		return null;
	}

	@Override
	public Void visit(CastExpr e) {
		write(getCastFunction(e.type));
		write("(");
		expr(e.expr);
		write(")");
		return null;
	}

	private String getCastFunction(Type type) {
		if (type == NamedType.REAL) {
			return "real";
		} else if (type == NamedType.INT) {
			return "floor";
		} else {
			throw new IllegalArgumentException("Unable to cast to type: " + type);
		}
	}

	@Override
	public Void visit(CondactExpr e) {
		write("condact(");
		expr(e.clock);
		write(", ");
		expr(e.call);
		for (Expr arg : e.args) {
			write(", ");
			expr(arg);
		}
		write(")");
		return null;
	}

	@Override
	public Void visit(FunctionCallExpr e) {
		write(e.function);
		write("(");
		Iterator<Expr> iterator = e.args.iterator();
		while (iterator.hasNext()) {
			expr(iterator.next());
			if (iterator.hasNext()) {
				write(", ");
			}
		}
		write(")");
		return null;
	}

	@Override
	public Void visit(IdExpr e) {
		write(e.id);
		return null;
	}

	@Override
	public Void visit(IfThenElseExpr e) {
		write("(if ");
		expr(e.cond);
		write(" then ");
		expr(e.thenExpr);
		write(" else ");
		expr(e.elseExpr);
		write(")");
		return null;
	}

	@Override
	public Void visit(IntExpr e) {
		write(e.value);
		return null;
	}

	@Override
	public Void visit(NodeCallExpr e) {
		write(e.node);
		write("(");
		Iterator<Expr> iterator = e.args.iterator();
		while (iterator.hasNext()) {
			expr(iterator.next());
			if (iterator.hasNext()) {
				write(", ");
			}
		}
		write(")");
		return null;
	}

	@Override
	public Void visit(RealExpr e) {
		String str = e.value.toPlainString();
		write(str);
		if (!str.contains(".")) {
			write(".0");
		}
		return null;
	}

	@Override
	public Void visit(RecordAccessExpr e) {
		expr(e.record);
		write(".");
		write(e.field);
		return null;
	}

	@Override
	public Void visit(RecordExpr e) {
		write(e.id);
		write(" {");
		Iterator<Entry<String, Expr>> iterator = e.fields.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Expr> entry = iterator.next();
			write(entry.getKey());
			write(" = ");
			expr(entry.getValue());
			if (iterator.hasNext()) {
				write("; ");
			}
		}
		write("}");
		return null;
	}

	@Override
	public Void visit(RecordUpdateExpr e) {
		expr(e.record);
		write("{");
		write(e.field);
		write(" := ");
		expr(e.value);
		write("}");
		return null;
	}

	@Override
	public Void visit(TupleExpr e) {
		if (e.elements.isEmpty()) {
			write("()");
			return null;
		}

		Iterator<Expr> iterator = e.elements.iterator();
		write("(");
		expr(iterator.next());
		while (iterator.hasNext()) {
			write(", ");
			expr(iterator.next());
		}
		write(")");
		return null;
	}

	@Override
	public Void visit(UnaryExpr e) {
		write("(");
		write(e.op);
		if (e.op != UnaryOp.NEGATIVE) {
			write(" ");
		}
		expr(e.expr);
		write(")");
		return null;
	}

	@Override
	public Void visit(Contract contract) {
		newline();
		write("(*@contract");
		newline();
		for (Expr expr : contract.requires) {
			write("assume ");
			expr(expr);
			write(";");
			newline();
		}
		for (Expr expr : contract.ensures) {
			write("guarantee ");
			expr(expr);
			write(";");
			newline();
		}
		write("*)");
		newline();
		return null;
	}

}