package it.webred.ct.data.access.basic.imu.dto;

public class XmlFiglioDTO {
	
	private static final long serialVersionUID = 1L;
	
	private boolean presente;
	private String percDetrazione;
	private String mesiDetrazione;
	
	public boolean isPresente() {
		return presente;
	}
	public void setPresente(boolean presente) {
		this.presente = presente;
	}
	public String getPercDetrazione() {
		return percDetrazione;
	}
	public void setPercDetrazione(String percDetrazione) {
		this.percDetrazione = percDetrazione;
	}
	public String getMesiDetrazione() {
		return mesiDetrazione;
	}
	public void setMesiDetrazione(String mesiDetrazione) {
		this.mesiDetrazione = mesiDetrazione;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
