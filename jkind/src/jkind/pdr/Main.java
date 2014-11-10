package jkind.pdr;

import jkind.lustre.BinaryExpr;
import jkind.lustre.BinaryOp;
import jkind.lustre.Equation;
import jkind.lustre.IdExpr;
import jkind.lustre.IntExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.UnaryExpr;
import jkind.lustre.UnaryOp;
import jkind.lustre.VarDecl;
import jkind.lustre.builders.NodeBuilder;

public class Main {
	public static void main(String[] args) {
		NodeBuilder builder = new NodeBuilder("main");
		builder.addOutput(new VarDecl("x", NamedType.INT));
		builder.addOutput(new VarDecl("ok", NamedType.BOOL));
		builder.addEquation(new Equation(new IdExpr("x"), new BinaryExpr(new IntExpr(0),
				BinaryOp.ARROW, new BinaryExpr(new UnaryExpr(UnaryOp.PRE, new IdExpr("x")),
						BinaryOp.PLUS, new IntExpr(1)))));
		builder.addEquation(new Equation(new IdExpr("ok"), new BinaryExpr(new IdExpr("x"), BinaryOp.NOTEQUAL, new IntExpr(-1))));
		builder.addProperty("ok");
		Node node = builder.build();

		Pdr pdr = new Pdr(node);
		showCex(pdr.check());
	}

	private static void showCex(Cube c) {
		while (c != null) {
			System.out.println(c);
			c = c.getNext();
		}
		System.out.println("Done");
	}
}
