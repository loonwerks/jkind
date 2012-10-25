package jkind.processes.messages;


public class BaseStepMessage extends Message {
	public int step;

	public BaseStepMessage(int step) {
		this.step = step;
	}
}
