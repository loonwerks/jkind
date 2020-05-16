package jkind.lustre;

public class Location {
	public final int line;
	public final int charPositionInLine;

	public Location(int line, int charPositionInLine) {
		this.line = line;
		this.charPositionInLine = charPositionInLine;
	}

	@Override
	public String toString() {
		return line + ":" + charPositionInLine;
	}

	public static final Location NULL = new Location(0, 0);
}
