package it.webred.ct.data.access.basic.pgt.dto;

import java.io.Serializable;

import it.webred.ct.data.model.pgt.PgtSqlDecoLayer;
import it.webred.ct.data.model.pgt.PgtSqlLayer;

public class TemaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private PgtSqlLayer pgtSqlLayer;
	private PgtSqlDecoLayer pgtSqlDecoLayer;
	private String occorrenze;
	
	public PgtSqlLayer getPgtSqlLayer() {
		return pgtSqlLayer;
	}
	
	public void setPgtSqlLayer(PgtSqlLayer pgtSqlLayer) {
		this.pgtSqlLayer = pgtSqlLayer;
	}
	
	public PgtSqlDecoLayer getPgtSqlDecoLayer() {
		return pgtSqlDecoLayer;
	}
	
	public void setPgtSqlDecoLayer(PgtSqlDecoLayer pgtSqlDecoLayer) {
		this.pgtSqlDecoLayer = pgtSqlDecoLayer;
	}
	
	public String getOccorrenze() {
		return occorrenze;
	}
	
	public void setOccorrenze(String occorrenze) {
		this.occorrenze = occorrenze;
	}
	
}
