package jkind.engines.ivcs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllIVCs {
	Set<String> allIVCSet = new HashSet<>();
	List<String> allIVCList = new ArrayList<String>();

	public AllIVCs(Set<String> allIVCSet, List<String> allIVCList) {
		this.allIVCSet = allIVCSet;
		this.allIVCList = allIVCList;
	}

	public Set<String> getAllIVCSet() {
		return allIVCSet;
	}

	public List<String> getAllIVCList() {
		return allIVCList;
	}
}
