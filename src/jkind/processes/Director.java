package jkind.processes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jkind.lustre.Node;
import jkind.processes.messages.CounterexampleMessage;
import jkind.processes.messages.Message;
import jkind.processes.messages.ValidMessage;
import jkind.solvers.Model;
import jkind.solvers.Value;
import jkind.translation.Lustre2Sexps;

public class Director {
	private Node node;
	private List<String> propertiesLeft;
	
	private Thread baseThread;
	private Thread inductiveThread;
	
	protected BlockingQueue<Message> incomming = new LinkedBlockingQueue<Message>();
	
	public Director(Node node) {
		this.node = node;
		propertiesLeft = new ArrayList<String>(node.properties);
	}

	public void run() {
		startThreads();
		long timeout = System.currentTimeMillis() + 30 * 1000;
		while (System.currentTimeMillis() < timeout && !propertiesLeft.isEmpty()) {
			processMessages();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}

		if (!propertiesLeft.isEmpty()) {
			System.out.println("Timeout on properties: " + propertiesLeft);
		}
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
				propertiesLeft.removeAll(vm.valid);
				System.out.println("Valid properties at k = " + vm.k + ": " + vm.valid);
				System.out.println();
			} else if (message instanceof CounterexampleMessage) {
				CounterexampleMessage cex = (CounterexampleMessage) message;
				propertiesLeft.removeAll(cex.invalid);
				printCounterexample(cex.invalid, cex.length, cex.model);
			} else {
				throw new IllegalArgumentException("Unknown message type in director: "
						+ message.getClass().getCanonicalName());
			}
		}
	}

	private void printCounterexample(List<String> invalid, int length, Model model) {
		System.out.println("Properties " + invalid + " are invalid with length " + length);
		for (String fn : model.getFunctions()) {
			System.out.print(fn + ": ");
			Map<Integer, Value> fnMap = model.getFunction(fn);
			for (int i = 0; i <= length; i++) {
				System.out.print("\t" + fnMap.get(i));
			}
			System.out.println();
		}
		System.out.println();
	}
}
