package it.webred.ct.data.access.basic.indice.dto;

import java.io.Serializable;

public class ListaTotale implements Serializable{

	private IndiceSearchCriteria criteria;
	private int startm;
	private int numberRecord;
	
	
	public IndiceSearchCriteria getCriteria() {
		return criteria;
	}
	public void setCriteria(IndiceSearchCriteria criteria) {
		this.criteria = criteria;
	}
	public int getStartm() {
		return startm;
	}
	public void setStartm(int startm) {
		this.startm = startm;
	}
	public int getNumberRecord() {
		return numberRecord;
	}
	public void setNumberRecord(int numberRecord) {
		this.numberRecord = numberRecord;
	}
	
	
	
	
}
