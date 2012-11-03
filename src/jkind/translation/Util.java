package jkind.translation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.lustre.Node;
import jkind.lustre.SubrangeIntType;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.sexp.Cons;
import jkind.sexp.Sexp;

public class Util {
	public static List<VarDecl> getVarDecls(Node node) {
		List<VarDecl> decls = new ArrayList<VarDecl>();
		decls.addAll(node.inputs);
		decls.addAll(node.outputs);
		decls.addAll(node.locals);
		return decls;
	}

	public static Map<String, Type> getTypeMap(Node node) {
		Map<String, Type> map = new HashMap<String, Type>();
		for (VarDecl v : getVarDecls(node)) {
			map.put(v.id, v.type);
		}
		return map;
	}

	public static List<String> getIds(List<VarDecl> decls) {
		List<String> ids = new ArrayList<String>();
		for (VarDecl decl : decls) {
			ids.add(decl.id);
		}
		return ids;
	}

	public static Map<String, Node> getNodeTable(List<Node> nodes) {
		Map<String, Node> nodeTable = new HashMap<String, Node>();
		for (Node node : nodes) {
			nodeTable.put(node.id, node);
		}
		return nodeTable;
	}

	public static Sexp subrangeConstraint(String id, Sexp index, SubrangeIntType subrange) {
		Sexp var = new Cons("$" + id, index);
		Sexp low = new Cons("<=", Sexp.fromInt(subrange.low), var);
		Sexp high = new Cons("<=", var, Sexp.fromInt(subrange.high));
		return new Cons("and", low, high);
	}
}
