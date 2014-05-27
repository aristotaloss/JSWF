package com.bartpelle.jswf.tag;

import com.bartpelle.jswf.BitBuffer;

public class ScriptLimits extends Tag {

	private int max_recursion_depth;
	private int script_timeout_seconds;

	@Override
	public void decode(BitBuffer buffer, int len) {
		max_recursion_depth = buffer.readShortLE();
		script_timeout_seconds = buffer.readShortLE();
	}
	
	@Override
	public void encode(BitBuffer buffer) {
		buffer.writeShortLE(max_recursion_depth);
		buffer.writeShortLE(script_timeout_seconds);
	}
	
	@Override
	public String toString() {
		return "ScriptLimits [max_recursion_depth=" + max_recursion_depth + ", script_timeout_seconds=" + script_timeout_seconds + "]";
	}

}
