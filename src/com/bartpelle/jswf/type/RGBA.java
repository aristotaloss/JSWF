package com.bartpelle.jswf.type;

import com.bartpelle.jswf.BitBuffer;

public class RGBA extends RGB {
	
	private int alpha;

	public RGBA(BitBuffer buffer) {
		super(buffer);
		
		alpha = buffer.readByte() & 0xFF;
	}

}
