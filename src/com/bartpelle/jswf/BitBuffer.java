package com.bartpelle.jswf;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BitBuffer {
	
	private byte[] b;
	private int pos;
	private int bitpos = -1;
	
	public BitBuffer(byte[] b) {
		this.b = b;
	}
	
	public void startBitMode() {
		bitpos = 0;
	}
	
	public void endBitMode() {
		if (bitpos <= 8)
			pos++;
		
		bitpos = -1;
	}
	
	public boolean isReadingBits() {
		return bitpos != -1;
	}
	
	public int readBits(int num) {
		int result = 0;
		
		for (int i=0; i<num; i++) {
			if (bitpos == 8) {
				pos++;
				bitpos = 0;
			}
			
			result |= (((b[pos] & 0xFF) >> (8 - bitpos - 1)) & 1) << (num - i - 1);
			bitpos++;
		}
		
		return result;
	}
	
	public void writeBits(int count, int value) {
		for (int i = 0; i < count; i++) {
			if (bitpos == 8) {
				pos++;
				bitpos = 0;
			}
			
			if ((value & (1 << (count - i - 1))) != 0) {
				b[pos] |= (1 << (8 - bitpos - 1));
			}

			bitpos++;
		}
	}
	
	private void ensureCapacity(int to_add) {
		while (pos + to_add >= b.length) {
			byte[] n = new byte[b.length * 2];
			System.arraycopy(b, 0, n, 0, b.length);
			b = n;
		}
	}
	
	public int readBit() {
		return readBits(1);
	}
	
	public boolean readBitBool() {
		return readBit() != 0;
	}
	
	public byte readByte() {
		return b[pos++];
	}
	
	public void writeByte(int bb) {
		ensureCapacity(1);
		b[pos++] = (byte) bb;
	}
	
	public short readShort() {
		return (short) (((b[pos++] & 0xFF) << 8) | (b[pos++] & 0xFF));
	}
	
	public short readShortLE() {
		return (short) (((b[pos++] & 0xFF)) | ((b[pos++] & 0xFF) << 8));
	}
	
	public void writeShortLE(int val) {
		ensureCapacity(2);
		b[pos++] = (byte) val;
		b[pos++] = (byte) (val >> 8);
	}
	
	public int readIntLE() {
		return ((b[pos++] & 0xFF) | ((b[pos++] & 0xFF) << 8) | ((b[pos++] & 0xFF) << 16) | ((b[pos++] & 0xFF) << 24));
	}
	
	public void writeIntLE(int val) {
		ensureCapacity(4);
		b[pos++] = (byte) val;
		b[pos++] = (byte) (val >> 8);
		b[pos++] = (byte) (val >> 16);
		b[pos++] = (byte) (val >> 24);
	}
	
	public long readLongLE() {
		long l1 = b[pos++] & 0xFFL;
		long l2 = b[pos++] & 0xFFL;
		long l3 = b[pos++] & 0xFFL;
		long l4 = b[pos++] & 0xFFL;
		long l5 = b[pos++] & 0xFFL;
		long l6 = b[pos++] & 0xFFL;
		long l7 = b[pos++] & 0xFFL;
		long l8 = b[pos++] & 0xFFL;
		
		long r = (l1) | (l2 << 8L) | (l3 << 16L) | (l4 << 24L) | (l5 << 32L) | (l6 << 40L) | (l7 << 48L) | (l8 << 56L);
		return r;
	}
	
	public double readDouble() {
		return Double.longBitsToDouble(readLongLE());
	}
	
	public String readString() {
		int start = pos;
		while (b[pos++] != 0);
		
		return new String(b, start, pos-start-1);
	}
	
	public void writeString(String s) {
		writeBytes(s.getBytes());
		writeByte(0);
	}
	
	/*public long readUVarInt() {
		int cur = 0;
		int val = 0;
		int i=0;
		
		while (true) {
			cur = readByte() & 0xFF;
			val |= (cur & 0x7F) << (i * 7);
			System.out.println(cur);
			if ((cur & 0x80) == 0) {
				break;
			}
			
			i++;
		}
		
		return val;
	}*/
	
	// From Gnash as I was not sure if mine was proper
	public long readUVarInt() {
		int data = readByte();
		int result = data;
		
		if ((result & 0x00000080) == 0)	return result;

		data = readByte();
		result = (result & 0x0000007F) | data << 7;
		if ((result & 0x00004000) == 0) return result;
		
		data = readByte();
		result = (result & 0x00003FFF) | data << 14;
		if ((result & 0x00200000) == 0) return result;

		data = readByte();
		result = (result & 0x001FFFFF) | data << 21;
		if ((result & 0x10000000) == 0) return result;

		data = readByte();
		return (result & 0x0FFFFFFF) | data << 28;
	}
	
	/*
	 * read(&data,1);
	boost::uint32_t result = data;
	if (!(result & 0x00000080))	return result;

	read(&data,1);
	result = (result & 0x0000007F) | data << 7;
	if (!(result & 0x00004000)) return result;
	
	read(&data,1);
	result = (result & 0x00003FFF) | data << 14;
	if (!(result & 0x00200000)) return result;

	read(&data,1);
	result = (result & 0x001FFFFF) | data << 21;
	if (!(result & 0x10000000)) return result;

	read(&data,1);
	return (result & 0x0FFFFFFF) | data << 28;
	 */
	
	public long readSVarInt() {
		int cur = 0;
		int val = 0;
		int i=0;
		
		while (i < 5) {
			cur = readByte() & 0xFF;
			val |= (cur & 0x7F) << (i * 7);
			
			if ((cur & 0x80) == 0) {
				break;
			}
			
			if (i == 4) {
				if ((cur & 0x80) != 0) {
					val = -val; // Negate it
				}
			}
			
			i++;
		}
		
		return val;
	}
	
	public byte[] readBytes(int amt) {
		byte[] dst = new byte[amt];
		System.arraycopy(b, pos, dst, 0, amt);
		pos += amt;
		return dst;
	}
	
	public void writeBytes(byte[] bs) {
		ensureCapacity(bs.length);
		for (byte bb : bs)
			b[pos++] = bb;
	}
	
	public void writeBytes(byte[] bs, int len) {
		ensureCapacity(len);
		for (int i=0; i<len; i++)
			b[pos++] = bs[i];
	}
	
	public boolean readable() {
		return pos < b.length;
	}

	public int position() {
		return pos;
	}
	
	public byte[] array() {
		return b;
	}
	
	public void position(int pos) {
		this.pos = pos;
	}
	
}
