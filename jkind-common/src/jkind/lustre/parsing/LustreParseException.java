package jkind.lustre.parsing;

import jkind.JKindException;
import jkind.lustre.Location;

public class LustreParseException extends JKindException {
	private static final long serialVersionUID = 1L;

	private final Location loc;

	public LustreParseException(Location loc, String text) {
		super(text);
		this.loc = loc;
	}
	
	public Location getLocation() {
		return loc;
	}
}
