package jkind.translation;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jkind.lustre.Expr;
import jkind.lustre.IdExpr;

public class ResolvingSubstitutionMap implements Map<String, Expr> {
	private final Map<String, Expr> map = new HashMap<>();
	private final Deque<String> stack = new ArrayDeque<>();

	private Expr resolve(Expr e) {
		if (e instanceof IdExpr) {
			IdExpr eid = (IdExpr) e;
			return resolve(eid.id);
		}
		return e;
	}

	private Expr resolve(String id) {
		if (stack.contains(id)) {
			return null;
		}

		stack.push(id);
		Expr e = map.get(id);
		Expr eResolved = resolve(e);
		if (eResolved == null) {
			eResolved = e;
		} else if (e != eResolved) {
			map.put(id, eResolved);
		}
		stack.pop();

		return eResolved;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
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
	public Expr get(Object key) {
		if (key instanceof String) {
			String id = (String) key;
			return resolve(id);
		}
		return null;
	}

	@Override
	public Expr put(String key, Expr value) {
		return map.put(key, value);
	}

	@Override
	public Expr remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Expr> m) {
		map.putAll(m);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<Expr> values() {
		return map.values();
	}

	@Override
	public Set<Map.Entry<String, Expr>> entrySet() {
		return map.entrySet();
	}
}
