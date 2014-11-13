package jkind.pdr;

import java.util.List;

import de.uni_freiburg.informatik.ultimate.logic.Script;
import de.uni_freiburg.informatik.ultimate.smtinterpol.smtlib2.SMTInterpol;
import jkind.lustre.Node;

public class PdrSat {
	private final Node node;
	private final List<Frame> F;
	private final Script script = new SMTInterpol();
	
	public enum Option { DEFAULT, EXTRACT_MODEL, NO_IND };

	public PdrSat(Node node, List<Frame> F) {
		this.node = node;
		this.F = F;

		Lustre2Term lustre2Term = new Lustre2Term(script);
	}

	public Cube getBadCube() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isInitial(Cube cube) {
		// TODO Auto-generated method stub
		return false;
	}

	public TCube solveRelative(TCube s, Option option) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public TCube solveRelative(TCube s) {
		return solveRelative(s, Option.DEFAULT);
	}

	public boolean isBlocked(TCube s) {
		// TODO Auto-generated method stub
		return false;
	}

	public void blockCubeInSolver(TCube s) {
		// TODO Auto-generated method stub
		
	}

	public void refine(Cube cube) {
		// TODO Auto-generated method stub
		
	}

	public Frame initialFrame() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean refine(List<Cube> cubes) {
		// TODO Auto-generated method stub
		return false;
	}
}
