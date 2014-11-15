package jkind.pdr;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import jkind.lustre.Node;
import jkind.pdr.PdrSat.Option;
import de.uni_freiburg.informatik.ultimate.logic.Term;

/**
 * PDR algorithm based on
 * "Efficient implementation of property directed reachability" by Niklas Een,
 * Alan Mishchenko, and Robert Brayton
 * 
 * SMT extension based on
 * "IC3 Modulo Theories via Implicit Predicate Abstraction" by Alessandro
 * Cimatti, Alberto Griggio, Sergio Mover, and Stefano Tonetta
 */
public class Pdr {
	private final Node node;
	private final List<Frame> F = new ArrayList<>();
	private PdrSat Z;

	public Pdr(Node node) {
		this.node = node;
	}

	public Cube pdrMain() {
		Z = new PdrSat(node, F);

		// Create F_INF and F[0]
		F.add(new Frame());
		addFrame(Z.createInitialFrame());

		try {
			while (true) {
				Cube c = Z.getBadCube();
				if (c != null) {
					recBlockCube(new TCube(c, depth()));
				} else {
					addFrame(new Frame());
					if (propogateBlockedCubes()) {
						return null;
					}
				}
			}
		} catch (CounterexampleException cex) {
			return cex.getInit();
		}
	}

	private void recBlockCube(TCube s0) {
		PriorityQueue<TCube> Q = new PriorityQueue<>();
		Q.add(s0);

		while (!Q.isEmpty()) {
			TCube s = Q.poll();

			if (s.getFrame() == 0) {
				if (!Z.refine(getCubes(s.getCube()))) {
					throw new CounterexampleException(s.getCube());
				} else {
					return;
				}
			}

			if (!isBlocked(s)) {
				assert (!Z.isInitial(s.getCube()));
				TCube z = Z.solveRelative(s, Option.EXTRACT_MODEL);
				if (z.getFrame() != TCube.FRAME_NULL) {
					// Cube 's' was blocked by image of predecessor
					generalize(z);

					// Push z as far forward as possible
					while (z.getFrame() < depth() - 1) {
						TCube nz = Z.solveRelative(z.next());
						if (nz.getFrame() != TCube.FRAME_NULL) {
							z = nz;
						} else {
							break;
						}
					}

					addBlockedCube(z);
					if (s.getFrame() < depth() && z.getFrame() != TCube.FRAME_INF) {
						Q.add(s.next());
					}
				} else {
					// Cube 's' was not blocked by image of predecessor
					z.setFrame(s.getFrame() - 1);
					z.getCube().setNext(s.getCube());
					Q.add(z);
					Q.add(s);
				}
			}
		}
	}

	private boolean isBlocked(TCube s) {
		// Check syntactic subsumption (faster than SAT):
		for (int d = s.getFrame(); d < F.size(); d++) {
			for (Cube c : F.get(d).getCubes()) {
				if (c.subsumes(s.getCube())) {
					return true;
				}
			}
		}

		// Semantics subsumption thru SAT:
		return Z.isBlocked(s);
	}

	// TODO: Move to pdrsat?
	private void generalize(TCube s) {
		List<Term> pLiterals = new ArrayList<>(s.getCube().getPLiterals());

		for (Term p : pLiterals) {
			s.getCube().removePLiteral(p);
			if (Z.isInitial(s.getCube()) || Z.solveRelative(s).getFrame() == TCube.FRAME_NULL) {
				s.getCube().addPLiteral(p);
			}
		}
	}

	private int depth() {
		return F.size() - 2;
	}

	private void addFrame(Frame frame) {
		F.add(F.size() - 1, frame);
	}

	private boolean propogateBlockedCubes() {
		for (int k = 1; k < depth(); k++) {
			for (Cube c : new ArrayList<>(F.get(k).getCubes())) {
				TCube s = Z.solveRelative(new TCube(c, k + 1), Option.NO_IND);
				if (s.getFrame() != TCube.FRAME_NULL) {
					addBlockedCube(s);
				}
			}

			if (F.get(k).isEmpty()) {
				return true;
			}
		}

		return false;
	}

	private void addBlockedCube(TCube s) {
		int k = Math.min(s.getFrame(), depth() + 1);

		// Remove subsumed clauses:
		for (int d = 1; d <= k; d++) {
			Set<Cube> cubes = F.get(d).getCubes();
			for (Cube c : new ArrayList<>(cubes)) {
				if (s.getCube().subsumes(c)) {
					cubes.remove(c);
				}
			}
		}

		// Store clause
		F.get(k).add(s.getCube());
		Z.blockCubeInSolver(s);
	}

	private List<Cube> getCubes(Cube init) {
		List<Cube> result = new ArrayList<>();
		Cube curr = init;
		while (curr != null) {
			result.add(curr);
			curr = curr.getNext();
		}
		return result;
	}
}
