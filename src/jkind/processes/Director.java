package jkind.processes;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jkind.lustre.Node;
import jkind.processes.messages.CounterexampleMessage;
import jkind.processes.messages.Message;
import jkind.processes.messages.ValidMessage;
import jkind.translation.Lustre2Sexps;
import jkind.translation.Util;
import jkind.writers.Writer;
import jkind.writers.XmlWriter;

public class Director {
	private Node node;
	private List<String> remainingProperties;
	private List<String> validProperties;
	private List<String> invalidProperties;
	private Writer writer;
	
	private Thread baseThread;
	private Thread inductiveThread;
	protected BlockingQueue<Message> incomming;
	
	public Director(String filename, Node node) throws FileNotFoundException {
		this.node = node;
		this.remainingProperties = new ArrayList<String>(node.properties);
		this.validProperties = new ArrayList<String>();
		this.invalidProperties = new ArrayList<String>();
		// this.writer = new ConsoleWriter();
		this.writer = new XmlWriter(filename + ".xml", Util.createTypeMap(node));
		this.incomming = new LinkedBlockingQueue<Message>();
	}

	public void run() throws FileNotFoundException {
		printHeader();
		writer.begin();
		startThreads();
		
		long timeout = System.currentTimeMillis() + 30 * 1000;
		while (System.currentTimeMillis() < timeout && !remainingProperties.isEmpty()) {
			processMessages();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		
		writer.end();
		printSummary();
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
		
		BaseProcess base = new BaseProcess(node.properties, translation, this);
		InductiveProcess inductive = new InductiveProcess(node.properties, translation, this);
		
		base.setInductiveProcess(inductive);
		inductive.setBaseProcess(base);
		
		baseThread = new Thread(base);
		inductiveThread = new Thread(inductive);
		
		baseThread.start();
		inductiveThread.start();
	}

	private void processMessages() {
		while (!incomming.isEmpty()) {
			Message message = incomming.poll();
			if (message instanceof ValidMessage) {
				ValidMessage vm = (ValidMessage) message;
				remainingProperties.removeAll(vm.valid);
				validProperties.addAll(vm.valid);
				writer.writeValid(vm.valid, vm.k);
			} else if (message instanceof CounterexampleMessage) {
				CounterexampleMessage cex = (CounterexampleMessage) message;
				remainingProperties.removeAll(cex.invalid);
				invalidProperties.addAll(cex.invalid);
				writer.writeInvalid(cex.invalid, cex.k, cex.model);
			} else {
				throw new IllegalArgumentException("Unknown message type in director: "
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
			System.out.println("TIMEOUT PROPERTIES: " + remainingProperties);
			System.out.println();
		}
	}
}
