package jkind.realizability.writers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import jkind.lustre.Node;
import jkind.results.Counterexample;
import jkind.results.layout.RealizabilityNodeLayout;
import jkind.util.Util;

public class ExcelWriter extends Writer {
	private final jkind.writers.ExcelWriter internal;
	private final ConsoleWriter summaryWriter = new ConsoleWriter(null);
	private static final List<String> REALIZABLE_LIST = Collections.singletonList(Util.REALIZABLE);

	public ExcelWriter(String filename, Node node) {
		this.internal = new jkind.writers.ExcelWriter(filename, new RealizabilityNodeLayout(node));
	}

	@Override
	public void begin() {
		internal.begin();
	}

	@Override
	public void end() {
		internal.end();
	}

	@Override
	public void writeBaseStep(int k) {
		internal.writeBaseStep(REALIZABLE_LIST, k);
	}

	@Override
	public void writeRealizable(int k, double runtime) {
		internal.writeValid(REALIZABLE_LIST, "extend", k, runtime, Collections.emptyList(),
				Collections.emptySet());
		summaryWriter.writeRealizable(k, runtime);
	}

	@Override
	public void writeUnrealizable(Counterexample cex, List<String> conflicts, double runtime) {
		internal.writeInvalid(Util.REALIZABLE, "base", cex, conflicts, runtime);
		summaryWriter.writeUnrealizable(cex, conflicts, runtime);
	}

	@Override
	public void writeUnknown(int trueFor, Counterexample cex, double runtime) {
		Map<String, Counterexample> map = Collections.singletonMap(Util.REALIZABLE, cex);
		internal.writeUnknown(REALIZABLE_LIST, trueFor, map, runtime);
		summaryWriter.writeUnknown(trueFor, cex, runtime);
	}

	@Override
	public void writeInconsistent(int k, double runtime) {
		internal.writeInconsistent(Util.REALIZABLE, "base", k, runtime);
		summaryWriter.writeInconsistent(k, runtime);
	}
}