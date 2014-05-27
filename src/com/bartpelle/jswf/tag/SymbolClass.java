package com.bartpelle.jswf.tag;

import java.util.LinkedList;

import com.bartpelle.jswf.BitBuffer;
import com.bartpelle.jswf.type.TagNamePair;

public class SymbolClass extends Tag {
	
	private LinkedList<TagNamePair> symbols = new LinkedList<TagNamePair>();

	@Override
	public void decode(BitBuffer buffer, int len) {
		int num_symbols = buffer.readShortLE();
		
		for (int i=0; i<num_symbols; i++) {
			symbols.add(new TagNamePair(buffer.readShortLE(), buffer.readString()));
		}
	}
	
	@Override
	public void encode(BitBuffer buffer) {
		buffer.writeShortLE(symbols.size());
		
		for (TagNamePair tnp : symbols) {
			buffer.writeShortLE(tnp.tag);
			buffer.writeString(tnp.name);
		}
	}

	@Override
	public String toString() {
		return "SymbolClass [symbols=" + symbols + "]";
	}

}
