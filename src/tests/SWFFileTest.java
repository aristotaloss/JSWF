package tests;

import java.io.File;
import java.io.IOException;

import com.bartpelle.jswf.BitBuffer;
import com.bartpelle.jswf.SWFFile;

public class SWFFileTest {

	public static void main(String[] args) {
		BitBuffer bb = new BitBuffer(new byte[] {(byte) 0b1010_0010, (byte) 0b1110_1010, 12});
		bb.startBitMode();
		System.out.println("4 -> "+bb.readBits(4));
		System.out.println("6 -> "+bb.readBits(6));
		bb.endBitMode();
		System.out.println(bb.readByte());
		
		try {
			File f = new File("how.swf");
			SWFFile swf = new SWFFile(f);
			swf.encode(new File("encoded.swf"));
			//swf = new SWFFile(new File("encoded.swf"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//0000 0010 0100 0011
		bb = new BitBuffer(new byte[] {(byte) 0b00000010, (byte) 0b01000011});
		bb.startBitMode();
	}

}
