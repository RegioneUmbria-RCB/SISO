package it.webred.ct.data.access.basic.catasto.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.Date;
import java.util.List;

/*Oggetto restituito dalla lista di ricerca*/

public class SintesiImmobileDTO extends CeTBaseObject {
	
	private static final long serialVersionUID = 1L;
	private String idImmobile;
	private String comune;
	private String foglio;
	private String particella;
	private String unimm;
	private String codCategoria;
	private String descCategoria;
	private Date dataInizioVal;
	private Date dataFineVal;
	private Boolean nonANorma;
	
	private List<IndirizzoDTO> indirizzi;
	
	private List<SoggettoDTO> soggetti;
	

	public String getIdImmobile() {
		return idImmobile;
	}
	
	public void setIdImmobile(String idImmobile) {
		this.idImmobile = idImmobile;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
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
	public String getUnimm() {
		return unimm;
	}
	public void setUnimm(String unimm) {
		this.unimm = unimm;
	}
	public String getCodCategoria() {
		return codCategoria;
	}
	public void setCodCategoria(String codCategoria) {
		this.codCategoria = codCategoria;
	}
	public String getDescCategoria() {
		return descCategoria;
	}
	public void setDescCategoria(String descCategoria) {
		this.descCategoria = descCategoria;
	}
	
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

	public List<IndirizzoDTO> getIndirizzi() {
		return indirizzi;
	}

	public void setIndirizzi(List<IndirizzoDTO> indirizzi) {
		this.indirizzi = indirizzi;
	}

	public List<SoggettoDTO> getSoggetti() {
		return soggetti;
	}

	public void setSoggetti(List<SoggettoDTO> soggetti) {
		this.soggetti = soggetti;
	}

	public void setNonANorma(Boolean nonANorma) {
		this.nonANorma = nonANorma;
	}

	public Boolean getNonANorma() {
		return nonANorma;
	}

	

}
