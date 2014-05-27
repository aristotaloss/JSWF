package com.bartpelle.jswf.as.multiname;

import com.bartpelle.jswf.BitBuffer;

/**
 * Created by Bart on 5/27/2014.
 */
public class TypeSet implements IMultiname {

	public int name;
	public int[] types;

	@Override
	public void decode(BitBuffer buffer) {
		name = (int) buffer.readUVarInt();

		types = new int[(int) buffer.readUVarInt()];
		for (int i=0; i<types.length; i++) {
			types[i] = (int) buffer.readUVarInt();
		}
	}

}
