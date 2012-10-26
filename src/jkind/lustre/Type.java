package jkind.lustre;

public enum Type {
	REAL ("real"), 
	BOOL ("bool"), 
	INT ("int");

	private String str;

	private Type(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}
}
