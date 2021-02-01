package it.webred.ct.config.parameters.dto;

import java.io.Serializable;

public class SelectObjDTO implements Serializable{

	private Object oggetto;
	private boolean check;
	private Object inDb;
	
	public Object getOggetto() {
		return oggetto;
	}
	public void setOggetto(Object oggetto) {
		this.oggetto = oggetto;
	}
	
	public boolean isCheck() {
		return check;
	}
	
	public void setCheck(boolean check) {
		this.check = check;
	}
	
	public Object getInDb() {
		return inDb;
	}
	
	public void setInDb(Object inDb) {
		this.inDb = inDb;
	}
	
}
