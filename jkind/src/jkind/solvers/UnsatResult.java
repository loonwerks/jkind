package jkind.solvers;

import java.util.ArrayList;
import java.util.List;

public class UnsatResult extends Result {
	final private List<String> unsatCore = new ArrayList<>();
	
	public List<String> getUnsatCore() {
		return unsatCore;
	}
}
