package com.bartpelle.jswf.type;

import com.bartpelle.jswf.BitBuffer;

public class ColorTransform {
	
	private int red_mult;
	private int green_mult;
	private int blue_mult;
	
	private int red_add;
	private int green_add;
	private int blue_add;
	
	public ColorTransform(BitBuffer bits) {
		bits.startBitMode();
		
		boolean has_add = bits.readBitBool();
		boolean has_mult = bits.readBitBool();
		int num_bits = bits.readBits(4);
		
		if (has_mult) {//TODO signed
			red_mult = bits.readBits(num_bits);
			green_mult = bits.readBits(num_bits);
			blue_mult = bits.readBits(num_bits);
		}
		
		if (has_add) {//TODO signed
			red_add = bits.readBits(num_bits);
			green_add = bits.readBits(num_bits);
			blue_add = bits.readBits(num_bits);
		}
		
		bits.endBitMode();
	}

	@Override
	public String toString() {
		return "ColorTransform [red_mult=" + red_mult + ", green_mult=" + green_mult + ", blue_mult=" + blue_mult + ", red_add=" + red_add + ", green_add=" + green_add + ", blue_add=" + blue_add + "]";
	}
	
}
