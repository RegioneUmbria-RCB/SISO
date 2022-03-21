package it.webred.ct.data.access.basic.pgt.dto;

import java.io.Serializable;

public class InfoCatDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String colonna;
	private String alias;
	private boolean visibile;
	
	public String getColonna() {
		return colonna;
	}
	public void setColonna(String colonna) {
		this.colonna = colonna;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public boolean isVisibile() {
		return visibile;
	}
	public void setVisibile(boolean visibile) {
		this.visibile = visibile;
	}
	
}
