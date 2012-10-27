package jkind.misc;

public class JKindException extends Error {
	private static final long serialVersionUID = 1L;

	public JKindException(String message) {
		super(message);
	}
	
	public JKindException(String message, Exception e) {
		super(message, e);
	}
}
