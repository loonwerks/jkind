package jkind.writers;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
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
import jkind.util.Tuple;

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

	private String escape(String invariant) {
		return invariant.replace("<", "&lt;").replace(">", "&gt;");
	}

	@Override
	public void writeValid(List<String> props, String source, int k, double proofTime, double runtime,
			List<Expr> invariants, Set<String> ivc, List<Tuple<Set<String>, List<String>>> allIvcs) {
		List<String> invText = invariants.stream().map(Expr::toString).collect(toList());
		// doesn't write allIvcs...
		for (String prop : props) {
			Set<List<String>> invariantSets = new HashSet<List<String>>();
			Set<List<String>> ivcSets = new HashSet<List<String>>();
			//The following are similar from the process in XmlWriter
			if (!allIvcs.isEmpty()) {
				for(Tuple<Set<String>, List<String>> ivcSet : allIvcs){
					List<String> curInvariant = new ArrayList<String>();
					List<String> curIvc = new ArrayList<String>();
					for (String invariant : ivcSet.secondElement()) {
						curInvariant.add(escape(invariant));
					}
					for (String supp : ivcSet.firstElement()) {
						curIvc.add(supp);
					}
					invariantSets.add(curInvariant);
					ivcSets.add(curIvc);
				}
			}
			properties.add(new ValidProperty(prop, source, k, runtime, invText, ivc, invariantSets, ivcSets));
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
