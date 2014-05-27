package com.bartpelle.jswf.tag;

import com.bartpelle.jswf.BitBuffer;

public class DefineBinaryData extends Tag {
	
	private int tag;
	private byte[] data;

	@Override
	public void decode(BitBuffer buffer, int len) {
		tag = buffer.readShortLE();
		buffer.readIntLE(); // reserved, 0
		data = buffer.readBytes(len - 6);
	}
	
	@Override
	public void encode(BitBuffer buffer) {
		buffer.writeShortLE(tag);
		buffer.writeIntLE(0);
		buffer.writeBytes(data);
	}

	@Override
	public String toString() {
		return "DefineBinaryData [tag=" + tag + ", data=" + data.length + "b]";
	}

}
