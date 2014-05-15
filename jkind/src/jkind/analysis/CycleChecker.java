package jkind.analysis;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jkind.Output;

public abstract class CycleChecker {
	protected final Map<String, Set<String>> dependencies = new HashMap<>();
	private Deque<String> callStack = new ArrayDeque<>();

	protected boolean checkDependencies() {
		for (String root : dependencies.keySet()) {
			if (!check(root)) {
				return false;
			}
		}

		return true;
	}

	private boolean check(String curr) {
		if (callStack.contains(curr)) {
			callStack.addLast(curr);
			while (!curr.equals(callStack.peekFirst())) {
				callStack.removeFirst();
			}
			Output.error(getError() + ": " + callStack);
			return false;
		}

		callStack.addLast(curr);
		for (String next : dependencies.get(curr)) {
			if (!check(next)) {
				return false;
			}
		}
		callStack.removeLast();

		return true;
	}

	protected abstract String getError();
}
