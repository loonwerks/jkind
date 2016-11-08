package jkind.api.examples.coverage;

import jkind.lustre.Location;

public class ELocation extends Location {
	public final int start;
	public final int stop;

	public ELocation(int line, int charPositionInLine, int start, int stop) {
		super(line, charPositionInLine);
		this.start = start;
		this.stop = stop;
	}
}
