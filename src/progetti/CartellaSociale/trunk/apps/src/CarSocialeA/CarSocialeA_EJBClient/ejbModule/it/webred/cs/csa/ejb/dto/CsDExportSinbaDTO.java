package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsDSinba;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.Date;
import java.util.Set;

public class CsDExportSinbaDTO  extends CeTBaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	private Long id;
	private String nomeFile;
	private Date dtIns;
	private Set<CsDSinba> csDSinbas;
	private String enteErogatore;
	private String denominazEnte;
	
	private String indirizzoEnte;
	private String cfOperatore;
	private String flusso;
	private Date dataInvio;
	private Long numProgressivo;
	private Date dtMod;
	private String identificazioneFlusso;
	
	private String xml;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Set<CsDSinba> getCsDSinbas() {
		return csDSinbas;
	}

	public void setCsDSinbas(Set<CsDSinba> csDSinbas) {
		this.csDSinbas = csDSinbas;
	}

	public String getEnteErogatore() {
		return enteErogatore;
	}

	public void setEnteErogatore(String enteErogatore) {
		this.enteErogatore = enteErogatore;
	}

	public String getDenominazEnte() {
		return denominazEnte;
	}

	public void setDenominazEnte(String denominazEnte) {
		this.denominazEnte = denominazEnte;
	}

	public String getIndirizzoEnte() {
		return indirizzoEnte;
	}

	public void setIndirizzoEnte(String indirizzoEnte) {
		this.indirizzoEnte = indirizzoEnte;
	}

	public String getCfOperatore() {
		return cfOperatore;
	}

	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}

	public String getFlusso() {
		return flusso;
	}

	public void setFlusso(String flusso) {
		this.flusso = flusso;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public Long getNumProgressivo() {
		return numProgressivo;
	}

	public void setNumProgressivo(Long numProgressivo) {
		this.numProgressivo = numProgressivo;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public String getIdentificazioneFlusso() {
		return identificazioneFlusso;
	}

	public void setIdentificazioneFlusso(String identificazioneFlusso) {
		this.identificazioneFlusso = identificazioneFlusso;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}
	
	
	
	
	
}
