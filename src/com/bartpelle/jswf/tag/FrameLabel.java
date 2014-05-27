package com.bartpelle.jswf.tag;

import com.bartpelle.jswf.BitBuffer;

public class FrameLabel extends Tag {

	private String label;

	@Override
	public void decode(BitBuffer buffer, int len) {
		label = buffer.readString();
	}
	
	@Override
	public void encode(BitBuffer buffer) {
		buffer.writeString(label);
	}

	@Override
	public String toString() {
		return "FrameLabel [label=" + label + "]";
	}

}
