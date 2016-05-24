package jkind.api.results;

public enum Status {
	WORKING, VALID, INVALID, UNKNOWN, INCONSISTENT, CANCELED, ERROR, WAITING, VALID_REFINED;

	@Override
	public String toString() {
		switch (this) {
		case WORKING:
			return "Working";
		case VALID:
			return "Valid";
		case INVALID:
			return "Invalid";
		case UNKNOWN:
			return "Unknown";
		case INCONSISTENT:
			return "Inconsistent";
		case CANCELED:
			return "Canceled";
		case ERROR:
			return "Error";
		case WAITING:
			return "Waiting";
		case VALID_REFINED:
		    return "Valid (refined)";
		default:
			return "";
		}
	}
}
