package jkind.slicing;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DependencySet implements Iterable<Dependency> {
	private final Set<Dependency> set = new HashSet<>();

	public boolean add(Dependency dep) {
		return set.add(dep);
	}

	public boolean addAll(DependencySet other) {
		return set.addAll(other.set);
	}

	public boolean contains(Dependency dep) {
		return set.contains(dep);
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public Set<Dependency> getSet() {
		return set;
	}

	@Override
	public Iterator<Dependency> iterator() {
		return set.iterator();
	}

	public Dependency first() {
		return iterator().next();
	}
}
