package jkind.lustre;

public class Location {
	final public int line;
	final public int charPositionInLine;

	public Location(int line, int charPositionInLine) {
		this.line = line;
		this.charPositionInLine = charPositionInLine;
	}
	
	@Override
	public String toString() {
		return line + ":" + charPositionInLine;
	}
	
	final public static Location NULL = new Location(0, 0);
}
