package it.webred.cs.csa.ejb.dto.pai.pti;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.Date;


public class InserimentoConsuntivazioneDTO extends CeTBaseObject{

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long idPaiPTI;

	private String codRouting;
	
	private Date dataDa;
	
	private Date dataA;
	
	private Long numGiorni;

	private Long tariffa;
	
	private Boolean inviatoEnte;
	
	private Boolean flagErogato;
	
	private Boolean flagNotifica;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPaiPTI() {
		return idPaiPTI;
	}

	public void setIdPaiPTI(Long idPaiPTI) {
		this.idPaiPTI = idPaiPTI;
	}

	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}

	public Date getDataDa() {
		return dataDa;
	}

	public void setDataDa(Date dataDa) {
		this.dataDa = dataDa;
	}

	public Date getDataA() {
		return dataA;
	}

	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

	public Long getNumGiorni() {
		return numGiorni;
	}

	public void setNumGiorni(Long numGiorni) {
		this.numGiorni = numGiorni;
	}

	public Long getTariffa() {
		return tariffa;
	}

	public void setTariffa(Long tariffa) {
		this.tariffa = tariffa;
	}

	public Boolean getInviatoEnte() {
		return inviatoEnte;
	}

	public void setInviatoEnte(Boolean inviatoEnte) {
		this.inviatoEnte = inviatoEnte;
	}

	public Boolean getFlagErogato() {
		return flagErogato;
	}

	public void setFlagErogato(Boolean flagErogato) {
		this.flagErogato = flagErogato;
	}

	public Boolean getFlagNotifica() {
		return flagNotifica;
	}

	public void setFlagNotifica(Boolean flagNotifica) {
		this.flagNotifica = flagNotifica;
	}
	
}
