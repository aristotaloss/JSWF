package com.bartpelle.jswf.tag;

import java.util.Date;

import com.bartpelle.jswf.BitBuffer;

public class ProductInfo extends Tag {

	private int id;
	private int edition;
	private int major;
	private int minor;
	private int build_h;
	private int build_l;
	private long timestamp;

	@Override
	public void decode(BitBuffer buffer, int len) {
		id = buffer.readIntLE();
		edition = buffer.readIntLE();
		major = buffer.readByte();
		minor = buffer.readByte();
		build_l = buffer.readIntLE();
		build_h = buffer.readIntLE();
		
		long l1 = buffer.readIntLE() & 0xFFFFFFFFL;
		long l2 = (long) buffer.readIntLE() << 32L;
		timestamp = l1 | l2;
	}
	
	@Override
	public void encode(BitBuffer buffer) {
		buffer.writeIntLE(id);
		buffer.writeIntLE(edition);
		buffer.writeByte(major);
		buffer.writeByte(minor);
		buffer.writeIntLE(build_l);
		buffer.writeIntLE(build_h);
		buffer.writeIntLE((int) timestamp);
		buffer.writeIntLE((int) (timestamp >> 32L));
	}
	
	@Override
	public String toString() {
		return "ProductInfo [id=" + id + ", edition=" + edition + ", major=" + major + ", minor=" + minor + ", build_h=" + build_h + ", build_l=" + build_l + ", timestamp=" + new Date(timestamp) + "]";
	}
	
}
