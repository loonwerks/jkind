package jkind.lustre;

import java.io.File;
import java.io.IOException;

public abstract class Ast {
	final public Location location;
	
	public Ast(Location location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		PrettyPrintVisitor visitor = new PrettyPrintVisitor();
		accept(visitor);
		return visitor.toString();
	}

	public void toFile(File file) throws IOException {
		
	}
	
	public abstract <T> T accept(AstVisitor<T> visitor);
}
