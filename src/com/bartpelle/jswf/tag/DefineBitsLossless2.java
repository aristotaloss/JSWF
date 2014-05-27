package com.bartpelle.jswf.tag;

import com.bartpelle.jswf.BitBuffer;

public class DefineBitsLossless2 extends Tag {

	private int character_id;
	private int bitmap_format;
	private int bitmap_width;
	private int bitmap_height;
	private int bitmap_color_table_size;
	private byte[] zlib_bitmap_data;

	@Override
	public void decode(BitBuffer buffer, int len) {
		character_id = buffer.readShortLE();
		bitmap_format = buffer.readByte();
		bitmap_width = buffer.readShortLE();
		bitmap_height = buffer.readShortLE();

		if (bitmap_format == 3)
			bitmap_color_table_size = buffer.readByte();

		zlib_bitmap_data = buffer.readBytes(len - (bitmap_format == 3 ? 8 : 7));
	}

	@Override
	public void encode(BitBuffer buffer) {
		buffer.writeShortLE(character_id);
		buffer.writeByte(bitmap_format);
		buffer.writeShortLE(bitmap_width);
		buffer.writeShortLE(bitmap_height);
		
		if (bitmap_format == 3)
			buffer.writeByte(bitmap_color_table_size);
		
		buffer.writeBytes(zlib_bitmap_data);
	}

	@Override
	public String toString() {
		return "DefineBitsLossless2 [character_id=" + character_id + ", bitmap_format=" + bitmap_format + ", bitmap_width=" + bitmap_width + ", bitmap_height=" + bitmap_height + ", bitmap_color_table_size=" + bitmap_color_table_size + ", zlib_bitmap_data=" + (zlib_bitmap_data.length) + "b]";
	}

}
