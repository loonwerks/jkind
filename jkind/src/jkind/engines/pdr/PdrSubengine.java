package jkind.engines.pdr;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import de.uni_freiburg.informatik.ultimate.logic.Term;
import jkind.analysis.LinearChecker;
import jkind.engines.Director;
import jkind.engines.StopException;
import jkind.engines.messages.InvalidMessage;
import jkind.engines.messages.InvariantMessage;
import jkind.engines.messages.Itinerary;
import jkind.engines.messages.ValidMessage;
import jkind.engines.pdr.PdrSmt.Option;
import jkind.lustre.Expr;
import jkind.lustre.Function;
import jkind.lustre.Node;
import jkind.lustre.builders.NodeBuilder;
import jkind.slicing.LustreSlicer;
import jkind.solvers.Model;
import jkind.translation.Specification;

/**
 * PDR algorithm based on "Efficient implementation of property directed
 * reachability" by Niklas Een, Alan Mishchenko, and Robert Brayton
 * 
 * SMT extension based on "IC3 Modulo Theories via Implicit Predicate
 * Abstraction" by Alessandro Cimatti, Alberto Griggio, Sergio Mover, and
 * Stefano Tonetta
 */
public class PdrSubengine extends Thread {
	private final Node node;
	private final List<Function> functions;
	private final String prop;
	
	private final PdrEngine parent;
	private final Director director;

	private final List<Frame> F = new ArrayList<>();
	private final String scratchBase;
	private PdrSmt Z;

	private volatile boolean cancel = false;

	public PdrSubengine(String prop, Specification spec, String scratchBase, PdrEngine parent, Director director) {
		super("pdr-" + prop);
		this.prop = prop;
		Node single = new NodeBuilder(spec.node).clearProperties().addProperty(prop).build();
		this.node = LustreSlicer.slice(single, spec.dependencyMap);
		this.functions = spec.functions;
		this.scratchBase = scratchBase;
		this.parent = parent;
		this.director = director;
	}

	public void cancel() {
		cancel = true;
	}

	@Override
	public void run() {
		if (!LinearChecker.isLinear(this.node)) {
			parent.reportUnknown(prop);
			return;
		}

		Z = new PdrSmt(node, functions, F, prop, scratchBase);
		Z.comment("Checking property: " + prop);

		// Create F_INF and F[0]
		F.add(new Frame());
		addFrame(Z.createInitialFrame());

		try {
			while (true) {
				Cube c = Z.getBadCube();
				if (c != null) {
					blockCube(new TCube(c, depth()));
				} else {
					addFrame(new Frame());
					Z.comment("Number of frames: " + F.size());
					List<Expr> invariants = propogateBlockedCubes();
					if (invariants != null) {
						sendValidAndInvariants(invariants);
						return;
					}
				}
			}
		} catch (CounterexampleException cex) {
			Z.comment("Found counterexample of length " + cex.getLength());
			sendInvalid(cex.getLength(), cex.getModel());
			return;
		} catch (StopException | OutOfMemoryError e) {
			parent.reportUnknown(prop);
			return;
		} catch (Throwable t) {
			parent.reportThrowable(t);
			return;
		}
	}

	private void blockCube(TCube s0) {
		PriorityQueue<TCube> Q = new PriorityQueue<>();
		Q.add(s0);

		while (!Q.isEmpty()) {
			checkCancel();
			TCube s = Q.poll();

			if (s.getFrame() == 0) {
				Z.refine(getCubes(s.getCube()));
				Z.comment("Refined abstraction");
				return;
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

		// Semantic subsumption thru SAT:
		return Z.isBlocked(s);
	}

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

	private List<Expr> propogateBlockedCubes() {
		for (int k = 1; k < depth(); k++) {
			for (Cube c : new ArrayList<>(F.get(k).getCubes())) {
				checkCancel();
				TCube s = Z.solveRelative(new TCube(c, k + 1), Option.NO_IND);
				if (s.getFrame() != TCube.FRAME_NULL) {
					addBlockedCube(s);
				}
			}

			if (F.get(k).isEmpty()) {
				return getInvariants(k + 1);
			}
		}

		return null;
	}

	private List<Expr> getInvariants(int k) {
		List<Expr> invariants = new ArrayList<>();
		for (int i = k; i < F.size(); i++) {
			for (Cube c : F.get(i).getCubes()) {
				invariants.add(Z.getInvariant(c));
			}
		}
		return invariants;
	}

	private void checkCancel() {
		if (cancel) {
			throw new StopException();
		}
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
		Z.comment("Blocked [" + k + "] : " + s.getCube());

		// Report if invariant
		if (s.getFrame() == TCube.FRAME_INF) {
			Expr invariant = Z.getInvariant(s.getCube());
			sendInvariant(invariant);
		}
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

	private void sendValidAndInvariants(List<Expr> invariants) {
		Itinerary itinerary = director.getValidMessageItinerary();
		director.broadcast(new ValidMessage(parent.getName(), prop, 1, invariants, null, itinerary));
		director.broadcast(new InvariantMessage(invariants));
	}

	private void sendInvalid(int length, Model model) {
		Itinerary itinerary = director.getInvalidMessageItinerary();
		director.broadcast(new InvalidMessage(parent.getName(), prop, length, model, itinerary));
	}

	private void sendInvariant(Expr invariant) {
		director.broadcast(new InvariantMessage(invariant));
	}
}
