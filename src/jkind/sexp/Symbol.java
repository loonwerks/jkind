package jkind.sexp;


public class Symbol extends Sexp {
	final public String sym;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sym == null) ? 0 : sym.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Symbol other = (Symbol) obj;
		if (sym == null) {
			if (other.sym != null)
				return false;
		} else if (!sym.equals(other.sym))
			return false;
		return true;
	}
}
