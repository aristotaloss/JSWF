package com.bartpelle.jswf.as.multiname;

import com.bartpelle.jswf.BitBuffer;

public class QName implements IMultiname {
	
	public int ns;
    public int name;

	@Override
	public void decode(BitBuffer buffer) {
		ns = (int) buffer.readUVarInt();
		name = (int) buffer.readUVarInt();
	}

    @Override
    public String toString() {
        return "QName [ns=+" + ns + ", name=" + name + "]";
    }
}
