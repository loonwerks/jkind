package jkind.lustre;

public enum UnaryOp {
	NEGATIVE ("-"),
	NOT ("not"),
	PRE ("pre");
	
	private String str;
	
	private UnaryOp(String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return str;
	}
}
