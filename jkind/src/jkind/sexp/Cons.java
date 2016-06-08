package jkind.sexp;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Cons extends Sexp {
	public final Sexp head;
	public final List<? extends Sexp> args;

	public Cons(Sexp head, List<? extends Sexp> args) {
		this.head = head;
		this.args = args;
	}

	public Cons(Sexp head, Sexp... args) {
		this(head, Arrays.asList(args));
	}

	public Cons(String head, List<? extends Sexp> args) {
		this.head = new Symbol(head);
		this.args = args;
	}

	public Cons(String head, Sexp... args) {
		this(head, Arrays.asList(args));
	}

	public Cons(List<Sexp> sexps) {
		this(sexps.get(0), sexps.subList(1, sexps.size()));
	}

	public Cons(String head, String arg1, String... args) {
		this.head = new Symbol(head);
		Stream<String> argStream = Stream.concat(Stream.of(arg1), Arrays.stream(args));
		this.args = argStream.map(Symbol::new).collect(toList());
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
		head.toBuilder(sb);
		for (Sexp arg : args) {
			sb.append(" ");
			arg.toBuilder(sb);
		}
		sb.append(")");
	}
}
