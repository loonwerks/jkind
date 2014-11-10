package jkind.pdr;

public class CounterexampleException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final Cube init;

	public CounterexampleException(Cube init) {
		this.init = init;
	}
	
	public Cube getInit() {
		return init;
	}
}
