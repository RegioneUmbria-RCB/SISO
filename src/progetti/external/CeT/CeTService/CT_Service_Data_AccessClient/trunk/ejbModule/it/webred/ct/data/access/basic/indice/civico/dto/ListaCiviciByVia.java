package it.webred.ct.data.access.basic.indice.civico.dto;

import java.io.Serializable;

public class ListaCiviciByVia implements Serializable{
	
	private int start;
	private int rowNumber;
	private String id;
	
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
