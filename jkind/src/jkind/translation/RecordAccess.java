package jkind.translation;

public class RecordAccess implements Access {
	public final String field;

	public RecordAccess(String field) {
		this.field = field;
	}
	
	@Override
	public String toString() {
		return "." + field;
	}
}
