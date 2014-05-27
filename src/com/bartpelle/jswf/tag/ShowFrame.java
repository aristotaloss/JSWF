package com.bartpelle.jswf.tag;

import com.bartpelle.jswf.BitBuffer;

public class ShowFrame extends Tag {

	@Override
	public void decode(BitBuffer buffer, int len) {
		// Empty tag
	}
	
	@Override
	public void encode(BitBuffer buffer) {
		// Empty tag
	}

	@Override
	public String toString() {
		return "ShowFrame []";
	}

}
