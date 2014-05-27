package com.bartpelle.jswf.tag;

import com.bartpelle.jswf.BitBuffer;

public class FileAttributes extends Tag {

	private boolean use_direct_blit;
	private boolean use_gpu;
	private boolean has_metadata;
	private boolean actionscript_3;
	private boolean use_network;
	private boolean suppress_cross_domain_caching;
	private boolean swf_relative_urls;

	@Override
	public void decode(BitBuffer buffer, int len) {
		buffer.startBitMode();

		int reserved = buffer.readBit();

		use_direct_blit = buffer.readBitBool();
		use_gpu = buffer.readBitBool();
		has_metadata = buffer.readBitBool();
		actionscript_3 = buffer.readBitBool();

		/*
		 * The two bits below are /not/ in the official documentation
		 * but an official application from Adobe to inspect SWF files
		 * had this included.
		 */
		suppress_cross_domain_caching = buffer.readBitBool();
		swf_relative_urls = buffer.readBitBool();

		use_network = buffer.readBitBool();

		int reserved__ = buffer.readBits(24);

		buffer.endBitMode();
	}
	
	@Override
	public void encode(BitBuffer buffer) {
		int flag = 0;
		if (use_direct_blit)
			flag |= 1 << 6;
		if (use_gpu)
			flag |= 1 << 5;
		if (has_metadata)
			flag |= 1 << 4;
		if (actionscript_3)
			flag |= 1 << 3;
		if (suppress_cross_domain_caching)
			flag |= 1 << 2;
		if (swf_relative_urls)
			flag |= 1 << 1;
		if (use_network)
			flag |= 1;
		
		buffer.writeIntLE(flag);
	}

	@Override
	public String toString() {
		return "FileAttributes [use_direct_blit=" + use_direct_blit + ", use_gpu=" + use_gpu + ", has_metadata=" + has_metadata + ", actionscript_3=" + actionscript_3 + ", use_network=" + use_network + "]";
	}

}
