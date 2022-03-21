package it.umbriadigitale.argo.data.cs.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "AR_O_ORG_IMP_EXP")
public class ArOOrgImpExp implements Serializable {

	@Id
	private long id;
	
	@OneToOne
	@JoinColumn(name="ORGANIZZAZIONE_EST_ID")
	private ArOOrganizzazioneEst arOOrganizzazioneEst;
	
	@OneToOne
	@JoinColumn(name="ORGANIZZAZIONE_ID")
	private ArOOrganizzazione arOOrganizzazione;
	
	@Column(name="CODICE_ORG")
	private String codiceOrg;
	
	@Column(name="FLUSSO_TIPO")
	private String flussoTipo;
	
	@Column(name="FLUSSO_VER")
	private String flussoVer;

	public long getId() {
		return id;
	}

	public ArOOrganizzazioneEst getArOOrganizzazioneEst() {
		return arOOrganizzazioneEst;
	}

	public void setArOOrganizzazioneEst(ArOOrganizzazioneEst arOOrganizzazioneEst) {
		this.arOOrganizzazioneEst = arOOrganizzazioneEst;
	}

	public ArOOrganizzazione getArOOrganizzazione() {
		return arOOrganizzazione;
	}

	public void setArOOrganizzazione(ArOOrganizzazione arOOrganizzazione) {
		this.arOOrganizzazione = arOOrganizzazione;
	}

	public String getCodiceOrg() {
		return codiceOrg;
	}

	public void setCodiceOrg(String codiceOrg) {
		this.codiceOrg = codiceOrg;
	}

	public String getFlussoTipo() {
		return flussoTipo;
	}

	public void setFlussoTipo(String flussoTipo) {
		this.flussoTipo = flussoTipo;
	}

	public String getFlussoVer() {
		return flussoVer;
	}

	public void setFlussoVer(String flussoVer) {
		this.flussoVer = flussoVer;
	}

	public void setId(long id) {
		this.id = id;
	}
}
