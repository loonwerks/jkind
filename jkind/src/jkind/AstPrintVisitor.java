package jkind;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.ArrayExpr;
import jkind.lustre.ArrayType;
import jkind.lustre.ArrayUpdateExpr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.BoolExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.CondactExpr;
import jkind.lustre.Constant;
import jkind.lustre.EnumType;
import jkind.lustre.Equation;
import jkind.lustre.Expr;
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
import jkind.lustre.SubrangeIntType;
import jkind.lustre.TupleExpr;
import jkind.lustre.TupleType;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.UnaryExpr;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstVisitor;
import jkind.lustre.visitors.TypeVisitor;

public class AstPrintVisitor implements AstVisitor<Void, Void>, TypeVisitor<Void> {
	private StringBuilder sb = new StringBuilder();

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
		write("new TypeDef(");
		string(typeDef.id);
		write(", ");
		type(typeDef.type);
		write(")");
		return null;
	}

	@Override
	public Void visit(Constant constant) {
		write("new Constant(");
		string(constant.id);
		write(", ");
		if (constant.type == null) {
			write("null");
		} else {
			type(constant.type);
		}
		write(", ");
		expr(constant.expr);
		write(")");
		return null;
	}

	@Override
	public Void visit(Node node) {
		write("new Node(");
		string(node.id);
		write(", ");
		varDecls(node.inputs);
		write(", ");
		varDecls(node.outputs);
		write(", ");
		varDecls(node.locals);
		write(", ");
		equations(node.equations);
		write(", ");
		properties(node.properties);
		write(", ");
		assertions(node.assertions);
		write(")");
		return null;
	}

	private void varDecls(List<VarDecl> varDecls) {
		write("new ArrayList<VarDecl>(){{ ");
		for (VarDecl varDecl : varDecls) {
			write("add(");
			visit(varDecl);
			write("); ");
		}
		write("}}");
	}

	@Override
	public Void visit(VarDecl varDecl) {
		write("new VarDecl(");
		string(varDecl.id);
		write(", ");
		type(varDecl.type);
		write(")");
		return null;
	}

	private void equations(List<Equation> equations) {
		write("new ArrayList<Equation>(){{ ");
		for (Equation equation : equations) {
			write("  add(");
			visit(equation);
			write("); ");
		}
		write("}}");
	}

	private void assertions(List<Expr> assertions) {
		write("new ArrayList<Expr>(){{ ");
		for (Expr assertion : assertions) {
			write("add(");
			expr(assertion);
			write("); ");
		}
		write("}}");
	}

	private void properties(List<String> properties) {
		write("new ArrayList<String>(){{ ");
		for (String property : properties) {
			write("add(");
			string(property);
			write("); ");
		}
		write("}}");
	}

	@Override
	public Void visit(Equation equation) {
		write("new Equation(");
		if (equation.lhs.size() == 1) {
			expr(equation.lhs.get(0));
		} else {
			write("new ArrayList<IdExpr>(){{ ");
			for (IdExpr idExpr : equation.lhs) {
				write("add(");
				expr(idExpr);
				write("); ");
			}
			write("}}");
		}
		write(", ");
		expr(equation.expr);
		write(")");
		return null;
	}

	public void expr(Expr e) {
		e.accept(this);
	}

	@Override
	public Void visit(ArrayAccessExpr e) {
		write("new ArrayAccessExpr(");
		expr(e.array);
		write(", ");
		expr(e.index);
		write(")");
		return null;
	}

	@Override
	public Void visit(ArrayExpr e) {
		write("new ArrayExpr(");
		write("new ArrayList<Expr>(){{ ");
		for (Expr element : e.elements) {
			write("add(");
			expr(element);
			write("); ");
		}
		write("}}");
		write(")");
		return null;
	}

	@Override
	public Void visit(ArrayUpdateExpr e) {
		write("new ArrayUpdateExpr(");
		expr(e.array);
		write(", ");
		expr(e.index);
		write(", ");
		expr(e.value);
		write(")");
		return null;
	}

	@Override
	public Void visit(BinaryExpr e) {
		write("new BinaryExpr(");
		expr(e.left);
		write(", ");
		write("BinaryOp.");
		write(e.op.name());
		write(", ");
		expr(e.right);
		write(")");
		return null;
	}

	@Override
	public Void visit(BoolExpr e) {
		write("new BoolExpr(");
		write(Boolean.toString(e.value));
		write(")");
		return null;
	}

	@Override
	public Void visit(CastExpr e) {
		write("new CastExpr(");
		type(e.type);
		write(", ");
		expr(e.expr);
		write(")");
		return null;
	}

	@Override
	public Void visit(CondactExpr e) {
		write("new CondactExpr(");
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
	public Void visit(IdExpr e) {
		write("new IdExpr(");
		string(e.id);
		write(")");
		return null;
	}

	@Override
	public Void visit(IfThenElseExpr e) {
		write("new IfThenElseExpr(");
		expr(e.cond);
		write(", ");
		expr(e.thenExpr);
		write(", ");
		expr(e.elseExpr);
		write(")");
		return null;
	}

	@Override
	public Void visit(IntExpr e) {
		write("new IntExpr(");
		bigInteger(e.value);
		write(")");
		return null;
	}

	@Override
	public Void visit(NodeCallExpr e) {
		write("new NodeCallExpr(");
		string(e.node);
		for (Expr arg : e.args) {
			write(", ");
			expr(arg);
		}
		write(")");
		return null;
	}

	@Override
	public Void visit(RealExpr e) {
		write("new RealExpr(");
		write("new BigDecimal(");
		string(e.toString());
		write("))");
		return null;
	}

	@Override
	public Void visit(RecordAccessExpr e) {
		write("new RecordAccessExpr(");
		expr(e.record);
		write(", ");
		string(e.field);
		write(")");
		return null;
	}

	@Override
	public Void visit(RecordExpr e) {
		write("new RecordExpr(");
		string(e.id);
		write(", ");
		write("new HashMap<String, Expr>() {{ ");
		for (Entry<String, Expr> entry : e.fields.entrySet()) {
			write("put(");
			string(entry.getKey());
			write(", ");
			expr(entry.getValue());
			write("); ");
		}
		write("}}");
		write(")");

		return null;
	}

	@Override
	public Void visit(RecordUpdateExpr e) {
		write("new RecordUpdateExpr(");
		write(e.record);
		write(", ");
		string(e.field);
		write(", ");
		write(e.value);
		write(")");
		return null;
	}

	@Override
	public Void visit(TupleExpr e) {
		write("new TupleExpr(");
		visitExprs(e.elements);
		write(")");
		return null;
	}

	@Override
	public Void visit(UnaryExpr e) {
		write("new UnaryExpr(");
		write("UnaryOp.");
		write(e.op.name());
		write(", ");
		expr(e.expr);
		write(")");
		return null;
	}

	private void visitExprs(List<Expr> exprs) {
		boolean first = true;
		for (Expr expr : exprs) {
			if (first) {
				first = false;
			} else {
				write(", ");
			}
			expr(expr);
		}
	}

	private void string(String string) {
		write("\"");
		write(string);
		write("\"");
	}

	private void type(Type type) {
		type.accept(this);
	}

	@Override
	public Void visit(ArrayType e) {
		write("new ArrayType(");
		type(e.base);
		write(", ");
		write(e.size);
		write(")");
		return null;
	}

	@Override
	public Void visit(EnumType e) {
		write("new EnumType(");
		string(e.id);
		write(", ");
		write("new ArrayList<String>(){{ ");
		for (String value : e.values) {
			write("add(");
			string(value);
			write("); ");
		}
		write("}}");
		write(")");
		return null;
	}

	@Override
	public Void visit(NamedType e) {
		if (e == NamedType.BOOL) {
			write("NamedType.BOOL");
		} else if (e == NamedType.INT) {
			write("NamedType.INT");
		} else if (e == NamedType.REAL) {
			write("NamedType.REAL");
		} else {
			write("new NamedType(");
			string(e.name);
			write(")");
		}
		return null;
	}

	@Override
	public Void visit(RecordType e) {
		write("new RecordType(");
		string(e.id);
		write(", ");
		write("new HashMap<String, Type>() {{ ");
		for (Entry<String, Type> entry : e.fields.entrySet()) {
			write("put(");
			string(entry.getKey());
			write(", ");
			type(entry.getValue());
			write("); ");
		}
		write("}}");
		write(")");
		return null;
	}

	@Override
	public Void visit(TupleType e) {
		throw new IllegalArgumentException();
	}

	@Override
	public Void visit(SubrangeIntType e) {
		write("new SubrangeIntType(");
		bigInteger(e.low);
		write(", ");
		bigInteger(e.high);
		write(")");
		return null;
	}

	private void bigInteger(BigInteger i) {
		write("new BigInteger(");
		string(i.toString());
		write(")");
	}
}