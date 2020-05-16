package jkind;

/**
 * An exception generated from JKind or the JKind API
 */
public class JKindException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public JKindException(String message) {
		super(message);
	}

	public JKindException(String message, Throwable t) {
		super(message, t);
	}
}
