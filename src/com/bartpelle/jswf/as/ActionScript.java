package com.bartpelle.jswf.as;

import com.bartpelle.jswf.BitBuffer;

public class ActionScript {
	
	private int minor_version;
	private int major_version;
	private ConstantPool cpool;
	
	public ActionScript(byte[] script) {
		BitBuffer buffer = new BitBuffer(script);
		
		minor_version = buffer.readShortLE();
		major_version = buffer.readShortLE();
		cpool = new ConstantPool(buffer);
	}

	@Override
	public String toString() {
		return "ActionScript [minor_version=" + minor_version + ", major_version=" + major_version + "]";
	}

}
