package jkind.fuzz;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Type model used by the random program generator. This mirrors the subset of
 * the JKind type system that the generator targets, and carries both the source
 * syntax used in declarations and enough structure to generate well-typed
 * expressions.
 */
public final class Ty {
	public enum K {
		BOOL, INT, REAL, SUBRANGE, ENUM, RECORD, ARRAY
	}

	public final K kind;
	/** Syntax written in a declaration (a type name for user-defined types). */
	public final String syn;
	public final Ty elem; // ARRAY
	public final int size; // ARRAY
	public final LinkedHashMap<String, Ty> fields; // RECORD
	public final List<String> values; // ENUM
	public final long lo, hi; // SUBRANGE
	/** Structural identity, used to match variables against expected types. */
	public final String key;

	private Ty(K kind, String syn, Ty elem, int size, LinkedHashMap<String, Ty> fields, List<String> values, long lo,
			long hi, String key) {
		this.kind = kind;
		this.syn = syn;
		this.elem = elem;
		this.size = size;
		this.fields = fields;
		this.values = values;
		this.lo = lo;
		this.hi = hi;
		this.key = key;
	}

	public static final Ty BOOL = new Ty(K.BOOL, "bool", null, 0, null, null, 0, 0, "bool");
	public static final Ty INT = new Ty(K.INT, "int", null, 0, null, null, 0, 0, "int");
	public static final Ty REAL = new Ty(K.REAL, "real", null, 0, null, null, 0, 0, "real");

	public static Ty subrange(long lo, long hi) {
		String syn = "subrange [" + lo + ", " + hi + "] of int";
		return new Ty(K.SUBRANGE, syn, null, 0, null, null, lo, hi, "subrange[" + lo + "," + hi + "]");
	}

	public static Ty enumeration(String name, List<String> values) {
		return new Ty(K.ENUM, name, null, 0, null, new ArrayList<>(values), 0, 0, "enum:" + name);
	}

	public static Ty record(String name, LinkedHashMap<String, Ty> fields) {
		return new Ty(K.RECORD, name, null, 0, new LinkedHashMap<>(fields), null, 0, 0, "record:" + name);
	}

	public static Ty array(Ty elem, int size) {
		return new Ty(K.ARRAY, elem.syn + "[" + size + "]", elem, size, null, null, 0, 0,
				"array(" + elem.key + ")[" + size + "]");
	}

	/** A named alias for an existing type, as introduced by a typedef. */
	public Ty aliased(String name) {
		return new Ty(kind, name, elem, size, fields, values, lo, hi, key);
	}

	/** True if this type is int-like, i.e. usable where an int is expected. */
	public boolean isIntLike() {
		return kind == K.INT || kind == K.SUBRANGE;
	}

	public boolean isNumeric() {
		return isIntLike() || kind == K.REAL;
	}

	/**
	 * True if a value of this type may be used where {@code expected} is
	 * required, following JKind's subtyping rules: any subrange is an int, and a
	 * subrange is contained in a wider subrange.
	 */
	public boolean fitsIn(Ty expected) {
		if (key.equals(expected.key)) {
			return true;
		}
		if (expected.kind == K.INT && kind == K.SUBRANGE) {
			return true;
		}
		if (expected.kind == K.SUBRANGE && kind == K.SUBRANGE) {
			return expected.lo <= lo && hi <= expected.hi;
		}
		return false;
	}

	/** Field names and types of a record type, empty for other types. */
	public Map<String, Ty> fieldMap() {
		return fields == null ? new LinkedHashMap<>() : fields;
	}

	@Override
	public String toString() {
		return syn;
	}
}
