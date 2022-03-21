package it.webred.ct.data.access.basic.catasto.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.util.Date;

public class DataVariazioneImmobileDTO extends CeTBaseObject{
	private static final long serialVersionUID = 1L;
	
	private String idUiu;
	
	private Date dataInizioVal;
	
	private Date dataFineVal;

	public Date getDataInizioVal() {
		return dataInizioVal;
	}

	public void setDataInizioVal(Date dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}

	public Date getDataFineVal() {
		return dataFineVal;
	}

	public void setDataFineVal(Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}

	public void setIdUiu(String idUiu) {
		this.idUiu = idUiu;
	}

	public String getIdUiu() {
		return idUiu;
	}

	

	

}
