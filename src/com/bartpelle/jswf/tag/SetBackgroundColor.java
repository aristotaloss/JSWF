package com.bartpelle.jswf.tag;

import com.bartpelle.jswf.BitBuffer;
import com.bartpelle.jswf.type.RGB;

public class SetBackgroundColor extends Tag {

	private RGB background_color;

	@Override
	public void decode(BitBuffer buffer, int len) {
		background_color = new RGB(buffer);
	}

	@Override
	public void encode(BitBuffer buffer) {
		background_color.encode(buffer);
	}
	
	@Override
	public String toString() {
		return "SetBackgroundColor [background_color=" + background_color + "]";
	}

}
