package jkind.lustre;

import java.util.ArrayList;
import java.util.List;

import jkind.util.Util;

public class TypeConstructor {
	public final String name;
	//elements are always pair (e.g., (head int) (tail Lst))
	public final List<InductTypeElement> elements;
	
	public TypeConstructor(String name, List<InductTypeElement> elements){
		this.name = name;
		this.elements = Util.safeList(elements);
	}

}
