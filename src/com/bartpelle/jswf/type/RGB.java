package com.bartpelle.jswf.type;

import com.bartpelle.jswf.BitBuffer;

public class RGB {
	
	private int red;
	private int green;
	private int blue;
	
	public RGB(BitBuffer buffer) {
		red = buffer.readByte() & 0xFF;
		green = buffer.readByte() & 0xFF;
		blue = buffer.readByte() & 0xFF;
	}
	
	public void encode(BitBuffer buffer) {
		buffer.writeByte(red);
		buffer.writeByte(green);
		buffer.writeByte(blue);
	}

	@Override
	public String toString() {
		return "RGB [red=" + red + ", green=" + green + ", blue=" + blue + "]";
	}

}
