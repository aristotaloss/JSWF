package com.bartpelle.jswf.tag;

import java.util.LinkedList;

import com.bartpelle.jswf.BitBuffer;

public class DefineSprite extends Tag {

	private int sprite_id;
	private int frame_count;
	private LinkedList<Tag> control_tags;
	
	@Override
	public void decode(BitBuffer buffer, int len) {
		int start_offset = buffer.position();
		
		sprite_id = buffer.readShortLE();
		frame_count = buffer.readShortLE();
		
		control_tags = new LinkedList<Tag>();
		while (buffer.position() < start_offset + len) {
			control_tags.add(TagCoder.decodeTag(buffer));
		}
	}
	
	@Override
	public void encode(BitBuffer buffer) {
		buffer.writeShortLE(sprite_id);
		buffer.writeShortLE(frame_count);
		
		for (Tag t : control_tags) {
			if (t instanceof End)
				continue;
			
			TagCoder.encodeTag(t, buffer);
		}
		
		TagCoder.encodeTag(new End(), buffer);
	}

	@Override
	public String toString() {
		return "DefineSprite [sprite_id=" + sprite_id + ", frame_count=" + frame_count + ", control_tags=" + control_tags + "]";
	}
	
}
