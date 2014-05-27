package com.bartpelle.jswf.as;

public class NamespaceInfo {
	
	public NamespaceKind kind;
	public int name;
	
	public NamespaceInfo(NamespaceKind k, int n) {
		kind = k;
		name = n;
	}

	@Override
	public String toString() {
		return "NamespaceInfo [kind=" + kind + ", name=" + name + "]";
	}

}
