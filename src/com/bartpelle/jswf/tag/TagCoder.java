package com.bartpelle.jswf.tag;

import java.util.HashMap;

import com.bartpelle.jswf.BitBuffer;

public class TagCoder {
	
	private static HashMap<Integer, Class<? extends Tag>> TAG_TABLE = new HashMap<>();
	
	public static Tag decodeTag(BitBuffer data) {
		RecordHeader header = new RecordHeader();
		header.decode(data, 0);
		
		int pos = data.position();
		
		if (TAG_TABLE.containsKey(header.getTagType())) {
			Class<? extends Tag> c = TAG_TABLE.get(header.getTagType());
			
			try {
				Tag tag = c.newInstance();
				tag.decode(data, header.getTagLength());
				
				data.position(pos + header.getTagLength());
				return tag;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("missing header tag: " + header.getTagType() + " [len="+header.getTagLength()+"]");
		}
		
		data.position(pos + header.getTagLength());
		return header;
	}
	
	public static void encodeTag(Tag tag, BitBuffer target) {
		BitBuffer buffer = new BitBuffer(new byte[16]);
		tag.encode(buffer);
		
		RecordHeader rh = RecordHeader.create(reverseLookup(tag.getClass()), buffer.position());
		rh.encode(target);
		target.writeBytes(buffer.array(), buffer.position());
	}
	
	private static int reverseLookup(Class<? extends Tag> tagClass) {
		for (int i : TAG_TABLE.keySet()) {
			if (TAG_TABLE.get(i) == tagClass) {
				return i;
			}
		}
		
		return -1;
	}
	
	static {
		TAG_TABLE.put(0, End.class);
		TAG_TABLE.put(1, ShowFrame.class);
		TAG_TABLE.put(9, SetBackgroundColor.class);
		TAG_TABLE.put(14, DefineSound.class);
		TAG_TABLE.put(36, DefineBitsLossless2.class);
		TAG_TABLE.put(39, DefineSprite.class);
		TAG_TABLE.put(41, ProductInfo.class);
		TAG_TABLE.put(43, FrameLabel.class);
		TAG_TABLE.put(65, ScriptLimits.class);
		TAG_TABLE.put(56, ExportAssets.class);
		TAG_TABLE.put(69, FileAttributes.class);
		TAG_TABLE.put(76, SymbolClass.class);
		TAG_TABLE.put(77, Metadata.class);
		TAG_TABLE.put(82, DoABC.class);
		TAG_TABLE.put(87, DefineBinaryData.class);
	}

}
