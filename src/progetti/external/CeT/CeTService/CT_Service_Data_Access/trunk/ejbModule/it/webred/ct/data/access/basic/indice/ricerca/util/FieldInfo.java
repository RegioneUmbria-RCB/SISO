package it.webred.ct.data.access.basic.indice.ricerca.util;

import java.io.Serializable;

public class FieldInfo implements Serializable, Comparable<FieldInfo> {

	private String name;
	private String pos;
	private String value;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public int compareTo(FieldInfo fi) {
		
		int tp = Integer.valueOf(pos);
		int fip = Integer.valueOf(fi.pos);
		
		if (tp == fip)
			return 0;
		else if (tp > fip)
			return 1;
		else 
			return -1;
		
	}
	
	
}
