package it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori;

import it.webred.cs.data.model.CsDValutazione;

import java.util.Date;

public class ProvvedimentiMinoriRowBean {
	private Long idRow;
	private Date dataProvv;
	private String tipoProvv;
	private Date scadenzaAdempimento;
	private String nProvvedimento;
	private boolean affidoComune;
	//private CsDValutazione scheda;


	public Long getIdRow() {
		return idRow;
	}

	public void setIdRow(Long idRow) {
		this.idRow = idRow;
	}

	
	public Date getDataProvv() {
		return dataProvv;
	}

	public void setDataProvv(Date dataProvv) {
		this.dataProvv = dataProvv;
	}

	public void setScadenzaAdempimento(Date scadenzaAdempimento) {
		this.scadenzaAdempimento = scadenzaAdempimento;
	}

	public String getTipoProvv() {
		return tipoProvv;
	}

	public void setTipoProvv(String tipoProvv) {
		this.tipoProvv = tipoProvv;
	}

	public boolean isAffidoComune() {
		return affidoComune;
	}

	public void setAffidoComune(boolean affidoComune) {
		this.affidoComune = affidoComune;
	}

	public Date getScadenzaAdempimento() {
		return scadenzaAdempimento;
	}

	public String getnProvvedimento() {
		return nProvvedimento;
	}

	public void setnProvvedimento(String nProvvedimento) {
		this.nProvvedimento = nProvvedimento;
	}

}
