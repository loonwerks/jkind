package jkind.results;


public abstract class Property {
	protected final String name;
	protected final int k;

	public Property(String name, int k) {
		this.name = name;
		this.k = k;
	}

	public String getName() {
		return name;
	}
	
	public int getK() {
		return k;
	}

	public abstract Property rename(Renaming renaming);
}
