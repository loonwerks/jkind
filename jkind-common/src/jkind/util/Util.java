package jkind.util;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import org.w3c.dom.Element;

import jkind.JKindException;
import jkind.interval.Interval;
import jkind.lustre.EnumType;
import jkind.lustre.Equation;
import jkind.lustre.Function;
import jkind.lustre.IdExpr;
import jkind.lustre.NamedType;
import jkind.lustre.Node;
import jkind.lustre.NodeCallExpr;
import jkind.lustre.Program;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.TypeDef;
import jkind.lustre.VarDecl;
import jkind.lustre.values.ArrayValue;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.EnumValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;
import jkind.lustre.visitors.AstIterVisitor;

public class Util {
	public static Set<Node> getAllNodeDependencies(Program program) {
		Map<String, Node> nodeTable = getNodeTable(program.nodes);
		Set<Node> result = new HashSet<>();

		Queue<Node> todo = new ArrayDeque<>();
		todo.add(program.getMainNode());

		while (!todo.isEmpty()) {
			Node curr = todo.remove();
			if (result.add(curr)) {
				for (String depName : getNodeDependenciesByName(curr)) {
					todo.add(nodeTable.get(depName));
				}
			}
		}

		return result;
	}

	public static Set<String> getNodeDependenciesByName(Node node) {
		Set<String> referenced = new HashSet<>();
		node.accept(new AstIterVisitor() {
			@Override
			public Void visit(NodeCallExpr e) {
				referenced.add(e.node);
				return super.visit(e);
			}
		});
		return referenced;
	}

	public static List<VarDecl> getVarDecls(Node node) {
		List<VarDecl> decls = new ArrayList<>();
		decls.addAll(node.inputs);
		decls.addAll(node.outputs);
		decls.addAll(node.locals);
		return decls;
	}

	public static List<VarDecl> getVarDecls(Function function) {
		List<VarDecl> decls = new ArrayList<>();
		decls.addAll(function.inputs);
		decls.addAll(function.outputs);
		return decls;
	}

	public static Map<String, Type> getTypeMap(Node node) {
		return getVarDecls(node).stream().collect(toMap(vd -> vd.id, vd -> vd.type));
	}

	public static List<String> getIds(List<VarDecl> decls) {
		return decls.stream().map(decl -> decl.id).collect(toList());
	}

	public static Map<String, Node> getNodeTable(List<Node> nodes) {
		return nodes.stream().collect(toMap(n -> n.id, n -> n));
	}

	public static Map<String, Function> getFunctionTable(List<Function> functions) {
		return functions.stream().collect(toMap(f -> f.id, f -> f));
	}

	/*
	 * Get the name of the type as modeled by the SMT solvers
	 */
	public static String getName(Type type) {
		if (type instanceof NamedType) {
			NamedType namedType = (NamedType) type;
			return namedType.name;
		} else if (type instanceof SubrangeIntType || type instanceof EnumType) {
			return "int";
		} else {
			throw new IllegalArgumentException("Cannot find name for type " + type);
		}
	}

	public static Value parseValue(String type, String value) {
		switch (type) {
		case "bool":
			if (value.equals("0") || value.equals("false")) {
				return BooleanValue.FALSE;
			} else if (value.equals("1") || value.equals("true")) {
				return BooleanValue.TRUE;
			}
			break;

		case "int":
			return new IntegerValue(new BigInteger(value));

		case "real":
			String[] strs = value.split("/");
			if (strs.length <= 2) {
				BigInteger num = new BigInteger(strs[0]);
				BigInteger denom = strs.length > 1 ? new BigInteger(strs[1]) : BigInteger.ONE;
				return new RealValue(new BigFraction(num, denom));
			}
			break;

		default:
			if (value.isEmpty() || Character.isAlphabetic(value.charAt(0))) {
				return new EnumValue(value);
			} else {
				throw new IllegalArgumentException("Invalid enumeration value: " + value);
			}
		}

		throw new JKindException("Unable to parse " + value + " as " + type);
	}

	public static Value parseArrayValue(String type, Element arrayElement) {
		int size = Integer.parseInt(arrayElement.getAttribute("size"));
		List<Value> elements = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Value elValue;
			Element arrayEl = getElement(arrayElement, "Array", i);
			if (arrayEl != null) {
				elValue = parseArrayValue(type, arrayEl);
			} else {
				arrayEl = getElement(arrayElement, "Item", i);
				int index = Integer.parseInt(arrayEl.getAttribute("index"));
				if (index != i) {
					throw new IllegalArgumentException("We expect array indicies to be sorted");
				}
				elValue = parseValue(type, arrayEl.getTextContent());
			}
			elements.add(elValue);
		}
		return new ArrayValue(elements);
	}

	public static Value promoteIfNeeded(Value value, Type type) {
		if (value instanceof IntegerValue && type == NamedType.REAL) {
			IntegerValue iv = (IntegerValue) value;
			return new RealValue(new BigFraction(iv.value));
		}
		return value;
	}

	private static Element getElement(Element element, String name, int index) {
		return (Element) element.getElementsByTagName(name).item(index);
	}

	public static Value parseValue(Type type, String value) {
		return parseValue(getName(type), value);
	}

	public static Type resolveType(Type type, Map<String, Type> map) {
		return TypeResolver.resolve(type, map);
	}

	public static Map<String, Type> createResolvedTypeTable(List<TypeDef> defs) {
		Map<String, Type> resolved = new HashMap<>();
		Map<String, Type> unresolved = createUnresolvedTypeTable(defs);
		for (TypeDef def : defs) {
			resolved.put(def.id, resolveType(def.type, unresolved));
		}
		return resolved;
	}

	private static Map<String, Type> createUnresolvedTypeTable(List<TypeDef> defs) {
		Map<String, Type> unresolved = new HashMap<>();
		for (TypeDef def : defs) {
			unresolved.put(def.id, def.type);
		}
		return unresolved;
	}

	public static Map<String, Set<String>> getDirectDependencies(Node node) {
		Map<String, Set<String>> directDepends = new HashMap<>();
		for (Equation eq : node.equations) {
			Set<String> set = CurrIdExtractorVisitor.getCurrIds(eq.expr);
			for (IdExpr idExpr : eq.lhs) {
				directDepends.put(idExpr.id, set);
			}
		}
		return directDepends;
	}

	public static void writeToFile(String content, File file) throws IOException {
		try (Writer writer = new BufferedWriter(new FileWriter(file))) {
			writer.append(content);
		}
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").startsWith("Windows");
	}

	public static boolean isArbitrary(Value value) {
		if (value == null) {
			return true;
		} else if (value instanceof Interval) {
			return ((Interval) value).isArbitrary();
		}
		return false;
	}

	public static <T, S> List<S> castList(List<T> list, Class<S> klass) {
		List<S> result = new ArrayList<>();
		for (T e : list) {
			result.add(klass.cast(e));
		}
		return result;
	}

	/**
	 * In SMT solvers, integer division behaves differently than in Java. In
	 * particular, for -5 div 3 java says '-1' and SMT solvers say '-2'
	 */
	public static BigInteger smtDivide(BigInteger a, BigInteger b) {
		return a.subtract(a.mod(b)).divide(b);
	}

	public static <T> List<T> safeList(Collection<? extends T> original) {
		if (original == null) {
			return Collections.emptyList();
		} else {
			return Collections.unmodifiableList(new ArrayList<>(original));
		}
	}

	public static <T> List<T> safeNullableList(List<? extends T> original) {
		if (original == null) {
			return null;
		} else {
			return Collections.unmodifiableList(new ArrayList<>(original));
		}
	}

	public static <T> Set<T> safeSet(Set<? extends T> original) {
		if (original == null) {
			return Collections.emptySet();
		} else {
			return Collections.unmodifiableSet(new HashSet<>(original));
		}
	}

	public static <T> SortedMap<String, T> safeStringSortedMap(Map<String, T> original) {
		TreeMap<String, T> map = new TreeMap<>(new StringNaturalOrdering());
		if (original != null) {
			map.putAll(original);
		}
		return Collections.unmodifiableSortedMap(map);
	}

	public static <T> List<T> copyNullable(List<? extends T> original) {
		if (original == null) {
			return null;
		}
		return new ArrayList<>(original);
	}

	public static Set<String> safeStringSortedSet(Collection<String> original) {
		TreeSet<String> set = new TreeSet<>(new StringNaturalOrdering());
		set.addAll(original);
		return Collections.unmodifiableSet(set);
	}

	public static <T> Set<T> setDifference(Collection<T> c1, Collection<T> c2) {
		Set<T> result = new HashSet<>(c1);
		result.removeAll(c2);
		return result;
	}

	public static List<EnumType> getEnumTypes(List<TypeDef> types) {
		List<EnumType> enums = new ArrayList<>();
		for (TypeDef def : types) {
			if (def.type instanceof EnumType) {
				enums.add((EnumType) def.type);
			}
		}
		return enums;
	}

	public static Value getDefaultValue(Type type) {
		return type.accept(new DefaultValueVisitor());
	}

	public static Value cast(Type type, Value value) {
		if (type == NamedType.REAL && value instanceof IntegerValue) {
			IntegerValue iv = (IntegerValue) value;
			return new RealValue(new BigFraction(iv.value));
		} else if (type == NamedType.INT && value instanceof RealValue) {
			RealValue rv = (RealValue) value;
			return new IntegerValue(rv.value.floor());
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static String removeTrailingZeros(String str) {
		if (!str.contains(".")) {
			return str;
		}

		return str.replaceFirst("\\.?0*$", "");
	}

	public static String spaces(int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(' ');
		}
		return sb.toString();
	}

	public static String secondsToTime(double seconds) {
		String result;

		int minutes = (int) (seconds / 60);
		seconds = seconds % 60;
		result = new DecimalFormat("#.###").format(seconds) + "s";
		if (minutes == 0) {
			return result;
		}

		int hours = minutes / 60;
		minutes = minutes % 60;
		result = minutes + "m " + result;
		if (hours == 0) {
			return result;
		}

		int days = hours / 24;
		hours = hours % 24;
		result = hours + "h " + result;
		if (days == 0) {
			return result;
		}

		return days + "d " + result;
	}

	/** Default name for realizability query property in XML file */
	public static final String REALIZABLE = "%REALIZABLE";

	/**
	 * ASCII "End of Text" character, used by JKindApi to ask JKind to terminate
	 */
	public static final int END_OF_TEXT = 0x03;

	public static String capitalize(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}
}
