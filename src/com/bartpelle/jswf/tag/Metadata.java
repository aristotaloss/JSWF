package com.bartpelle.jswf.tag;

import com.bartpelle.jswf.BitBuffer;

public class Metadata extends Tag {
	
	private String metadata;
	
	@Override
	public void decode(BitBuffer buffer, int len) {
		metadata = buffer.readString();
	}

	@Override
	public void encode(BitBuffer buffer) {
		buffer.writeString(metadata);
	}
	
	@Override
	public String toString() {
		return "Metadata [metadata=" + metadata + "]";
	}

}
