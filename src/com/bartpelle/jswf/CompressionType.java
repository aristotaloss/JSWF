package com.bartpelle.jswf;

public enum CompressionType {
	
	NONE, ZLIB, LZMA;
	
	public static CompressionType forChar(int c) {
		if (c == 'C')
			return ZLIB;
		else if (c == 'Z')
			return LZMA;
		else return NONE;
	}

}
