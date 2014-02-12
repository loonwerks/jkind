package jkind.util;

import jkind.lustre.Type;

public class TypeResolutionException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public final Type type;

	public TypeResolutionException(Type type) {
		this.type = type;
	}
}
