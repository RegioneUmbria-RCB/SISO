package it.webred.ct.data.access.basic.indice.dto;

import java.io.Serializable;

public class SitOperationDTO implements Serializable{

	private boolean check;
	private String ctrHash;
	private boolean validato;
	private String stato; 
	private String label;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public String getCtrHash() {
		return ctrHash;
	}
	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}
	public boolean isValidato() {
		return validato;
	}
	public void setValidato(boolean validato) {
		this.validato = validato;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	
}
