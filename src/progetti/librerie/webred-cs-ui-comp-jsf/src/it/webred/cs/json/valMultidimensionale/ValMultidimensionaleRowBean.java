package it.webred.cs.json.valMultidimensionale;

import it.webred.cs.data.model.CsDValutazione;

import java.util.Date;

public class ValMultidimensionaleRowBean {
	private Long idRow;
	//TODO:Altri campi
	
	private Date dtModifica;
	private String opModifica;
	private CsDValutazione scheda;
	private String statoCompilazione;
	public Long getIdRow() {
		return idRow;
	}
	public void setIdRow(Long idRow) {
		this.idRow = idRow;
	}
	public Date getDtModifica() {
		return dtModifica;
	}
	public void setDtModifica(Date dtModifica) {
		this.dtModifica = dtModifica;
	}
	public String getOpModifica() {
		return opModifica;
	}
	public void setOpModifica(String opModifica) {
		this.opModifica = opModifica;
	}
	public CsDValutazione getScheda() {
		return scheda;
	}
	public void setScheda(CsDValutazione scheda) {
		this.scheda = scheda;
	}
	public String getStatoCompilazione() {
		return statoCompilazione;
	}
	public void setStatoCompilazione(String statoCompilazione) {
		this.statoCompilazione = statoCompilazione;
	}


}
