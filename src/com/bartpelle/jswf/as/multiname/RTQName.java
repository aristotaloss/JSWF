package com.bartpelle.jswf.as.multiname;

import com.bartpelle.jswf.BitBuffer;

public class RTQName implements IMultiname {
	
	private int name;

	@Override
	public void decode(BitBuffer buffer) {
		name = (int) buffer.readUVarInt();
	}

}
