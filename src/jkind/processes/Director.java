package jkind.processes;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jkind.JKindException;
import jkind.Settings;
import jkind.lustre.Node;
import jkind.lustre.Type;
import jkind.processes.messages.CounterexampleMessage;
import jkind.processes.messages.Message;
import jkind.processes.messages.ValidMessage;
import jkind.translation.Lustre2Sexps;
import jkind.translation.Util;
import jkind.writers.ConsoleWriter;
import jkind.writers.Writer;
import jkind.writers.XmlWriter;

public class Director {
	private String filename;
	private Node node;
	private List<String> remainingProperties;
	private List<String> validProperties;
	private List<String> invalidProperties;
	private Map<String, Type> typeMap;
	private Writer writer;
	
	private Thread baseThread;
	private Thread inductiveThread;
	private Thread invariantThread;
	protected BlockingQueue<Message> incomming;
	private long startTime;
	
	public Director(String filename, Node node) {
		this.filename = filename;
		this.node = node;
		this.remainingProperties = new ArrayList<String>(node.properties);
		this.validProperties = new ArrayList<String>();
		this.invalidProperties = new ArrayList<String>();
		this.typeMap = Util.createTypeMap(node);
		this.writer = getWriter();
		this.incomming = new LinkedBlockingQueue<Message>();
	}

	private Writer getWriter() {
		if (Settings.xml) {
			try {
				return new XmlWriter(filename + ".xml", typeMap);
			} catch (FileNotFoundException e) {
				throw new JKindException("Unable to open XML output file", e);
			}
		} else {
			return new ConsoleWriter();
		}
	}

	public void run() {
		printHeader();
		writer.begin();
		startThreads();
		
		startTime = System.currentTimeMillis();
		long timeout = startTime + Settings.timeout * 1000;
		while (System.currentTimeMillis() < timeout && !remainingProperties.isEmpty()
				&& someThreadAlive()) {
			processMessages();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		
		processMessages();
		if (!remainingProperties.isEmpty()) {
			writer.writeUnknown(remainingProperties);
		}
		
		writer.end();
		printSummary();
	}

	private boolean someThreadAlive() {
		boolean result = baseThread.isAlive();
		if (inductiveThread != null) {
			result = result || inductiveThread.isAlive();
		}
		if (invariantThread != null) {
			result = result || invariantThread.isAlive();
		}
		return result;
	}

	private void printHeader() {
		System.out.println("==========================================");
		System.out.println("  JAVA KIND");
		System.out.println("==========================================");
		System.out.println();
		System.out.println("There are " + remainingProperties.size() + " properties to be checked.");
		System.out.println("PROPERTIES TO BE CHECKED: " + remainingProperties);
		System.out.println();
	}

	private void startThreads() {
		Lustre2Sexps translation = new Lustre2Sexps(node);
		
		BaseProcess base = new BaseProcess(filename, node.properties, translation, this);
		InductiveProcess inductive = new InductiveProcess(filename, node.properties, translation,
				this);
		InvariantProcess invariant = new InvariantProcess(filename, translation, typeMap);

		base.setInductiveProcess(inductive);
		inductive.setBaseProcess(base);
		invariant.setInductiveProcess(inductive);
		
		baseThread = new Thread(base, "base");
		inductiveThread = new Thread(inductive, "inductive");
		invariantThread = new Thread(invariant, "invariant");
		
		baseThread.start();
		inductiveThread.start();
		invariantThread.start();
	}

	private void processMessages() {
		while (!incomming.isEmpty()) {
			Message message = incomming.poll();
			long elapsed = System.currentTimeMillis() - startTime;
			if (message instanceof ValidMessage) {
				ValidMessage vm = (ValidMessage) message;
				remainingProperties.removeAll(vm.valid);
				validProperties.addAll(vm.valid);
				writer.writeValid(vm.valid, vm.k, elapsed);
			} else if (message instanceof CounterexampleMessage) {
				CounterexampleMessage cex = (CounterexampleMessage) message;
				remainingProperties.removeAll(cex.invalid);
				invalidProperties.addAll(cex.invalid);
				writer.writeInvalid(cex.invalid, cex.k, cex.model, elapsed);
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
}
