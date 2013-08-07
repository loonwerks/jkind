package jkind.api.results;

import jkind.results.InvalidProperty;
import jkind.results.Property;
import jkind.results.UnknownProperty;
import jkind.results.ValidProperty;

public class PropertyResult extends AnalysisResult {
	private int elapsed;
	private Status status;
	private Property property;
	private final Renaming renaming;

	public PropertyResult(String name, Renaming renaming) {
		super(name);
		this.elapsed = 0;
		this.status = Status.WAITING;
		this.property = null;
		this.renaming = renaming;
	}

	public int getElapsed() {
		return elapsed;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public Property getProperty() {
		return property;
	}
	
	public void setProperty(Property original) {
		if (renaming == null) {
			property = original;
		} else {
			property = renaming.rename(original);
		}
		
		if (property instanceof ValidProperty) {
			setStatus(Status.VALID);
		} else if (property instanceof InvalidProperty) {
			setStatus(Status.INVALID);
		} else if (property instanceof UnknownProperty) {
			setStatus(Status.UNKNOWN);
		}
	}
	
	public void start() {
		setStatus(Status.WORKING);
	}

	public void tick() {
		if (status == Status.WORKING) {
			pcs.firePropertyChange("elapased", elapsed, ++elapsed);
		}
	}
	
	public void cancel() {
		if (status == Status.WORKING || status == Status.WAITING) {
			setStatus(Status.CANCELED);
		}
	}
	
	public void done() {
		if (status == Status.WORKING || status == Status.WAITING) {
			setStatus(Status.ERROR);
		}
	}
	
	private void setStatus(Status status) {
		pcs.firePropertyChange("status", this.status, this.status = status);
	}
	
	@Override
	public String toString() {
		return name + " - " + status;
	}
}
