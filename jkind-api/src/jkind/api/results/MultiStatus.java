package jkind.api.results;

import java.util.EnumMap;
import java.util.StringJoiner;

public class MultiStatus {
	final private EnumMap<Status, Integer> map = new EnumMap<>(Status.class);

	public int getCount(Status status) {
		if (map.containsKey(status)) {
			return map.get(status);
		} else {
			return 0;
		}
	}

	public void add(Status status) {
		if (status != null) {
			map.put(status, getCount(status) + 1);
		}
	}

	public void add(MultiStatus other) {
		if (other != null) {
			for (Status key : other.map.keySet()) {
				map.put(key, getCount(key) + other.map.get(key));
			}
		}
	}

	public void remove(Status status) {
		if (status != null) {
			map.put(status, getCount(status) - 1);
		}
	}

	public void remove(MultiStatus other) {
		if (other != null) {
			for (Status key : other.map.keySet()) {
				map.put(key, getCount(key) - other.map.get(key));
			}
		}
	}

	private static final Status[] PRECEDENCE = new Status[] { Status.WORKING, Status.WAITING,
			Status.ERROR, Status.INVALID, Status.INCONSISTENT, Status.UNKNOWN, Status.CANCELED,
			Status.VALID };

	public Status getOverallStatus() {
		for (Status status : PRECEDENCE) {
			if (getCount(status) > 0) {
				return status;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringJoiner text = new StringJoiner(", ");
		for (Status status : PRECEDENCE) {
			int count = getCount(status);
			if (count > 0) {
				text.add(count + " " + status);
			}
		}

		return text.toString();
	}
}
