package com.bartpelle.jswf.type;

import com.bartpelle.jswf.BitBuffer;

public class Matrix {
	
	private int scale_x;
	private int scale_y;
	private int rotate_skew_0;
	private int rotate_skew_1;
	private int translate_x;
	private int translate_y;
	
	public Matrix(BitBuffer bits) {
		bits.startBitMode();
		
		boolean has_scale = bits.readBitBool();
		if (has_scale) {
			int scale_bits = bits.readBits(5);
			scale_x = bits.readBits(scale_bits);
			scale_y = bits.readBits(scale_bits);
		}
		
		boolean has_rotate = bits.readBitBool();
		if (has_rotate) {
			int rotate_bits = bits.readBits(5);
			rotate_skew_0 = bits.readBits(rotate_bits);
			rotate_skew_1 = bits.readBits(rotate_bits);
		}

		int translate_bits = bits.readBits(5);
		translate_x = bits.readBits(translate_bits);
		translate_y = bits.readBits(translate_bits);
		
		bits.endBitMode();
	}

	@Override
	public String toString() {
		return "Matrix [scale_x=" + scale_x + ", scale_y=" + scale_y + ", rotate_skew_0=" + rotate_skew_0 + ", rotate_skew_1=" + rotate_skew_1 + ", translate_x=" + translate_x + ", translate_y=" + translate_y + "]";
	}
	
}
