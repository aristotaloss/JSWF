package com.bartpelle.jswf.as;

import com.bartpelle.jswf.as.multiname.*;

public enum MultinameKind {
	
	QNAME(0x7),
	QNAME_A(0xD),
	RTQNAME(0xF),
	RTQNAME_A(0x10),
	RTQNAME_L(0x11),
	RTQNAME_LA(0x12),
	MULTINAME(0x9),
	MULTINAME_A(0xE),
	MULTINAME_L(0x1B),
	MULTINAME_LA(0x1C),
	TYPESET(0x1D);
	
	public int value;
	
	private MultinameKind(int v) {
		value = v;
	}
	
	public static MultinameKind forValue(int v) {
		for (MultinameKind k : values()) {
			if (k.value == v)
				return k;
		}
		
		return null;
	}

    public IMultiname getObject() {
        switch (this) {
            case QNAME:
            case QNAME_A:
                return new QName();
            case RTQNAME:
            case RTQNAME_A:
                return new RTQName();
            case RTQNAME_L:
            case RTQNAME_LA:
                return new RTQNameL();
            case MULTINAME:
            case MULTINAME_A:
                return new Multiname();
            case MULTINAME_L:
            case MULTINAME_LA:
                return new MultinameL();
	        case TYPESET:
		        return new TypeSet();
        }

        return null;
    }

}
