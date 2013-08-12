package jkind.api.results;

import java.util.Map;

/**
 * A renaming backed by a map from strings to strings
 */
public class MapRenaming extends Renaming {
	private final Map<String, String> map;
	private final Mode mode;

	public static enum Mode {
		NULL, IDENTITY
	}

	public MapRenaming(Map<String, String> map, Mode mode) {
		super();
		this.map = map;
		this.mode = mode;
	}

	@Override
	public String rename(String original) {
		String renamed = map.get(original);
		if (renamed == null && mode == Mode.IDENTITY) {
			return original;
		} else {
			return renamed;
		}
	}
}
