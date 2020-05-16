package jkind.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A bi-directional map that preserves the order of keys and values
 */
public class LinkedBiMap<K, V> implements Map<K, V> {
	private Map<K, V> map;
	private Map<V, K> inverse;

	public LinkedBiMap() {
		this.map = new LinkedHashMap<>();
		this.inverse = new LinkedHashMap<>();
	}

	private LinkedBiMap(Map<K, V> map, Map<V, K> inverse) {
		this.map = map;
		this.inverse = inverse;
	}

	public LinkedBiMap<V, K> inverse() {
		return new LinkedBiMap<>(inverse, map);
	}

	@Override
	public void clear() {
		map.clear();
		inverse.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return Collections.unmodifiableSet(map.entrySet());
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	public K getKey(V value) {
		return inverse.get(value);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return Collections.unmodifiableSet(map.keySet());
	}

	@Override
	public V put(K key, V value) {
		if (inverse.containsKey(value)) {
			throw new IllegalArgumentException("BiMap already has a mapping to value: " + value);
		}

		if (map.containsKey(key)) {
			inverse.remove(map.get(key));
		}

		map.put(key, value);
		inverse.put(value, key);
		return value;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> other) {
		for (Entry<? extends K, ? extends V> e : other.entrySet()) {
			put(e.getKey(), e.getValue());
		}
	}

	@Override
	public V remove(Object key) {
		if (map.containsKey(key)) {
			inverse.remove(map.get(key));
			return map.remove(key);
		} else {
			return null;
		}
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		return Collections.unmodifiableCollection(map.values());
	}

	public List<K> keyList() {
		return Util.safeList(map.keySet());
	}

	public List<V> valueList() {
		return Util.safeList(map.values());
	}
}
