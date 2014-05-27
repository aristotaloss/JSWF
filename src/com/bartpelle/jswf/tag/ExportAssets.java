package com.bartpelle.jswf.tag;

import java.util.LinkedList;

import com.bartpelle.jswf.BitBuffer;
import com.bartpelle.jswf.type.TagNamePair;

public class ExportAssets extends Tag {
	
	private LinkedList<TagNamePair> assets = new LinkedList<TagNamePair>();

	@Override
	public void decode(BitBuffer buffer, int len) {
		int count = buffer.readShortLE();
		
		for (int i=0; i<count; i++) {
			assets.add(new TagNamePair(buffer.readShortLE(), buffer.readString()));
		}
	}
	
	@Override
	public void encode(BitBuffer buffer) {
		buffer.writeShortLE(assets.size());
		
		for (TagNamePair tnp : assets) {
			buffer.writeShortLE(tnp.tag);
			buffer.writeString(tnp.name);
		}
	}
	
	@Override
	public String toString() {
		return "ExportAssets [assets=" + assets + "]";
	}

}
