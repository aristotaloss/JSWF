package com.bartpelle.jswf.tag;

import com.bartpelle.jswf.BitBuffer;

public class RecordHeader extends Tag {

	private int tag_type;
	private int tag_len;

	@Override
	public void decode(BitBuffer buffer, int len) {
		int settings = buffer.readShortLE();

		tag_type = settings >> 6;
		tag_len = settings & 0x3F;

		if (tag_len == 0x3F) {
			tag_len = buffer.readIntLE();
		}
	}
	
	@Override
	public void encode(BitBuffer buffer) {
		int settings = (tag_type << 6) | (tag_len >= 0x3F ? 0x3F : tag_len);
		buffer.writeShortLE(settings);
		
		if (tag_len >= 0x3F) {
			buffer.writeIntLE(tag_len);
		}
	}

	public int getTagType() {
		return tag_type;
	}

	public int getTagLength() {
		return tag_len;
	}

	@Override
	public String toString() {
		return "RecordHeader [tag_type=" + tag_type + ", tag_len=" + tag_len + "]";
	}
	
	public static RecordHeader create(int type, int len) {
		RecordHeader rh = new RecordHeader();
		rh.tag_len = len;
		rh.tag_type = type;
		return rh;
	}

}
