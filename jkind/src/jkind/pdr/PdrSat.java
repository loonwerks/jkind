package jkind.pdr;

import java.util.List;

import jkind.lustre.Node;

public class PdrSat {
	private final Node node;
	private final List<Frame> F;
	
	public enum Option { DEFAULT, EXTRACT_MODEL, NO_IND };

	public PdrSat(Node node, List<Frame> F) {
		this.node = node;
		this.F = F;
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
