package jkind.lustre;

public enum BinaryOp {
	PLUS("+"), MINUS("-"), MULTIPLY("*"), DIVIDE("/"), INT_DIVIDE("div"), MODULUS("mod"), EQUAL("="), NOTEQUAL(
			"<>"), GREATER(">"), LESS("<"), GREATEREQUAL(
					">="), LESSEQUAL("<="), OR("or"), AND("and"), XOR("xor"), IMPLIES("=>"), ARROW("->");

	private String str;

	private BinaryOp(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}

	public static BinaryOp fromString(String string) {
		for (BinaryOp op : BinaryOp.values()) {
			if (op.toString().equals(string)) {
				return op;
			}
		}
		throw new IllegalArgumentException("Unknown binary operator: " + string);
	}
}
