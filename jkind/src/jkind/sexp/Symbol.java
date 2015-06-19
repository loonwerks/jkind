package jkind.sexp;


public class Symbol extends Sexp {
	public final String str;

	public Symbol(String sym) {
		this.str = sym;
	}

	@Override
	public String toString() {
		return str;
	}

	@Override
	protected void toBuilder(StringBuilder sb) {
		sb.append(str);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((str == null) ? 0 : str.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Symbol)) {
			return false;
		}
		Symbol other = (Symbol) obj;
		if (str == null) {
			if (other.str != null) {
				return false;
			}
		} else if (!str.equals(other.str)) {
			return false;
		}
		return true;
	}
}
