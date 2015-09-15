package jkind.lustre;

public enum QuantOp {
	FORALL("forall"),
	EXISTS("exists");
	
private String str;
	
	private QuantOp(String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return str;
	}
	
	public static QuantOp fromString(String string) {
		for (QuantOp op : QuantOp.values()) {
			if (op.toString().equals(string)) {
				return op;
			}
		}
		throw new IllegalArgumentException("Unknown quantifier: " + string);
	}
}
