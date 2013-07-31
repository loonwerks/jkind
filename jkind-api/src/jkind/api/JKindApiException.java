package jkind.api;

public class JKindApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public JKindApiException(String message) {
		super(message);
	}

	public JKindApiException(String message, Exception e) {
		super(message, e);
	}
}
