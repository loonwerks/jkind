package jkind.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CycleFinder {
	public static List<String> findCycle(Map<String, Set<String>> dependencies) {
		return new CycleFinder(dependencies).findCycle();
	}

	private final Deque<String> callStack = new ArrayDeque<>();
	private final Map<String, Set<String>> dependencies;

	private CycleFinder(Map<String, Set<String>> dependencies) {
		this.dependencies = dependencies;
	}

	private List<String> findCycle() {
		for (String root : dependencies.keySet()) {
			List<String> cycle = findCycle(root);
			if (cycle != null) {
				return cycle;
			}
		}

		return null;
	}

	private List<String> findCycle(String curr) {
		if (callStack.contains(curr)) {
			callStack.addLast(curr);
			while (!curr.equals(callStack.peekFirst())) {
				callStack.removeFirst();
			}
			return new ArrayList<>(callStack);
		}

		if (dependencies.containsKey(curr)) {
			callStack.addLast(curr);
			for (String next : dependencies.get(curr)) {
				List<String> cycle = findCycle(next);
				if (cycle != null) {
					return cycle;
				}
			}
			callStack.removeLast();
		}

		return null;
	}
}
