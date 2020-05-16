package jkind.solvers.smtinterpol;

import de.uni_freiburg.informatik.ultimate.smtinterpol.smtlib2.TerminationRequest;

public class TerminationRequestImpl implements TerminationRequest {

	private boolean requestTermination = false;

	public void requestTermination() {
		this.requestTermination = true;
	}

	@Override
	public boolean isTerminationRequested() {
		// TODO Auto-generated method stub
		return this.requestTermination;
	}

}
