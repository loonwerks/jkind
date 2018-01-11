package jkind.engines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import jkind.ExitCodes;
import jkind.JKindException;
import jkind.JKindSettings;
import jkind.Main;
import jkind.StdErr;
import jkind.advice.Advice;
import jkind.advice.AdviceReader;
import jkind.advice.AdviceWriter;
import jkind.engines.invariant.GraphInvariantGenerationEngine;
import jkind.engines.messages.BaseStepMessage;
import jkind.engines.messages.EngineType;
import jkind.engines.messages.InductiveCounterexampleMessage;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.Itinerary;
import jkind.engines.messages.Message;
import jkind.engines.messages.MessageHandler;
import jkind.engines.messages.StopMessage;
import jkind.engines.messages.UnknownMessage;
import jkind.engines.messages.ValidMessage;
import jkind.engines.pdr.PdrEngine;
import jkind.lustre.Expr;
import jkind.results.Counterexample;
import jkind.results.layout.NodeLayout;
import jkind.solvers.Model;
import jkind.translation.Specification;
import jkind.util.CounterexampleExtractor;
import jkind.util.ModelReconstructionEvaluator;
import jkind.util.Util;
import jkind.writers.ConsoleWriter;
import jkind.writers.ExcelWriter;
import jkind.writers.Writer;
import jkind.writers.XmlWriter;

public class Director extends MessageHandler {
	public static final String NAME = "director";

	private final JKindSettings settings;
	private final Specification userSpec;
	private final Specification analysisSpec;
	private final Writer writer;
	private final long startTime;

	private final List<String> remainingProperties = new ArrayList<>();
	private final List<String> validProperties = new ArrayList<>();
	private final List<String> invalidProperties = new ArrayList<>();
	private int baseStep = 0;
	private final Map<String, InductiveCounterexampleMessage> inductiveCounterexamples = new HashMap<>();

	private final List<Engine> engines = new ArrayList<>();
	private final List<Thread> threads = new ArrayList<>();

	private Advice inputAdvice;
	private AdviceWriter adviceWriter;

	public Director(JKindSettings settings, Specification userSpec, Specification analysisSpec) {
		this.settings = settings;
		this.userSpec = userSpec;
		this.analysisSpec = analysisSpec;

		this.writer = getWriter();
		this.startTime = System.currentTimeMillis();
		this.remainingProperties.addAll(analysisSpec.node.properties);

		if (settings.readAdvice != null) {
			this.inputAdvice = AdviceReader.read(settings.readAdvice);
		}

		if (settings.writeAdvice != null) {
			this.adviceWriter = new AdviceWriter(settings.writeAdvice);
			this.adviceWriter.addVarDecls(Util.getVarDecls(analysisSpec.node));
		}

		initializeUnknowns(settings, analysisSpec.node.properties);
	}

	private final Writer getWriter() {
		try {
			if (settings.excel) {
				return new ExcelWriter(settings.filename + ".xls", userSpec.node);
			} else if (settings.xml) {
				return new XmlWriter(settings.filename + ".xml", userSpec.typeMap,
						settings.xmlToStdout);
			} else {
				return new ConsoleWriter(new NodeLayout(userSpec.node));
			}
		} catch (IOException e) {
			throw new JKindException("Unable to open output file", e);
		}
	}

	public int run() {
		printHeader();
		writer.begin();
		addShutdownHook();
		createAndStartEngines();

		while (!timeout() && propertiesRemaining() && someThreadAlive() && !someEngineFailed()
				&& !exitRequested()) {
			processMessages();
			sleep(100);
		}

		stopEngines();
		processMessages();
		int exitCode = 0;
		if (removeShutdownHook()) {
			postProcessing();
			exitCode = reportFailures();
		}
		return exitCode;
	}

	private boolean exitRequested() {
		try {
			while (System.in.available() > 0) {
				if (System.in.read() == Util.END_OF_TEXT) {
					return true;
				}
			}
		} catch (IOException e) {
		}
		return false;
	}

	private void postProcessing() {
		writeUnknowns();
		writer.end();
		writeAdvice();
		printSummary();
	}

	private final Thread shutdownHook = new Thread("shutdown-hook") {
		@Override
		public void run() {
			Director.sleep(100);
			postProcessing();
		}
	};

	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	}

	private boolean removeShutdownHook() {
		try {
			Runtime.getRuntime().removeShutdownHook(shutdownHook);
			return true;
		} catch (IllegalStateException e) {
			// JVM already shutting down
			return false;
		}
	}

	private void createAndStartEngines() {
		createEngines();
		threads.forEach(Thread::start);
	}

	private void createEngines() {
		if (settings.boundedModelChecking) {
			addEngine(new BmcEngine(analysisSpec, settings, this));
		}

		if (settings.kInduction) {
			addEngine(new KInductionEngine(analysisSpec, settings, this));
		}

		if (settings.invariantGeneration) {
			addEngine(new GraphInvariantGenerationEngine(analysisSpec, settings, this));
		}

		if (settings.reduceIvc) {
			addEngine(new IvcReductionEngine(analysisSpec, settings, this));
		}

		if (settings.smoothCounterexamples) {
			addEngine(new SmoothingEngine(analysisSpec, settings, this));
		}

		if (settings.intervalGeneralization) {
			addEngine(new IntervalGeneralizationEngine(analysisSpec, settings, this));
		}

		if (settings.pdrMax > 0) {
			addEngine(new PdrEngine(analysisSpec, settings, this));
		}

		if (settings.readAdvice != null) {
			addEngine(new AdviceEngine(analysisSpec, settings, this, inputAdvice));
		}
	}

	private void addEngine(Engine engine) {
		engines.add(engine);
		threads.add(new Thread(engine, engine.getName()));
	}

	private void stopEngines() {
		for (Engine engine : engines) {
			engine.receiveMessage(new StopMessage());
		}
	}

	private static void sleep(int millis) {
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

	private int reportFailures() {
		int exitCode = 0;
		for (Engine engine : engines) {
			if (engine.getThrowable() != null) {
				StdErr.println(engine.getName() + " process failed");
				StdErr.printStackTrace(engine.getThrowable());
				exitCode = ExitCodes.UNCAUGHT_EXCEPTION;
			}
		}
		return exitCode;
	}

	private void printHeader() {
		if (!settings.xmlToStdout) {
			System.out.println("==========================================");
			System.out.println("  JKind " + Main.VERSION);
			System.out.println("==========================================");
			System.out.println();
			System.out.println("There are " + remainingProperties.size()
					+ " properties to be checked.");
			System.out.println("PROPERTIES TO BE CHECKED: " + remainingProperties);
			System.out.println();
		}
	}

	private void writeAdvice() {
		if (adviceWriter != null) {
			adviceWriter.write();
		}
	}

	public void broadcast(Message message) {
		receiveMessage(message);
		for (Engine engine : engines) {
			engine.receiveMessage(message);
		}
	}

	@Override
	protected void handleMessage(ValidMessage vm) {
		if (vm.getNextDestination() != null) {
			return;
		}

		List<String> newValid = intersect(vm.valid, remainingProperties);
		if (newValid.isEmpty()) {
			return;
		}

		remainingProperties.removeAll(newValid);
		validProperties.addAll(newValid);
		inductiveCounterexamples.keySet().removeAll(newValid);

		if (adviceWriter != null) {
			adviceWriter.addInvariants(vm.invariants);
		}

		List<Expr> invariants = settings.reduceIvc ? vm.invariants : Collections.emptyList();
		writer.writeValid(newValid, vm.source, vm.k, getRuntime(), invariants, vm.ivc);
	}

	private List<String> intersect(List<String> list1, List<String> list2) {
		List<String> result = new ArrayList<>();
		result.addAll(list1);
		result.retainAll(list2);
		return result;
	}

	@Override
	protected void handleMessage(InvalidMessage im) {
		if (im.getNextDestination() != null) {
			return;
		}

		List<String> newInvalid = intersect(im.invalid, remainingProperties);
		if (newInvalid.isEmpty()) {
			return;
		}

		remainingProperties.removeAll(newInvalid);
		invalidProperties.addAll(newInvalid);
		inductiveCounterexamples.keySet().removeAll(newInvalid);

		double runtime = getRuntime();
		for (String invalidProp : newInvalid) {
			Counterexample cex = extractCounterexample(invalidProp, im.length, im.model, true);
			writer.writeInvalid(invalidProp, im.source, cex, Collections.emptyList(), runtime);
		}
	}

	@Override
	protected void handleMessage(InductiveCounterexampleMessage icm) {
		for (String property : icm.properties) {
			inductiveCounterexamples.put(property, icm);
		}
	}

	private final Map<String, Integer> bmcUnknowns = new HashMap<>();
	private final Set<String> kInductionUnknowns = new HashSet<>();
	private final Set<String> pdrUnknowns = new HashSet<>();

	private void initializeUnknowns(JKindSettings settings, List<String> properties) {
		if (!settings.boundedModelChecking) {
			for (String prop : properties) {
				bmcUnknowns.put(prop, 0);
			}
		}

		if (!settings.kInduction) {
			kInductionUnknowns.addAll(properties);
		}

		if (settings.pdrMax <= 0) {
			pdrUnknowns.addAll(properties);
		}
	}

	@Override
	protected void handleMessage(UnknownMessage um) {
		if (um.source.equals(NAME)) {
			return;
		}

		markUnknowns(um);

		Map<Integer, List<String>> completed = getCompletelyUnknownByBaseStep(um);
		for (Entry<Integer, List<String>> entry : completed.entrySet()) {
			int baseStep = entry.getKey();
			List<String> unknowns = entry.getValue();
			remainingProperties.removeAll(unknowns);
			writer.writeUnknown(um.unknown, baseStep, convertInductiveCounterexamples(),
					getRuntime());
			broadcast(new UnknownMessage(NAME, unknowns));
		}
	}

	private Map<Integer, List<String>> getCompletelyUnknownByBaseStep(UnknownMessage um) {
		return um.unknown.stream().filter(this::isCompletelyUnknown)
				.collect(Collectors.groupingBy(bmcUnknowns::get));
	}

	private void markUnknowns(UnknownMessage um) {
		switch (um.source) {
		case BmcEngine.NAME:
			for (String prop : um.unknown) {
				bmcUnknowns.put(prop, baseStep);
			}
			break;

		case KInductionEngine.NAME:
			kInductionUnknowns.addAll(um.unknown);
			break;

		case PdrEngine.NAME:
			pdrUnknowns.addAll(um.unknown);
			break;
		}
	}

	public boolean isCompletelyUnknown(String prop) {
		return bmcUnknowns.containsKey(prop) && kInductionUnknowns.contains(prop)
				&& pdrUnknowns.contains(prop);
	}

	@Override
	protected void handleMessage(BaseStepMessage bsm) {
		baseStep = bsm.step;
		if (!bsm.properties.isEmpty()) {
			writer.writeBaseStep(bsm.properties, baseStep);
		}
	}

	@Override
	protected void handleMessage(InvariantMessage im) {
	}

	public Itinerary getValidMessageItinerary() {
		List<EngineType> destinations = new ArrayList<>();
		if (settings.reduceIvc) {
			destinations.add(EngineType.IVC_REDUCTION);
		}
		return new Itinerary(destinations);
	}

	public Itinerary getInvalidMessageItinerary() {
		List<EngineType> destinations = new ArrayList<>();
		if (settings.smoothCounterexamples) {
			destinations.add(EngineType.SMOOTHING);
		}
		if (settings.intervalGeneralization) {
			destinations.add(EngineType.INTERVAL_GENERALIZATION);
		}
		return new Itinerary(destinations);
	}

	private double getRuntime() {
		return (System.currentTimeMillis() - startTime) / 1000.0;
	}

	private void printSummary() {
		if (!settings.xmlToStdout) {
			System.out.println("    -------------------------------------");
			System.out.println("    --^^--        SUMMARY          --^^--");
			System.out.println("    -------------------------------------");
			System.out.println();
			if (!validProperties.isEmpty()) {
				System.out.println("VALID PROPERTIES: " + validProperties);
				System.out.println();
			}
			if (!invalidProperties.isEmpty()) {
				System.out.println("INVALID PROPERTIES: " + invalidProperties);
				System.out.println();
			}

			List<String> unknownProperties = new ArrayList<>(analysisSpec.node.properties);
			unknownProperties.removeAll(validProperties);
			unknownProperties.removeAll(invalidProperties);
			if (!unknownProperties.isEmpty()) {
				System.out.println("UNKNOWN PROPERTIES: " + unknownProperties);
				System.out.println();
			}
		}
	}

	private Map<String, Counterexample> convertInductiveCounterexamples() {
		Map<String, Counterexample> result = new HashMap<>();

		for (String prop : inductiveCounterexamples.keySet()) {
			InductiveCounterexampleMessage icm = inductiveCounterexamples.get(prop);
			result.put(prop, extractCounterexample(prop, icm.length, icm.model, false));
		}

		return result;
	}

	private Counterexample extractCounterexample(String property, int k, Model model,
			boolean concrete) {
		model = ModelReconstructionEvaluator.reconstruct(userSpec, analysisSpec, model, property, k, concrete);
		return CounterexampleExtractor.extract(userSpec, k, model);
	}
}
