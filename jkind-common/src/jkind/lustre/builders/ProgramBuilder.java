package jkind.lustre.builders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jkind.lustre.Constant;
import jkind.lustre.Function;
import jkind.lustre.Location;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.TypeDef;

public class ProgramBuilder {
	private List<TypeDef> types = new ArrayList<>();
	private List<Constant> constants = new ArrayList<>();
	private List<Node> nodes = new ArrayList<>();
	private List<Function> functions = new ArrayList<>();
	private String main;

	public ProgramBuilder() {
	}

	public ProgramBuilder(Program program) {
		this.types = new ArrayList<>(program.types);
		this.constants = new ArrayList<>(program.constants);
		this.nodes = new ArrayList<>(program.nodes);
		this.functions = new ArrayList<>(program.functions);
		this.main = program.main;
	}

	public ProgramBuilder addType(TypeDef type) {
		this.types.add(type);
		return this;
	}

	public ProgramBuilder addTypes(Collection<TypeDef> types) {
		this.types.addAll(types);
		return this;
	}

	public ProgramBuilder clearTypes() {
		this.types.clear();
		return this;
	}

	public ProgramBuilder addConstant(Constant constant) {
		this.constants.add(constant);
		return this;
	}

	public ProgramBuilder addConstants(Collection<Constant> constants) {
		this.constants.addAll(constants);
		return this;
	}

	public ProgramBuilder clearConstants() {
		this.constants.clear();
		return this;
	}

	public ProgramBuilder addNode(Node node) {
		this.nodes.add(node);
		return this;
	}

	public ProgramBuilder addNodes(Collection<Node> nodes) {
		this.nodes.addAll(nodes);
		return this;
	}

	public ProgramBuilder clearNodes() {
		this.nodes.clear();
		return this;
	}

	public ProgramBuilder addFunction(Function function) {
		this.functions.add(function);
		return this;
	}

	public ProgramBuilder addFunctions(Collection<Function> functions) {
		this.functions.addAll(functions);
		return this;
	}

	public ProgramBuilder clearFunctions() {
		this.functions.clear();
		return this;
	}

	public ProgramBuilder setMain(String main) {
		this.main = main;
		return this;
	}

	public Program build() {
		return new Program(Location.NULL, types, constants, functions, nodes, main);
	}
}