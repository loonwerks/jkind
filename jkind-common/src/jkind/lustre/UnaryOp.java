package jkind.lustre;

public enum UnaryOp {
	NEGATIVE ("-"),
	NOT ("not"),
	PRE ("pre"), 
	EXP("exp"),
	LOG("log"),
	SQRT("sqrt"),
    SIN("sin"),
    COS("cos"),
    TAN("tan"),
    ARCSIN("arcsin"),
    ARCCOS("arccos"),
    ARCTAN("arctan"),
    SINH("sinh"),
    COSH("cosh"),
    TANH("tanh"),
    MATAN("matan"),	
	;
	
	private String str;
	
	private UnaryOp(String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return str;
	}
	
	public static UnaryOp fromString(String string) {
		for (UnaryOp op : UnaryOp.values()) {
			if (op.toString().equals(string)) {
				return op;
			}
		}
		return null;
	}
}
