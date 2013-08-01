package jkind.api;

import java.util.Map;

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
