package com.bartpelle.jswf.tag;

import com.bartpelle.jswf.BitBuffer;

public class DoABC extends Tag {
	
	private int flags;
	private String name;
	private byte[] data;

	@Override
	public void decode(BitBuffer buffer, int len) {
		flags = buffer.readIntLE();
		name = buffer.readString();
		data = buffer.readBytes(len - 4 - name.length() - 1);
	}
	
	@Override
	public void encode(BitBuffer buffer) {
		buffer.writeIntLE(flags);
		buffer.writeString(name);
		buffer.writeBytes(data);
	}
	
	public int getFlags() {
		return flags;
	}
	
	public String getName()  {
		return name;
	}
	
	public byte[] getData() {
		return data;
	}

	@Override
	public String toString() {
		return "DoABC [flags=" + flags + ", name=" + name + ", data=" + data.length + "b]";
	}

}
