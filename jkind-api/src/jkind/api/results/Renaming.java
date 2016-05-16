package jkind.api.results;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;

import jkind.lustre.values.Value;
import jkind.results.Counterexample;
import jkind.results.InconsistentProperty;
import jkind.results.InvalidProperty;
import jkind.results.Property;
import jkind.results.Signal;
import jkind.results.UnknownProperty;
import jkind.results.ValidProperty;

/**
 * A class for renaming and removing variables from analysis results
 * 
 * @see MapRenaming
 */
public abstract class Renaming {
	/**
	 * Returns the new name for a given name, or null if the original name
	 * should be hidden. This method should always return the same result when
	 * given the same input.
	 * 
	 * @param original
	 *            Original variable name
	 * @return the new variable name or null if variable should be hidden
	 */
	public abstract String rename(String original);

	/**
	 * Rename property and signals (if present), possibly omitting some
	 * 
	 * @param property
	 *            Property to be renamed
	 * @return Renamed version of the property, or <code>null</code> if there is
	 *         no renaming for the property
	 */
	public Property rename(Property property) {
		if (property instanceof ValidProperty) {
			return rename((ValidProperty) property);
		} else if (property instanceof InvalidProperty) {
			return rename((InvalidProperty) property);
		} else if (property instanceof UnknownProperty) {
			return rename((UnknownProperty) property);
		} else if (property instanceof InconsistentProperty) {
			return rename((InconsistentProperty) property);
		} else {
			return null;
		}
	}

	/**
	 * Rename valid property and signals (if present), possibly omitting some
	 * 
	 * Note: Invariants (if present) will not be renamed
	 * 
	 * @param property
	 *            Property to be renamed
	 * @return Renamed version of the property, or <code>null</code> if there is
	 *         no renaming for the property
	 */
	public ValidProperty rename(ValidProperty property) {
		String name = rename(property.getName());
		if (name == null) {
			return null;
		}

		return new ValidProperty(name, property.getSource(), property.getK(),
				property.getRuntime(), property.getInvariants(), rename(this::renameIVC,
						property.getIvc()));
	}

	/**
	 * Rename invalid property and signals (if present), possibly omitting some
	 * 
	 * @param property
	 *            Property to be renamed
	 * @return Renamed version of the property, or <code>null</code> if there is
	 *         no renaming for the property
	 */
	public InvalidProperty rename(InvalidProperty property) {
		String name = rename(property.getName());
		if (name == null) {
			return null;
		}

		return new InvalidProperty(name, property.getSource(),
				rename(property.getCounterexample()),
				rename(this::rename, property.getConflicts()), property.getRuntime());
	}

	/**
	 * Rename unknown property and signals (if present), possibly omitting some
	 * 
	 * @param property
	 *            Property to be renamed
	 * @return Renamed version of the property, or <code>null</code> if there is
	 *         no renaming for the property
	 */
	public UnknownProperty rename(UnknownProperty property) {
		String name = rename(property.getName());
		if (name == null) {
			return null;
		}

		return new UnknownProperty(name, property.getTrueFor(),
				rename(property.getInductiveCounterexample()), property.getRuntime());
	}

	/**
	 * Rename inconsistent property
	 * 
	 * @param property
	 *            Property to be renamed
	 * @return Renamed version of the property, or <code>null</code> if there is
	 *         no renaming for the property
	 */
	public InconsistentProperty rename(InconsistentProperty property) {
		String name = rename(property.getName());
		if (name == null) {
			return null;
		}

		return new InconsistentProperty(name, property.getSource(), property.getK(),
				property.getRuntime());
	}

	/**
	 * Rename signals in a counterexample, possibly omitting some
	 * 
	 * @param cex
	 *            Counterexample to be renamed
	 * @return Renamed version of the counterexample
	 */
	protected Counterexample rename(Counterexample cex) {
		if (cex == null) {
			return null;
		}

		Counterexample result = new Counterexample(cex.getLength());
		for (Signal<Value> signal : cex.getSignals()) {
			Signal<Value> newSignal = rename(signal);
			if (newSignal != null) {
				result.addSignal(newSignal);
			}
		}
		return result;
	}

	/**
	 * Rename signal
	 * 
	 * @param <T>
	 * 
	 * @param signal
	 *            The signal to be renamed
	 * @return Renamed version of the signal or <code>null</code> if there is no
	 *         renaming for it
	 */
	private <T extends Value> Signal<T> rename(Signal<T> signal) {
		String name = rename(signal.getName());
		if (name == null) {
			return null;
		}

		Signal<T> newSignal = new Signal<>(name);
		for (Entry<Integer, T> entry : signal.getValues().entrySet()) {
			newSignal.putValue(entry.getKey(), entry.getValue());
		}
		return newSignal;
	}

	/**
	 * Rename a collection of elements, possibly omitting some
	 * 
	 * @param es
	 *            Strings to be renamed
	 * @return Renamed version of the conflicts
	 */
	private List<String> rename(Function<String, String> f, Collection<String> es) {
		return es.stream().map(f).filter(e -> e != null).collect(toList());
	}

	/**
	 * Rename an IVC variable
	 * 
	 * @param ivc
	 *            the string to be renamed
	 * @return Renamed version of the ivc string
	 */
	public String renameIVC(String ivc) {
		return rename(ivc);
	}
}
