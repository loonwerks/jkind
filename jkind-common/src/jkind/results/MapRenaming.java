package jkind.results;

import java.util.Map;

/**
 * A renaming backed by a map from strings to strings
 */
public class MapRenaming implements Renaming {
	private Map<String, String> map;
	
	public MapRenaming(Map<String, String> map) {
		this.map = map;
	}
	
	@Override
	public String rename(String original) {
		return map.get(original);
	}
}
