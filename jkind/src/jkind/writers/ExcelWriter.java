package jkind.writers;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jkind.excel.ExcelFormatter;
import jkind.lustre.Expr;
import jkind.lustre.Node;
import jkind.results.Counterexample;
import jkind.results.InconsistentProperty;
import jkind.results.InvalidProperty;
import jkind.results.Property;
import jkind.results.UnknownProperty;
import jkind.results.ValidProperty;
import jkind.results.layout.Layout;
import jkind.results.layout.NodeLayout;

public class ExcelWriter extends Writer {
	private final File file;
	private final Layout layout;
	private final List<Property> properties = new ArrayList<>();
	private ExcelFormatter formatter;

	public ExcelWriter(String filename, Node node) {
		this.file = new File(filename);
		this.layout = new NodeLayout(node);
	}

	public ExcelWriter(String filename, Layout layout) {
		this.file = new File(filename);
		this.layout = layout;
	}

	@Override
	public void begin() {
		formatter = new ExcelFormatter(file, layout);
	}

	@Override
	public void end() {
		formatter.write(properties);
		formatter.close();
	}

	@Override
	public void writeValid(List<String> props, String source, int k, double runtime,
			List<Expr> invariants, Set<String> ivc) {
		List<String> invText = invariants.stream().map(Expr::toString).collect(toList());
		for (String prop : props) {
			properties.add(new ValidProperty(prop, source, k, runtime, invText, ivc));
		}
	}

	@Override
	public void writeInvalid(String prop, String source, Counterexample cex,
			List<String> conflicts, double runtime) {
		properties.add(new InvalidProperty(prop, source, cex, conflicts, runtime));
	}

	@Override
	public void writeUnknown(List<String> props, int trueFor,
			Map<String, Counterexample> inductiveCounterexamples, double runtime) {
		for (String prop : props) {
			properties.add(new UnknownProperty(prop, trueFor, inductiveCounterexamples.get(prop),
					runtime));
		}
	}

	@Override
	public void writeBaseStep(List<String> props, int k) {
	}

	@Override
	public void writeInconsistent(String prop, String source, int k, double runtime) {
		properties.add(new InconsistentProperty(prop, source, k, runtime));
	}
}
