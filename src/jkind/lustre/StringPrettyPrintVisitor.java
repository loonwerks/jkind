package jkind.lustre;


public class StringPrettyPrintVisitor extends PrettyPrintVisitor {
	private StringBuilder sb = new StringBuilder();
	
	@Override
	public String toString() {
		return sb.toString();
	}
	
	protected void write(Object o) {
		sb.append(o);
	}
}