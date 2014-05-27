package com.bartpelle.jswf.as.multiname;

import com.bartpelle.jswf.BitBuffer;

public class MultinameL implements IMultiname {

	int ns_set;

	@Override
	public void decode(BitBuffer buffer) {
		ns_set = (int) buffer.readUVarInt();
	}

}
