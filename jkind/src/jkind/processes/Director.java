package jkind.processes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jkind.JKindException;
import jkind.JKindSettings;
import jkind.Output;
import jkind.invariant.Invariant;
import jkind.lustre.EnumType;
import jkind.lustre.Type;
import jkind.lustre.values.EnumValue;
import jkind.lustre.values.IntegerValue;
import jkind.lustre.values.Value;
import jkind.processes.messages.CounterexampleMessage;
import jkind.processes.messages.InductiveCounterexampleMessage;
import jkind.processes.messages.InvalidMessage;
import jkind.processes.messages.Message;
import jkind.processes.messages.UnknownMessage;
import jkind.processes.messages.ValidMessage;
import jkind.results.Counterexample;
import jkind.results.Signal;
import jkind.results.layout.NodeLayout;
import jkind.solvers.Model;
import jkind.translation.Specification;
import jkind.util.StreamIndex;
import jkind.writers.ConsoleWriter;
import jkind.writers.ExcelWriter;
import jkind.writers.Writer;
import jkind.writers.XmlWriter;

public class Director {
	private JKindSettings settings;
	private Specification spec;
	private Writer writer;

	private List<String> remainingProperties = new ArrayList<>();
	private List<String> validProperties = new ArrayList<>();
	private List<String> invalidProperties = new ArrayList<>();
	private List<String> unknownProperties = new ArrayList<>();
	private Map<String, InductiveCounterexampleMessage> inductiveCounterexamples = new HashMap<>();

	private BaseProcess baseProcess;
	private InductiveProcess inductiveProcess;
	private InvariantProcess invariantProcess;
	private ReduceProcess reduceProcess;
	private SmoothProcess smoothProcess;
	private IntervalProcess intervalProcess;

	private List<Process> processes = new ArrayList<>();
	private List<Thread> threads = new ArrayList<>();

	protected BlockingQueue<Message> incoming = new LinkedBlockingQueue<>();

	public Director(JKindSettings settings, Specification spec) {
		this.settings = settings;
		this.spec = spec;
		this.writer = getWriter(spec);
		this.remainingProperties.addAll(spec.node.properties);
	}

	private Writer getWriter(Specification spec) {
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
		startThreads();

		long startTime = System.currentTimeMillis();
		long timeout = startTime + ((long) settings.timeout) * 1000;
		while (System.currentTimeMillis() < timeout && !remainingProperties.isEmpty()
				&& someThreadAlive() && !someProcessFailed()) {
			processMessages(startTime);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}

		processMessages(startTime);

		unknownProperties.addAll(remainingProperties);
		remainingProperties.clear();
		if (!unknownProperties.isEmpty()) {
			writer.writeUnknown(unknownProperties, convertInductiveCounterexamples());
		}

		writer.end();
		printSummary();
		reportFailures();
	}

	private boolean someThreadAlive() {
		for (Thread thread : threads) {
			if (thread.isAlive()) {
				return true;
			}
		}

		return false;
	}

	private boolean someProcessFailed() {
		for (Process process : processes) {
			if (process.getThrowable() != null) {
				return true;
			}
		}

		return false;
	}

	private void reportFailures() {
		for (Process process : processes) {
			if (process.getThrowable() != null) {
				Throwable t = process.getThrowable();
				Output.println(process.getName() + " process failed");
				Output.printStackTrace(t);
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

	private void startThreads() {
		baseProcess = new BaseProcess(spec, settings, this);
		registerProcess(baseProcess);

		if (settings.useInductiveProcess) {
			inductiveProcess = new InductiveProcess(spec, settings, this);
			baseProcess.setInductiveProcess(inductiveProcess);
			inductiveProcess.setBaseProcess(baseProcess);
			registerProcess(inductiveProcess);
		}

		if (settings.useInvariantProcess) {
			invariantProcess = new InvariantProcess(spec, settings);
			invariantProcess.setInductiveProcess(inductiveProcess);
			inductiveProcess.setInvariantProcess(invariantProcess);
			registerProcess(invariantProcess);
		}

		if (settings.reduceInvariants) {
			reduceProcess = new ReduceProcess(spec, settings, this);
			inductiveProcess.setReduceProcess(reduceProcess);
			registerProcess(reduceProcess);
		}

		if (settings.smoothCounterexamples) {
			smoothProcess = new SmoothProcess(spec, settings, this);
			baseProcess.setCounterexampleProcess(smoothProcess);
			registerProcess(smoothProcess);
		}

		if (settings.intervalGeneralization) {
			intervalProcess = new IntervalProcess(spec, settings, this);
			if (smoothProcess == null) {
				baseProcess.setCounterexampleProcess(intervalProcess);
			} else {
				smoothProcess.setCounterexampleProcess(intervalProcess);
			}
			registerProcess(intervalProcess);
		}

		for (Thread thread : threads) {
			thread.start();
		}
	}

	private void registerProcess(Process process) {
		processes.add(process);
		threads.add(new Thread(process, process.getName()));
	}

	private void processMessages(long startTime) {
		while (!incoming.isEmpty()) {
			Message message = incoming.poll();
			double runtime = (System.currentTimeMillis() - startTime) / 1000.0;
			if (message instanceof ValidMessage) {
				ValidMessage vm = (ValidMessage) message;
				remainingProperties.removeAll(vm.valid);
				validProperties.addAll(vm.valid);
				inductiveCounterexamples.keySet().removeAll(vm.valid);
				List<Invariant> invariants = vm.invariants;
				if (reduceProcess == null) {
					invariants = Collections.<Invariant> emptyList();
				}
				writer.writeValid(vm.valid, vm.k, runtime, invariants);
			} else if (message instanceof InvalidMessage) {
				InvalidMessage im = (InvalidMessage) message;
				remainingProperties.removeAll(im.invalid);
				invalidProperties.addAll(im.invalid);
				inductiveCounterexamples.keySet().removeAll(im.invalid);
				for (String invalidProp : im.invalid) {
					Model slicedModel = im.model.slice(spec.dependencyMap.get(invalidProp));
					Counterexample cex = extractCounterexample(im.k, slicedModel);
					writer.writeInvalid(invalidProp, cex, runtime);
				}
			} else if (message instanceof CounterexampleMessage) {
				CounterexampleMessage cm = (CounterexampleMessage) message;
				remainingProperties.remove(cm.invalid);
				invalidProperties.add(cm.invalid);
				inductiveCounterexamples.keySet().remove(cm.invalid);
				writer.writeInvalid(cm.invalid, cm.cex, runtime);
			} else if (message instanceof InductiveCounterexampleMessage) {
				InductiveCounterexampleMessage icm = (InductiveCounterexampleMessage) message;
				inductiveCounterexamples.put(icm.property, icm);
			} else if (message instanceof UnknownMessage) {
				UnknownMessage um = (UnknownMessage) message;
				remainingProperties.removeAll(um.unknown);
				unknownProperties.addAll(um.unknown);
			} else {
				throw new JKindException("Unknown message type in director: "
						+ message.getClass().getCanonicalName());
			}
		}
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
			if (!unknownProperties.isEmpty()) {
				Output.println("UNKNOWN PROPERTIES: " + unknownProperties);
				Output.println();
			}
		}
	}

	private Map<String, Counterexample> convertInductiveCounterexamples() {
		Map<String, Counterexample> result = new HashMap<>();

		for (String prop : inductiveCounterexamples.keySet()) {
			InductiveCounterexampleMessage icm = inductiveCounterexamples.get(prop);
			Model slicedModel = icm.model.slice(spec.dependencyMap.get(icm.property));
			result.put(prop, extractCounterexample(icm.k, slicedModel));
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
			return new EnumValue(et.values.get(iv.value.intValue()));
		}
		return value;
	}
}
