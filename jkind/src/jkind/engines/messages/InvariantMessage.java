package jkind.engines.messages;

import java.util.Collections;
import java.util.List;

import jkind.lustre.Expr;

public class InvariantMessage extends Message {
	public final List<Expr> invariants;

	public InvariantMessage(List<Expr> invs) {
		this.invariants = safeCopy(invs);
	}

	public InvariantMessage(Expr invariant) {
		this(Collections.singletonList(invariant));
	}

	@Override
	public void accept(MessageHandler handler) {
		handler.handleMessage(this);
	}
}
