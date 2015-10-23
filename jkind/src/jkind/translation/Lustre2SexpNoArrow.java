package jkind.translation;

import jkind.lustre.BinaryExpr;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;

public class Lustre2SexpNoArrow extends Lustre2Sexp {

	public Lustre2SexpNoArrow(int index) {
		super(index);
	}

	
	@Override
	public Sexp visit(BinaryExpr e) {
		Sexp left = e.left.accept(this);
		Sexp right = e.right.accept(this);

		switch (e.op) {
		case NOTEQUAL:
		case XOR:
			return new Cons("not", new Cons("=", left, right));

		case ARROW:
			if (pre) {
				throw new IllegalArgumentException(
						"Arrows cannot be nested under pre during translation to sexp");
			}
			if(index == 0){
				return left;
			}
			return right;

		default:
			return new Cons(e.op.toString(), left, right);
		}
	}
	
}
