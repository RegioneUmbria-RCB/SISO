package eu.smartpeg.rievazionepresenze.dto.pai;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class ArCsPaiPtiConsuntiDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6361181717385353725L;

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

	public ArCsPaiPtiConsuntiDTO() {
		
	}
	public ArCsPaiPtiConsuntiDTO(Long idPaiPTI, String codRouting) {
		super();
		this.idPaiPTI = idPaiPTI;
		this.codRouting = codRouting;
	}

	public boolean isInviato() {
		if (inviatoEnte != null) {
			return inviatoEnte;
		}
		return false;
	}

	public boolean isErogato() {
		if (flagErogato != null) {
			return flagErogato;
		}
		return false;
	}

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
