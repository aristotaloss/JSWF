package com.bartpelle.jswf.as;

import com.bartpelle.jswf.BitBuffer;
import com.bartpelle.jswf.as.multiname.IMultiname;

import java.util.Arrays;

public class ConstantPool {

	private int[] integers;
	private long[] uintegers;
	private double[] doubles;
	private String[] strings;
	private NamespaceInfo[] namespaces;
	private int[][] namespace_sets;
	private IMultiname[] multinames;

	public ConstantPool(BitBuffer buffer) {
		integers = new int[(int) buffer.readUVarInt()];

		if (integers.length > 0) {
			integers[0] = 0;
			for (int i = 1; i < integers.length; i++) {
				integers[i] = (int) buffer.readUVarInt();
			}
		}

		System.out.println(Arrays.toString(integers));

		uintegers = new long[(int) buffer.readUVarInt()];
		if (uintegers.length > 0) {
			uintegers[0] = 0;
			for (int i = 1; i < uintegers.length; i++) {
				uintegers[i] = buffer.readUVarInt();
			}
		}

		System.out.println(Arrays.toString(uintegers));

		doubles = new double[(int) buffer.readUVarInt()];
		if (doubles.length > 0) {
			doubles[0] = 0;
			for (int i = 1; i < doubles.length; i++) {
				doubles[i] = buffer.readDouble();
			}
		}

		System.out.println(Arrays.toString(doubles));

		strings = new String[(int) buffer.readUVarInt()];
		if (strings.length > 0) {
			strings[0] = "";
			for (int i = 1; i < strings.length; i++) {
				strings[i] = new String(buffer.readBytes((int) buffer.readUVarInt()));
			}
		}

		System.out.println(Arrays.toString(strings));

		namespaces = new NamespaceInfo[(int) buffer.readUVarInt()];
		if (namespaces.length > 0) {
			namespaces[0] = new NamespaceInfo(NamespaceKind.NAMESPACE, 0);

			for (int i = 1; i < namespaces.length; i++) {
				namespaces[i] = new NamespaceInfo(NamespaceKind.forValue(buffer.readByte() & 0xFF), (int) buffer.readUVarInt());
			}
		}

		System.out.println(Arrays.toString(namespaces));

		namespace_sets = new int[(int) buffer.readUVarInt()][];
		if (namespace_sets.length > 0) {
			namespace_sets[0] = new int[0];

			for (int i = 1; i < namespace_sets.length; i++) {
				int sz = (int) buffer.readUVarInt();
				namespace_sets[i] = new int[sz];

				for (int ii = 0; ii < sz; ii++) {
					namespace_sets[i][ii] = (int) buffer.readUVarInt();
				}
				//TODO: verify no entry == 0
			}
		}

		multinames = new IMultiname[(int) buffer.readUVarInt()];
		if (multinames.length > 0) {
			multinames[0] = null; // TODO makes something outta this

			for (int i = 1; i < multinames.length; i++) {
				int type = buffer.readByte() & 0xFF;
				MultinameKind kind = MultinameKind.forValue(type);

				IMultiname iMultiname = kind.getObject();
				iMultiname.decode(buffer);

				multinames[i] = iMultiname;
			}
		}
	}

}
