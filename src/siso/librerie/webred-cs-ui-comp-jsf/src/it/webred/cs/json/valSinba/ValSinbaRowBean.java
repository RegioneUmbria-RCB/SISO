package it.webred.cs.json.valSinba;

import it.webred.cs.data.model.CsDSinba;

import java.util.Date;

public class ValSinbaRowBean {

	private Long idRow;
	
	private Date dataExport;
	private Date dataValutazione;
	private Date dtModifica;
	private String opModifica;
	private String identificazioneFlusso;
	private CsDSinba scheda;
	
	public Long getIdRow() {
		return idRow;
	}
	public void setIdRow(Long idRow) {
		this.idRow = idRow;
	}
	public Date getDataExport() {
		return dataExport;
	}
	public void setDataExport(Date dataExport) {
		this.dataExport = dataExport;
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
	public CsDSinba getScheda() {
		return scheda;
	}
	public void setScheda(CsDSinba scheda) {
		this.scheda = scheda;
	}
	public String getIdentificazioneFlusso() {
		return identificazioneFlusso;
	}
	public void setIdentificazioneFlusso(String identificazioneFlusso) {
		this.identificazioneFlusso = identificazioneFlusso;
	}
	public Date getDataValutazione() {
		return dataValutazione;
	}
	public void setDataValutazione(Date dataValutazione) {
		this.dataValutazione = dataValutazione;
	}
	
}
