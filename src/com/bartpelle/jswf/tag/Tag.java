package com.bartpelle.jswf.tag;

import com.bartpelle.jswf.BitBuffer;

public abstract class Tag {
	
	public abstract void decode(BitBuffer buffer, int len);
	
	public abstract void encode(BitBuffer buffer);
}
