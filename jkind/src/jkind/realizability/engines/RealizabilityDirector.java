package jkind.realizability.engines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jkind.ExitCodes;
import jkind.JKindException;
import jkind.JRealizabilitySettings;
import jkind.Main;
import jkind.StdErr;
import jkind.realizability.engines.messages.BaseStepMessage;
import jkind.realizability.engines.messages.ExtendCounterexampleMessage;
import jkind.realizability.engines.messages.InconsistentMessage;
import jkind.realizability.engines.messages.Message;
import jkind.realizability.engines.messages.RealizableMessage;
import jkind.realizability.engines.messages.UnknownMessage;
import jkind.realizability.engines.messages.UnrealizableMessage;
import jkind.realizability.writers.ConsoleWriter;
import jkind.realizability.writers.ExcelWriter;
import jkind.realizability.writers.Writer;
import jkind.realizability.writers.XmlWriter;
import jkind.results.Counterexample;
import jkind.results.layout.RealizabilityNodeLayout;
import jkind.solvers.Model;
import jkind.translation.Specification;
import jkind.util.CounterexampleExtractor;

public class RealizabilityDirector {
	private JRealizabilitySettings settings;
	private Specification spec;
	private Writer writer;

	private int baseStep = 0;
	private ExtendCounterexampleMessage extendCounterexample;
	private boolean done = false;

	private List<RealizabilityEngine> engines = new ArrayList<>();
	private List<Thread> threads = new ArrayList<>();

	protected BlockingQueue<Message> incoming = new LinkedBlockingQueue<>();

	public RealizabilityDirector(JRealizabilitySettings settings, Specification spec) {
		this.settings = settings;
		this.spec = spec;
		this.writer = getWriter(spec);
	}

	private Writer getWriter(Specification spec) {
		try {
			if (settings.excel) {
				return new ExcelWriter(settings.filename + ".xls", spec.node);
			} else if (settings.xml) {
				return new XmlWriter(settings.filename + ".xml", spec.typeMap);
			} else {
				return new ConsoleWriter(new RealizabilityNodeLayout(spec.node));
			}
		} catch (IOException e) {
			throw new JKindException("Unable to open output file", e);
		}
	}

	public int run() {
		printHeader();
		writer.begin();
		startThreads();

		long startTime = System.currentTimeMillis();
		long timeout = startTime + ((long) settings.timeout) * 1000;
		while (System.currentTimeMillis() < timeout && !done && someThreadAlive()
				&& !someEngineFailed()) {
			processMessages(startTime);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}

		processMessages(startTime);

		if (!done) {
			writer.writeUnknown(baseStep, convertExtendCounterexample(), getRuntime(startTime));
		}

		writer.end();
		return reportFailures();
	}

	private boolean someThreadAlive() {
		for (Thread thread : threads) {
			if (thread.isAlive()) {
				return true;
			}
		}

		return false;
	}

	private boolean someEngineFailed() {
		for (RealizabilityEngine process : engines) {
			if (process.getThrowable() != null) {
				return true;
			}
		}

		return false;
	}

	private int reportFailures() {
		int exitCode = 0;
		for (RealizabilityEngine process : engines) {
			if (process.getThrowable() != null) {
				Throwable t = process.getThrowable();
				StdErr.println(process.getName() + " process failed");
				StdErr.printStackTrace(t);
				exitCode = ExitCodes.UNCAUGHT_EXCEPTION;
			}
		}
		return exitCode;
	}

	private void printHeader() {
		System.out.println("==========================================");
		System.out.println("  JRealizability " + Main.VERSION);
		System.out.println("==========================================");
		System.out.println();
	}

	private void startThreads() {
		RealizabilityBaseEngine baseEngine = new RealizabilityBaseEngine(spec, settings, this);
		registerProcess(baseEngine);

		RealizabilityExtendEngine extendEngine = new RealizabilityExtendEngine(spec, settings, this);
		baseEngine.setExtendEngine(extendEngine);
		extendEngine.setBaseEngine(baseEngine);
		registerProcess(extendEngine);

		for (Thread thread : threads) {
			thread.start();
		}
	}

	private void registerProcess(RealizabilityEngine process) {
		engines.add(process);
		threads.add(new Thread(process, process.getName()));
	}

	private void processMessages(long startTime) {
		while (!done && !incoming.isEmpty()) {
			Message message = incoming.poll();
			double runtime = getRuntime(startTime);
			if (message instanceof RealizableMessage) {
				RealizableMessage rm = (RealizableMessage) message;
				done = true;
				writer.writeRealizable(rm.k, runtime);
			} else if (message instanceof UnrealizableMessage) {
				UnrealizableMessage um = (UnrealizableMessage) message;
				done = true;
				Counterexample cex = extractCounterexample(um.k, um.model);
				writer.writeUnrealizable(cex, um.properties, runtime);
			} else if (message instanceof ExtendCounterexampleMessage) {
				extendCounterexample = (ExtendCounterexampleMessage) message;
			} else if (message instanceof UnknownMessage) {
				done = true;
				writer.writeUnknown(baseStep, null, runtime);
			} else if (message instanceof BaseStepMessage) {
				BaseStepMessage bsm = (BaseStepMessage) message;
				writer.writeBaseStep(bsm.step);
				baseStep = bsm.step;
			} else if (message instanceof InconsistentMessage) {
				InconsistentMessage im = (InconsistentMessage) message;
				done = true;
				writer.writeInconsistent(im.k, runtime);
			} else {
				throw new JKindException("Unknown message type in director: "
						+ message.getClass().getCanonicalName());
			}
		}
	}

	private double getRuntime(long startTime) {
		return (System.currentTimeMillis() - startTime) / 1000.0;
	}

	private Counterexample convertExtendCounterexample() {
		if (extendCounterexample == null) {
			return null;
		}

		return extractCounterexample(extendCounterexample.k, extendCounterexample.model);
	}

	private Counterexample extractCounterexample(int k, Model model) {
		return CounterexampleExtractor.extract(spec, k, model);
	}
}