package jkind.lustre;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class PrettyPrintVisitor implements AstVisitor<Void> {
	private StringBuilder sb = new StringBuilder();
	private String main;

	@Override
	public String toString() {
		return sb.toString();
	}

	protected void write(Object o) {
		sb.append(o);
	}

	private final static String seperator = System.getProperty("line.separator");

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
		if (!node.locals.isEmpty()) {
			write("var");
			newline();
			varDecls(node.locals);
			write(";");
			newline();
		}
		write("let");

		if (node.id.equals(main)) {
			newline();
			write("  --%MAIN");
			newline();
		}

		for (Equation equation : node.equations) {
			newline();
			write("  ");
			equation.accept(this);
			newline();
		}

		for (Expr assertion : node.assertions) {
			newline();
			assertion(assertion);
		}

		if (!node.properties.isEmpty()) {
			newline();
			for (String property : node.properties) {
				property(property);
			}
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
		Iterator<IdExpr> iterator = equation.lhs.iterator();
		while (iterator.hasNext()) {
			write(iterator.next().id);
			if (iterator.hasNext()) {
				write(", ");
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

	private void property(String s) {
		write("  --%PROPERTY ");
		write(s);
		write(";");
		newline();
	}

	public void expr(Expr e) {
		e.accept(this);
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
	public Void visit(ProjectionExpr e) {
		expr(e.expr);
		write(".");
		write(e.field);
		return null;
	}

	@Override
	public Void visit(RealExpr e) {
		write(e.value.toPlainString());
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
	public Void visit(UnaryExpr e) {
		write(e.op);
		if (e.op != UnaryOp.NEGATIVE) {
			write(" ");
		}
		expr(e.expr);
		return null;
	}
}