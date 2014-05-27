package com.bartpelle.jswf.as;

public enum NamespaceKind {
	
	NAMESPACE(0x8),
	PACKAGE_NAMESPACE(0x16),
	PACKAGE_INTERNAL_NS(0x17),
	PROTECTED_NAMESPACE(0x18),
	EXPLICIT_NAMESPACE(0x19),
	STATIC_PROTECTED_NS(0x1A),
	PRIVATE_NAMESPACE(0x5);
	
	public int value;
	
	private NamespaceKind(int val) {
		value = val;
	}
	
	public static NamespaceKind forValue(int v) {
		for (NamespaceKind k : values()) {
			if (k.value == v)
				return k;
		}
		
		return null;
	}

}
