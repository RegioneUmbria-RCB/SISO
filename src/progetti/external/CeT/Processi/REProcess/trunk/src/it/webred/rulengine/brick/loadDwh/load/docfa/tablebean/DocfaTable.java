package it.webred.rulengine.brick.loadDwh.load.docfa.tablebean;

import java.io.Serializable;
import java.util.Date;

public class DocfaTable implements Serializable {
	
	private Date reDataInizioVal;
	private Date reDataFineVal;
	private Long reFlagElaborato;
	
	public DocfaTable(Date reDataInizioVal, Date reDataFineVal,
			Long reFlagElaborato) {
		
		super();
		
		this.reDataInizioVal = reDataInizioVal;
		this.reDataFineVal = reDataFineVal;
		this.reFlagElaborato = reFlagElaborato;
	}

	public Date getReDataInizioVal() {
		return reDataInizioVal;
	}

	public void setReDataInizioVal(Date reDataInizioVal) {
		this.reDataInizioVal = reDataInizioVal;
	}

	public Date getReDataFineVal() {
		return reDataFineVal;
	}

	public void setReDataFineVal(Date reDataFineVal) {
		this.reDataFineVal = reDataFineVal;
	}

	public Long getReFlagElaborato() {
		return reFlagElaborato;
	}

	public void setReFlagElaborato(Long reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}
	
	
}
