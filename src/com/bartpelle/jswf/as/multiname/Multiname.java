package com.bartpelle.jswf.as.multiname;

import com.bartpelle.jswf.BitBuffer;

public class Multiname implements IMultiname {
	
	int name;
	int ns_set;

	@Override
	public void decode(BitBuffer buffer) {
		name = (int) buffer.readUVarInt();
		ns_set = (int) buffer.readUVarInt();
	}

}
