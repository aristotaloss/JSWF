package com.bartpelle.jswf.tag;

import com.bartpelle.jswf.BitBuffer;

public class DefineSound extends Tag {

	private int sound_id;
	private int sound_format; // TODO enum
	private int sound_rate;
	private int sound_size;
	private int sound_type;
	private int sound_sample_count;
	private byte[] sound_data;

	@Override
	public void decode(BitBuffer buffer, int len) {
		sound_id = buffer.readShortLE();

		buffer.startBitMode();
		sound_format = buffer.readBits(4);
		sound_rate = buffer.readBits(2);
		sound_size = buffer.readBit();
		sound_type = buffer.readBit();
		buffer.endBitMode();

		sound_sample_count = buffer.readIntLE();
		sound_data = buffer.readBytes(len - 2 - 1 - 4);// TODO verify
	}
	
	@Override
	public void encode(BitBuffer buffer) {
		buffer.writeShortLE(sound_id);
		
		int settings = 0;
		settings |= sound_format << 4;
		settings |= sound_rate << 2;
		settings |= sound_size << 1;
		settings |= sound_type;
		
		buffer.writeByte(settings);
		buffer.writeIntLE(sound_sample_count);
		buffer.writeBytes(sound_data);
	}

	@Override
	public String toString() {
		return "DefineSound [sound_id=" + sound_id + ", sound_format=" + sound_format + ", sound_rate=" + sound_rate + ", sound_size=" + sound_size + ", sound_type=" + sound_type + ", sound_sample_count=" + sound_sample_count + ", sound_data=" + (sound_data.length) + "b]";
	}

}
