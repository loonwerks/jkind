package jkind.lustre;

public enum BinaryOp {
	PLUS ("+"),
	MINUS ("-"),
	MULTIPLY ("*"),
	DIVIDE ("/"),
	INT_DIVIDE ("div"),
	EQUAL ("="),
	NOTEQUAL ("<>"),
	GREATER (">"),
	LESS ("<"),
	GREATEREQUAL (">="),
	LESSEQUAL ("<="),
	OR ("or"),
	AND ("and"),
	XOR ("xor"),
	IMPLIES ("=>"),
	ARROW ("->");
	
	private String str;
	
	private BinaryOp(String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return str;
	}
}
