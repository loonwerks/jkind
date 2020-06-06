package jkind;

public enum AllIvcsAlgorithm {
	OFFLINE_MIVC_ENUM_ALG, ONLINE_MIVC_ENUM_ALG;
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
