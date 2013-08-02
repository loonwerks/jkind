package jkind.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.JKindException;
import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.lustre.VarDecl;
import jkind.lustre.values.BooleanValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.RealValue;
import jkind.lustre.values.Value;

public class Util {
	public static List<VarDecl> getVarDecls(Node node) {
		List<VarDecl> decls = new ArrayList<>();
		decls.addAll(node.inputs);
		decls.addAll(node.outputs);
		decls.addAll(node.locals);
		return decls;
	}

	public static Map<String, Type> getTypeMap(Node node) {
		Map<String, Type> map = new HashMap<>();
		for (VarDecl v : getVarDecls(node)) {
			map.put(v.id, v.type);
		}
		return map;
	}

	public static List<String> getIds(List<VarDecl> decls) {
		List<String> ids = new ArrayList<>();
		for (VarDecl decl : decls) {
			ids.add(decl.id);
		}
		return ids;
	}

	public static Map<String, Node> getNodeTable(List<Node> nodes) {
		Map<String, Node> nodeTable = new HashMap<>();
		for (Node node : nodes) {
			nodeTable.put(node.id, node);
		}
		return nodeTable;
	}
	
	public static Value parseValue(String type, String value) {
		if (type.equals("bool")) {
			if (value.equals("0") || value.equals("false")) {
				return BooleanValue.FALSE;
			} else if (value.equals("1") || value.equals("true")) {
				return BooleanValue.TRUE;
			}
		} else if (type.equals("int")) {
			return new IntegerValue(new BigInteger(value));
		} else if (type.equals("real")) {
			String[] strs = value.split("/");
			if (strs.length <= 2) {
				BigInteger num = new BigInteger(strs[0]);
				BigInteger denom = strs.length > 1 ? new BigInteger(strs[1]) : BigInteger.ONE;
				return new RealValue(num, denom);
			}
		}

		throw new JKindException("Unable to parse " + value + " as " + type);
	}
}
