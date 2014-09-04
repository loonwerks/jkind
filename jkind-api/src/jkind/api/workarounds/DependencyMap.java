package jkind.api.workarounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DependencyMap<T> {
	private Map<String, T> valueMap = new LinkedHashMap<>();
	private Map<String, Set<String>> dependencyMap = new HashMap<>();

	public void put(String id, T value, Set<String> dependencies) {
		valueMap.put(id, value);
		dependencyMap.put(id, dependencies);
	}
	
	public List<T> getSortedValues() {
		List<T> result = new ArrayList<>();
		for (String id : getSortedIds()) {
			result.add(valueMap.get(id));
		}
		return result;
	}

	private List<String> getSortedIds() {
		List<String> ordered = new ArrayList<>();
		for (String id : valueMap.keySet()) {
			addToSortedIdsList(id, ordered);
		}
		return ordered;
	}

	private void addToSortedIdsList(String id, List<String> sorted) {
		if (sorted.contains(id)) {
			return;
		}
		
		Set<String> dependencies = new HashSet<>(dependencyMap.get(id));
		dependencies.removeAll(sorted);
		for (String dependency : dependencies) {
			addToSortedIdsList(dependency, sorted);
		}
		
		sorted.add(id);
	}
}
