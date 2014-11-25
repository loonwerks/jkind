package jkind.engines.messages;

import java.util.Collections;
import java.util.List;

import jkind.engines.EngineType;
import jkind.solvers.Model;

public class InvalidMessage extends Message {
	final public String originalSource;
	final public List<String> invalid;
	final public int length;
	final public Model model;

	public InvalidMessage(EngineType source, String originalSource, List<String> invalid,
			int length, Model model) {
		super(source);
		this.originalSource = originalSource;
		this.invalid = safeCopy(invalid);
		this.length = length;
		this.model = model;
	}

	public InvalidMessage(EngineType source, String originalSource, String invalid, int length, Model model) {
		this(source, originalSource, Collections.singletonList(invalid), length, model);
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
