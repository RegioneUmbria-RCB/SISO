package it.webred.ct.data.access.basic.cartellasociale.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InterventoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String descrizione;
	private Date dtInizioVal;
	private Date dtFineVal;
	private String dtInizioValStr;
	private String dtFineValStr;
	private BigDecimal importoErogato;
	private String importoErogatoStr;
	private String descComuneErogante;
	public String getDescrizione() {
		return descrizione;
	}
	public Date getDtInizioVal() {
		return dtInizioVal;
	}
	public Date getDtFineVal() {
		return dtFineVal;
	}
	public String getDtInizioValStr() {
		return dtInizioValStr;
	}
	public void setDtInizioValStr(String dtInizioValStr) {
		this.dtInizioValStr = dtInizioValStr;
	}
	public String getDtFineValStr() {
		return dtFineValStr;
	}
	public void setDtFineValStr(String dtFineValStr) {
		this.dtFineValStr = dtFineValStr;
	}
	public BigDecimal getImportoErogato() {
		return importoErogato;
	}
	public String getDescComuneErogante() {
		return descComuneErogante;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public void setDtInizioVal(Date dtInizioVal) {
		this.dtInizioVal = dtInizioVal;
	}
	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}
	public void setImportoErogato(BigDecimal importoErogato) {
		this.importoErogato = importoErogato;
	}
	public void setDescComuneErogante(String descComuneErogante) {
		this.descComuneErogante = descComuneErogante;
	}
	public String getImportoErogatoStr() {
		return importoErogatoStr;
	}
	public void setImportoErogatoStr(String importoErogatoStr) {
		this.importoErogatoStr = importoErogatoStr;
	}
		
}
