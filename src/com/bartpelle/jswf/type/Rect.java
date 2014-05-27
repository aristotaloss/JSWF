package com.bartpelle.jswf.type;

import com.bartpelle.jswf.BitBuffer;

public class Rect {
	
	private int x_min;
	private int x_max;
	private int y_min;
	private int y_max;
	
	public Rect(BitBuffer bits) {
		bits.startBitMode();
		
		int rect_bit_num = bits.readBits(5);
		x_min = bits.readBits(rect_bit_num);
		x_max = bits.readBits(rect_bit_num);
		y_min = bits.readBits(rect_bit_num);
		y_max = bits.readBits(rect_bit_num);
		
		bits.endBitMode();
	}
	
	public void encode(BitBuffer buffer) {
		buffer.startBitMode();
		
		int highest = Math.max(Math.max(x_min, x_max), Math.max(y_min, y_max));
		int bitcount = Integer.toBinaryString(highest).length() + 1;
		
		buffer.writeBits(5, bitcount);
		buffer.writeBits(bitcount, x_min);
		buffer.writeBits(bitcount, x_max);
		buffer.writeBits(bitcount, y_min);
		buffer.writeBits(bitcount, y_max);
		
		buffer.endBitMode();
	}

	@Override
	public String toString() {
		return "SWFRect [x_min=" + x_min + ", x_max=" + x_max + ", y_min=" + y_min + ", y_max=" + y_max + "]";
	}
	
}
