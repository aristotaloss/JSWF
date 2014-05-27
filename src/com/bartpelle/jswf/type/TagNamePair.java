package com.bartpelle.jswf.type;

public class TagNamePair {
	
	public int tag;
	public String name;
	
	public TagNamePair(int tag, String name) {
		this.tag = tag;
		this.name = name;
	}

	@Override
	public String toString() {
		return "TagNamePair [tag=" + tag + ", name=" + name + "]";
	}
	
}