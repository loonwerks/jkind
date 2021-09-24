package jkind.api.ui.counterexample;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import jkind.lustre.values.Value;
import jkind.results.Signal;

public class SignalGrouper {
	public static Collection<SignalGroup> group(SignalGroup parent, Collection<Signal<Value>> signals) {
		Map<String, SignalGroup> groups = new LinkedHashMap<>();

		for (Signal<Value> signal : signals) {
			String[] split = splitName(signal.getName());
			String groupName = split[0];
			String subName = split[1];

			SignalGroup group = groups.get(groupName);
			if (group == null) {
				group = new SignalGroup(parent, groupName);
				groups.put(groupName, group);
			}
			group.addSignal(signal.rename(subName));
		}

		return groups.values();
	}

	private static String[] splitName(String name) {
		int dotIndex = name.indexOf(".");
		int arrayIndex = name.indexOf("[", 1);

		if (0 <= dotIndex && (dotIndex < arrayIndex || arrayIndex == -1)) {
			return new String[] { name.substring(0, dotIndex), name.substring(dotIndex + 1, name.length()) };
		}

		if (0 <= arrayIndex && (arrayIndex < dotIndex || dotIndex == -1)) {
			return new String[] { name.substring(0, arrayIndex), name.substring(arrayIndex, name.length()) };
		}

		return new String[] { name, "" };
	}
}
