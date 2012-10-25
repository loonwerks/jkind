package jkind.sexp;


public class Symbol extends Sexp {
	private String sym;

	public Symbol(String sym) {
		this.sym = sym;
	}

	@Override
	public String toString() {
		return sym;
	}

	@Override
	protected void toBuilder(StringBuilder sb) {
		sb.append(sym);
	}
}
