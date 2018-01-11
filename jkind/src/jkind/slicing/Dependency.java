package jkind.slicing;

public class Dependency {
	public final String name;
	public final DependencyType type;

	public Dependency(String name, DependencyType type) {
		this.name = name;
		this.type = type;
	}

	public static Dependency variable(String name) {
		return new Dependency(name, DependencyType.VARIABLE);
	}

	public static Dependency function(String name) {
		return new Dependency(name, DependencyType.FUNCTION);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Dependency)) {
			return false;
		}
		Dependency other = (Dependency) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}
