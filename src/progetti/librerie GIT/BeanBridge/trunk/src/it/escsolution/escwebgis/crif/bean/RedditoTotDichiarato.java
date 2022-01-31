package it.escsolution.escwebgis.crif.bean;

import it.escsolution.escwebgis.anagrafe.bean.Anagrafe;
import it.escsolution.escwebgis.common.EscObject;
import it.escsolution.escwebgis.redditiAnnuali.bean.RedditiAnnuali;
import it.escsolution.escwebgis.toponomastica.bean.Civico;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class RedditoTotDichiarato extends EscObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String codModello;
	private String ideTelematico;
	private String codFiscale;
	private String anno;
	private Double importoComplessivo;
	private Boolean importoNonDisponibile;
		
	public String getCodModello() {
		return codModello;
	}
	public void setCodModello(String codModello) {
		this.codModello = codModello;
	}
	public String getIdeTelematico() {
		return ideTelematico;
	}
	public void setIdeTelematico(String ideTelematico) {
		this.ideTelematico = ideTelematico;
	}
	public String getCodFiscale() {
		return codFiscale;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public Double getImportoComplessivo() {
		return importoComplessivo;
	}
	public void setImportoComplessivo(Double importoComplessivo) {
		this.importoComplessivo = importoComplessivo;
		if(importoComplessivo==null)
			setImportoNonDisponibile(true);
		else
			setImportoNonDisponibile(false);
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Boolean getImportoNonDisponibile() {
		return importoNonDisponibile;
	}
	public void setImportoNonDisponibile(Boolean importoNonDisponibile) {
		this.importoNonDisponibile = importoNonDisponibile;
	}
	
	
}
