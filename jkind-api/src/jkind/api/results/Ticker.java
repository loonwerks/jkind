package jkind.api.results;

public class Ticker extends Thread {
	private DynamicJKindResult result;
	private boolean done;

	public Ticker(DynamicJKindResult result) {
		super("Ticker");
		this.result = result;
		this.done = false;
	}

	@Override
	public void run() {
		try {
			while (!done) {
				Thread.sleep(1000);
				result.tick();
			}
		} catch (InterruptedException e) {
		}
	}

	public void done() {
		done = true;
	}
}
