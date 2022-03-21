package it.webred.ct.data.access.basic.docfa.dto;

import java.io.Serializable;

public class FoglioMicrozonaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String oldMicrozona;
	private String newMicrozona;
	private String zc;
	public String getOldMicrozona() {
		return oldMicrozona;
	}
	public void setOldMicrozona(String oldMicrozona) {
		this.oldMicrozona = oldMicrozona;
	}
	public String getNewMicrozona() {
		return newMicrozona;
	}
	public void setNewMicrozona(String newMicrozona) {
		this.newMicrozona = newMicrozona;
	}
	public String getZc() {
		return zc;
	}
	public void setZc(String zc) {
		this.zc = zc;
	}
	
	
	public String print(){
		return "Zona:["+zc+"];OldMicrozona["+oldMicrozona+"];NewMicrozona["+newMicrozona+"]";
	}
	

}
