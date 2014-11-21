package jkind.engines.messages;

import java.util.Collections;
import java.util.List;

import jkind.engine.EngineType;
import jkind.solvers.Model;

public class InvalidMessage extends Message {
	final public List<String> invalid;
	final public int k;
	final public Model model;

	public InvalidMessage(EngineType source, List<String> invalid, int k, Model model) {
		super(source);
		this.invalid = safeCopy(invalid);
		this.k = k;
		this.model = model;
	}

	public InvalidMessage(EngineType source, String invalid, int k, Model model) {
		this(source, Collections.singletonList(invalid), k, model);
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
