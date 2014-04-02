package jkind.lustre;

import java.util.Collections;
import java.util.List;

public class InlinedProgram {
	public final List<Function> functions;
	public final Node node;

	public InlinedProgram(List<Function> functions, Node node) {
		this.functions = Collections.unmodifiableList(functions);
		this.node = node;
	}

	@Override
	public String toString() {
		return new Program(Location.NULL, Collections.<TypeDef> emptyList(),
				Collections.<Constant> emptyList(), functions, Collections.singletonList(node),
				null).toString();
	}
}