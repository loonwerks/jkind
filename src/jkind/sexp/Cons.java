package jkind.sexp;

import java.util.Arrays;
import java.util.List;

public class Cons extends Sexp {
	private String head;
	private List<Sexp> args;
	
	public Cons(String head, List<Sexp> args) {
		this.head = head;
		this.args = args;
	}
	
	public Cons(String head, Sexp... args) {
		this(head, Arrays.asList(args));
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toBuilder(sb);
		return sb.toString();
	}

	@Override
	protected void toBuilder(StringBuilder sb) {
		sb.append("(");
		sb.append(head);
		for (Sexp arg : args) {
			sb.append(" ");
			arg.toBuilder(sb);
		}
		sb.append(")");
	}
}
