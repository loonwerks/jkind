package jkind.solvers;

import java.util.ArrayList;
import java.util.List;

public class UnsatResult extends Result {
	final private List<Label> unsatCore = new ArrayList<>();
	
	public List<Label> getUnsatCore() {
		return unsatCore;
	}
}
