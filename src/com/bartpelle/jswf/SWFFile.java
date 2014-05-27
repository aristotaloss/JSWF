package com.bartpelle.jswf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.LinkedList;
import java.util.zip.Inflater;

import com.bartpelle.jswf.as.ActionScript;
import com.bartpelle.jswf.ex.InvalidSWFFileException;
import com.bartpelle.jswf.tag.DoABC;
import com.bartpelle.jswf.tag.End;
import com.bartpelle.jswf.tag.Tag;
import com.bartpelle.jswf.tag.TagCoder;
import com.bartpelle.jswf.type.Rect;
import tests.AbcPrinter;

public class SWFFile {

	private File file;

	private CompressionType compression;
	private int version;
	private int fileLength;
	private Rect dimensions;
	private int framerate;
	private int frames;
	private LinkedList<Tag> tags = new LinkedList<Tag>();

	public SWFFile(File file) {
		this.file = file;

		try {
			decode();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void decode() throws Exception {
		MappedByteBuffer buffer = FileChannel.open(file.toPath()).map(
				MapMode.READ_ONLY, 0, file.length());
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		compression = CompressionType.forChar(buffer.get() & 0xFF);
		System.out.println(compression);

		if (buffer.get() != 'W' || buffer.get() != 'S') {
			throw new InvalidSWFFileException();
		}

		version = buffer.get() & 0xFF;
		fileLength = buffer.getInt();

		byte[] raw = new byte[buffer.remaining()];
		buffer.get(raw);

		if (compression == CompressionType.ZLIB) {
			Inflater i = new Inflater();
			i.setInput(raw);

			byte[] real = new byte[fileLength - 4 - 1 - 3];
			System.out.println("CMP: " + fileLength + " " + raw.length + " " + (real.length));
			i.inflate(real);
			raw = real;
		}
		
		try {
			FileOutputStream fos = new FileOutputStream("ex_"+file.getName());
			fos.write(raw);
			fos.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		BitBuffer swfbuffer = new BitBuffer(raw);

		dimensions = new Rect(swfbuffer);
		framerate = swfbuffer.readShort();
		frames = swfbuffer.readShortLE();

		System.out.println(dimensions + ", " + frames + ", " + framerate);

		boolean ok = false;
		while (swfbuffer.readable()) {
			Tag t = TagCoder.decodeTag(swfbuffer);

			if (t instanceof DoABC) {
				if (!ok) {
					ok = true;
					byte[] abc = ((DoABC) t).getData();
					System.out.println(abc.length);
					ActionScript as = new ActionScript(abc);
					System.out.println(as);
				}
			}

			tags.add(t);
			System.out.println(t);
		}

		System.out.println(swfbuffer.position() + ", " + raw.length + ", "
				+ fileLength);
	}

	public void encode(File out) throws IOException {
		byte[] back = new byte[fileLength];
		BitBuffer outbuffer = new BitBuffer(back);

		dimensions.encode(outbuffer);

		outbuffer.writeByte(0);
		outbuffer.writeByte(framerate);
		outbuffer.writeShortLE(frames);

		for (Tag t : tags) {
			if (t instanceof End) // Disallow end, we write that manually.
				continue;

			TagCoder.encodeTag(t, outbuffer);
		}

		TagCoder.encodeTag(new End(), outbuffer);

		try {
			FileOutputStream fos = new FileOutputStream(out);

			fos.write('F');
			fos.write('W');
			fos.write('S');
			fos.write(version);

			int size = outbuffer.position() + 8;
			fos.write(size);
			fos.write(size >> 8);
			fos.write(size >> 16);
			fos.write(size >> 24);

			fos.write(outbuffer.array(), 0, outbuffer.position());

			fos.flush();
			fos.close();
		} catch (IOException e) {
			throw e;
		}
	}

}
