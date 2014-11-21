package jkind.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.Output;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.Message;
import jkind.engines.messages.MessageHandler;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.invariant.Invariant;
import jkind.lustre.EnumType;
import jkind.lustre.Type;
import jkind.lustre.values.EnumValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.Signal;
import jkind.results.layout.NodeLayout;
import jkind.slicing.ModelSlicer;
import jkind.solvers.Model;
import jkind.translation.Specification;
import jkind.util.StreamIndex;
import jkind.writers.ConsoleWriter;
import jkind.writers.ExcelWriter;
import jkind.writers.Writer;
import jkind.writers.XmlWriter;

public class Director extends MessageHandler {
	private final JKindSettings settings;
	private final Specification spec;
	private final Writer writer;
	private final long startTime;

	private final List<String> remainingProperties = new ArrayList<>();
	private final List<String> validProperties = new ArrayList<>();
	private final List<String> invalidProperties = new ArrayList<>();
	private int baseStep = 0;
	private final Map<String, InductiveCounterexampleMessage> inductiveCounterexamples = new HashMap<>();

	private final List<Engine> engines = new ArrayList<>();
	private final List<Thread> threads = new ArrayList<>();

	public Director(JKindSettings settings, Specification spec) {
		this.settings = settings;
		this.spec = spec;
		this.writer = getWriter(spec);
		this.startTime = System.currentTimeMillis();
		this.remainingProperties.addAll(spec.node.properties);
	}

	private final Writer getWriter(Specification spec) {
		try {
			if (settings.excel) {
				return new ExcelWriter(spec.filename + ".xls", spec.node);
			} else if (settings.xml) {
				return new XmlWriter(spec.filename + ".xml", spec.typeMap, settings.xmlToStdout);
			} else {
				return new ConsoleWriter(new NodeLayout(spec.node));
			}
		} catch (IOException e) {
			throw new JKindException("Unable to open output file", e);
		}
	}

	public void run() {
		printHeader();
		writer.begin();
		createAndStartEngines();

		while (!timeout() && propertiesRemaining() && someThreadAlive() && !someEngineFailed()) {
			processMessages();
			sleep(100);
		}

		processMessages();
		writeUnknowns();

		writer.end();
		printSummary();
		reportFailures();
	}

	private void createAndStartEngines() {
		createEngines();
		threads.stream().forEach(Thread::start);
	}

	private void createEngines() {
		addEngine(new BmcEngine(spec, settings, this));

		if (settings.useInductiveProcess) {
			addEngine(new KInductionEngine(spec, settings, this));
		}

		if (settings.useInvariantProcess) {
			addEngine(new InvariantGenerationEngine(spec, settings, this));
		}

		if (settings.reduceInvariants) {
			addEngine(new InvariantReductionEngine(spec, settings, this));
		}

		if (settings.smoothCounterexamples) {
			addEngine(new SmoothProcess(spec, settings, this));
		}

		if (settings.intervalGeneralization) {
			addEngine(new IntervalGeneralizationEngine(spec, settings, this));
		}
	}

	private void addEngine(Engine engine) {
		engines.add(engine);
		threads.add(new Thread(engine, engine.getName()));
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	private boolean timeout() {
		long timeout = startTime + ((long) settings.timeout) * 1000;
		return System.currentTimeMillis() > timeout;
	}

	private boolean propertiesRemaining() {
		return !remainingProperties.isEmpty();
	}

	private boolean someThreadAlive() {
		return threads.stream().anyMatch(Thread::isAlive);
	}

	private boolean someEngineFailed() {
		return engines.stream().anyMatch(e -> e.getThrowable() != null);
	}

	private void writeUnknowns() {
		if (!remainingProperties.isEmpty()) {
			writer.writeUnknown(remainingProperties, baseStep, convertInductiveCounterexamples(),
					getRuntime());
		}
	}

	private void reportFailures() {
		for (Engine engine : engines) {
			if (engine.getThrowable() != null) {
				Output.println(engine.getName() + " process failed");
				Output.printStackTrace(engine.getThrowable());
			}
		}
	}

	private void printHeader() {
		if (!settings.xmlToStdout) {
			Output.println("==========================================");
			Output.println("  JAVA KIND");
			Output.println("==========================================");
			Output.println();
			Output.println("There are " + remainingProperties.size() + " properties to be checked.");
			Output.println("PROPERTIES TO BE CHECKED: " + remainingProperties);
			Output.println();
		}
	}

	public void broadcast(Message message, Engine source) {
		receiveMessage(message);
		for (Engine engine : engines) {
			if (engine != source) {
				engine.receiveMessage(message);
			}
		}
	}

	@Override
	protected void handleMessage(ValidMessage vm) {
		if (nextResponsible(vm) != null) {
			return;
		}

		remainingProperties.removeAll(vm.valid);
		validProperties.addAll(vm.valid);
		inductiveCounterexamples.keySet().removeAll(vm.valid);

		List<Invariant> invariants = settings.reduceInvariants ? vm.invariants : Collections
				.emptyList();
		writer.writeValid(vm.valid, vm.k, getRuntime(), invariants);
	}

	// General flow of valid messages:
	// (k-induction | pdr | ...) -> invariant reduction -> director
	public EngineType nextResponsible(ValidMessage vm) {
		switch (vm.getSource()) {
		case INVARIANT_REDUCTION:
			return null;

		default:
			if (settings.reduceInvariants) {
				return EngineType.INVARIANT_REDUCTION;
			} else {
				return null;
			}
		}
	}

	@Override
	protected void handleMessage(InvalidMessage im) {
		if (nextResponsible(im) != null) {
			return;
		}

		remainingProperties.removeAll(im.invalid);
		invalidProperties.addAll(im.invalid);
		inductiveCounterexamples.keySet().removeAll(im.invalid);

		double runtime = getRuntime();
		for (String invalidProp : im.invalid) {
			Model slicedModel = ModelSlicer.slice(im.model, spec.dependencyMap.get(invalidProp));
			Counterexample cex = extractCounterexample(im.k, slicedModel);
			writer.writeInvalid(invalidProp, cex, runtime);
		}
	}

	// General flow of invalid messages:
	// (bmc | pdr | ...) -> smoothing -> interval generalization -> director
	public EngineType nextResponsible(InvalidMessage im) {
		switch (im.getSource()) {
		case INTERVAL_GENERALIZATION:
			return null;

		case SMOOTHING:
			return settings.intervalGeneralization ? EngineType.INTERVAL_GENERALIZATION : null;

		default:
			if (settings.smoothCounterexamples) {
				return EngineType.SMOOTHING;
			} else if (settings.intervalGeneralization) {
				return EngineType.INTERVAL_GENERALIZATION;
			} else {
				return null;
			}
		}
	}

	@Override
	protected void handleMessage(InductiveCounterexampleMessage icm) {
		inductiveCounterexamples.put(icm.property, icm);
	}

	@Override
	protected void handleMessage(UnknownMessage um) {
		remainingProperties.removeAll(um.unknown);
		writer.writeUnknown(um.unknown, baseStep, convertInductiveCounterexamples(), getRuntime());
	}

	@Override
	protected void handleMessage(BaseStepMessage bsm) {
		baseStep = bsm.step;
		if (!remainingProperties.isEmpty()) {
			writer.writeBaseStep(remainingProperties, baseStep);
		}
	}

	@Override
	protected void handleMessage(InvariantMessage im) {
	}

	private double getRuntime() {
		return (System.currentTimeMillis() - startTime) / 1000.0;
	}

	private void printSummary() {
		if (!settings.xmlToStdout) {
			Output.println("    -------------------------------------");
			Output.println("    --^^--        SUMMARY          --^^--");
			Output.println("    -------------------------------------");
			Output.println();
			if (!validProperties.isEmpty()) {
				Output.println("VALID PROPERTIES: " + validProperties);
				Output.println();
			}
			if (!invalidProperties.isEmpty()) {
				Output.println("INVALID PROPERTIES: " + invalidProperties);
				Output.println();
			}
			if (!remainingProperties.isEmpty()) {
				Output.println("UNKNOWN PROPERTIES: " + remainingProperties);
				Output.println();
			}
		}
	}

	int todo_move_all_to_separate_class;

	private Map<String, Counterexample> convertInductiveCounterexamples() {
		Map<String, Counterexample> result = new HashMap<>();

		for (String prop : inductiveCounterexamples.keySet()) {
			InductiveCounterexampleMessage icm = inductiveCounterexamples.get(prop);
			Model slicedModel = ModelSlicer.slice(icm.model, spec.dependencyMap.get(icm.property));
			result.put(prop, extractCounterexample(icm.length, slicedModel));
		}

		return result;
	}

	private Counterexample extractCounterexample(int k, Model model) {
		Counterexample cex = new Counterexample(k);
		for (String var : model.getVariableNames()) {
			StreamIndex si = StreamIndex.decode(var);
			if (si.getIndex() >= 0 && !isInternal(si.getStream())) {
				Signal<Value> signal = cex.getOrCreateSignal(si.getStream());
				Value value = convert(si.getStream(), model.getValue(var));
				signal.putValue(si.getIndex(), value);
			}
		}
		return cex;
	}

	private boolean isInternal(String stream) {
		return stream.startsWith("%");
	}

	private Value convert(String base, Value value) {
		Type type = spec.typeMap.get(base);
		if (type instanceof EnumType) {
			EnumType et = (EnumType) type;
			IntegerValue iv = (IntegerValue) value;
			return new EnumValue(et.getValue(iv.value.intValue(), ""));
		}
		return value;
	}
}
