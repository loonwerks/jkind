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
import jkind.Settings;
import jkind.invariant.Invariant;
import jkind.processes.messages.CounterexampleMessage;
import jkind.processes.messages.InductiveCounterexampleMessage;
import jkind.processes.messages.Message;
import jkind.processes.messages.ValidMessage;
import jkind.slicing.CounterexampleSlicer;
import jkind.solvers.Model;
import jkind.translation.Specification;
import jkind.writers.ConsoleWriter;
import jkind.writers.ExcelWriter;
import jkind.writers.Writer;
import jkind.writers.XmlWriter;

public class Director {
	private Specification spec;
	private Writer writer;

	private List<String> remainingProperties = new ArrayList<String>();
	private List<String> validProperties = new ArrayList<String>();
	private List<String> invalidProperties = new ArrayList<String>();
	private Map<String, InductiveCounterexampleMessage> inductiveCounterexamples = new HashMap<String, InductiveCounterexampleMessage>();

	private BaseProcess baseProcess;
	private InductiveProcess inductiveProcess;
	private InvariantProcess invariantProcess;
	private ReduceProcess reduceProcess;
	private SmoothProcess smoothProcess;
	private List<Process> processes = new ArrayList<Process>();
	private List<Thread> threads = new ArrayList<Thread>();

	protected BlockingQueue<Message> incoming = new LinkedBlockingQueue<Message>();

	public Director(Specification spec) {
		this.spec = spec;
		this.writer = getWriter(spec);
		this.remainingProperties.addAll(spec.node.properties);
	}

	private Writer getWriter(Specification spec) {
		try {
			if (Settings.excel) {
				return new ExcelWriter(spec.filename + ".xls", spec.node);
			} else if (Settings.xml) {
				return new XmlWriter(spec.filename + ".xml", spec.typeMap);
			} else {
				return new ConsoleWriter();
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
		long timeout = startTime + Settings.timeout * 1000;
		while (System.currentTimeMillis() < timeout && !remainingProperties.isEmpty()
				&& someThreadAlive() && !someProcessFailed()) {
			processMessages(startTime);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}

		processMessages(startTime);
		if (!remainingProperties.isEmpty()) {
			sliceInductiveCounterexamples();
			writer.writeUnknown(remainingProperties, inductiveCounterexamples);
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
				System.out.println(process.getName() + " process failed");
				t.printStackTrace(System.out);
			}
		}
	}

	private void printHeader() {
		System.out.println("==========================================");
		System.out.println("  JAVA KIND");
		System.out.println("==========================================");
		System.out.println();
		System.out
				.println("There are " + remainingProperties.size() + " properties to be checked.");
		System.out.println("PROPERTIES TO BE CHECKED: " + remainingProperties);
		System.out.println();
	}

	private void startThreads() {
		baseProcess = new BaseProcess(spec, this);
		registerProcess(baseProcess);

		if (Settings.useInductiveProcess) {
			inductiveProcess = new InductiveProcess(spec, this);
			baseProcess.setInductiveProcess(inductiveProcess);
			inductiveProcess.setBaseProcess(baseProcess);
			registerProcess(inductiveProcess);
		}

		if (Settings.useInvariantProcess) {
			invariantProcess = new InvariantProcess(spec);
			invariantProcess.setInductiveProcess(inductiveProcess);
			inductiveProcess.setInvariantProcess(invariantProcess);
			registerProcess(invariantProcess);
		}

		if (Settings.reduceInvariants) {
			reduceProcess = new ReduceProcess(spec, this);
			registerProcess(reduceProcess);
		}

		if (Settings.smoothCounterexamples) {
			smoothProcess = new SmoothProcess(spec, this);
			registerProcess(smoothProcess);
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
			long elapsed = System.currentTimeMillis() - startTime;
			if (message instanceof ValidMessage) {
				ValidMessage vm = (ValidMessage) message;
				if (reduceProcess != null && !vm.reduced) {
					reduceProcess.incoming.add(message);
				} else {
					remainingProperties.removeAll(vm.valid);
					validProperties.addAll(vm.valid);
					inductiveCounterexamples.keySet().removeAll(vm.valid);
					List<Invariant> invariants = vm.invariants;
					if (reduceProcess == null) {
						invariants = Collections.<Invariant> emptyList();
					}
					writer.writeValid(vm.valid, vm.k, elapsed, invariants);
				}
			} else if (message instanceof CounterexampleMessage) {
				CounterexampleMessage cex = (CounterexampleMessage) message;
				if (smoothProcess != null && !cex.smooth) {
					smoothProcess.incoming.add(message);
				} else {
					remainingProperties.removeAll(cex.invalid);
					invalidProperties.addAll(cex.invalid);
					inductiveCounterexamples.keySet().removeAll(cex.invalid);
					CounterexampleSlicer cexSlicer = new CounterexampleSlicer(spec.dependencyMap);
					for (String invalidProp : cex.invalid) {
						Model slicedModel = cexSlicer.slice(invalidProp, cex.model);
						writer.writeInvalid(invalidProp, cex.k, slicedModel, elapsed);
					}
				}
			} else if (message instanceof InductiveCounterexampleMessage) {
				InductiveCounterexampleMessage icm = (InductiveCounterexampleMessage) message;
				inductiveCounterexamples.put(icm.property, icm);
			} else {
				throw new JKindException("Unknown message type in director: "
						+ message.getClass().getCanonicalName());
			}
		}
	}

	private void printSummary() {
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
		if (!remainingProperties.isEmpty()) {
			System.out.println("UNKNOWN PROPERTIES: " + remainingProperties);
			System.out.println();
		}
	}

	private void sliceInductiveCounterexamples() {
		CounterexampleSlicer cexSlicer = new CounterexampleSlicer(spec.dependencyMap);
		for (String prop : inductiveCounterexamples.keySet()) {
			InductiveCounterexampleMessage icm = inductiveCounterexamples.get(prop);
			Model slicedModel = cexSlicer.slice(icm.property, icm.model);
			InductiveCounterexampleMessage slicedIcm = new InductiveCounterexampleMessage(
					icm.property, icm.n, icm.k, slicedModel);
			inductiveCounterexamples.put(prop, slicedIcm);
		}
	}
}
