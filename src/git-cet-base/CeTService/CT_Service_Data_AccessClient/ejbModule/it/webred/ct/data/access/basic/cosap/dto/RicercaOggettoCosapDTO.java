package it.webred.ct.data.access.basic.cosap.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class RicercaOggettoCosapDTO extends CeTBaseObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String foglio;
	private String particella;
	
	private String sub;
	private String codiceVia;
	private String civico;
	
	private List<String> listaId;
    private Date dataRif;
    
    private String numeroDocumento;
    private String annoDocumento;
    private String codiceImmobile;
    private String tipoOccupazione;
    private Date dtIniValidita;
    private Date dtFineValidita;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getCodiceVia() {
		return codiceVia;
	}
	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public List<String> getListaId() {
		return listaId;
	}
	public void setListaId(List<String> listaId) {
		this.listaId = listaId;
	}
	public Date getDataRif() {
		return dataRif;
	}
	public void setDataRif(Date dataRif) {
		this.dataRif = dataRif;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getAnnoDocumento() {
		return annoDocumento;
	}
	public void setAnnoDocumento(String annoDocumento) {
		this.annoDocumento = annoDocumento;
	}
	public String getCodiceImmobile() {
		return codiceImmobile;
	}
	public void setCodiceImmobile(String codiceImmobile) {
		this.codiceImmobile = codiceImmobile;
	}
	public String getTipoOccupazione() {
		return tipoOccupazione;
	}
	public void setTipoOccupazione(String tipoOccupazione) {
		this.tipoOccupazione = tipoOccupazione;
	}
	public Date getDtIniValidita() {
		return dtIniValidita;
	}
	public void setDtIniValidita(Date dtIniValidita) {
		this.dtIniValidita = dtIniValidita;
	}
	public Date getDtFineValidita() {
		return dtFineValidita;
	}
	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}
	

}
