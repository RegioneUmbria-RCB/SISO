package it.webred.ss.data.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="SS_REL_UFF_PCONT_ORG")
@NamedQuery(name="SsRelUffPcontOrg.findAll", query="SELECT c FROM SsRelUffPcontOrg c order by c.ssOOrganizzazione.nome, c.ssUfficio.nOrd, c.ssUfficio.nome, c.ssPuntoContatto.nome")
public class SsRelUffPcontOrg implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SsRelUffPcontOrgPK id;
	
	private Boolean abilitato;
	
	@ManyToOne
	@JoinColumn(name="UFFICIO_ID",insertable=false, updatable=false)
	private SsUfficio ssUfficio;
	
	@ManyToOne
	@JoinColumn(name="PUNTO_CONTATTO_ID",insertable=false, updatable=false)
	private SsPuntoContatto ssPuntoContatto;
	
	@ManyToOne
	@JoinColumn(name="ORGANIZZAZIONE_ID",insertable=false, updatable=false)
	private SsOOrganizzazione ssOOrganizzazione;
	

	public SsRelUffPcontOrgPK getId() {
		return id;
	}

	public void setId(SsRelUffPcontOrgPK id) {
		this.id = id;
	}

	public SsUfficio getSsUfficio() {
		return ssUfficio;
	}

	public void setSsUfficio(SsUfficio ssUfficio) {
		this.ssUfficio = ssUfficio;
	}

	public SsPuntoContatto getSsPuntoContatto() {
		return ssPuntoContatto;
	}

	public void setSsPuntoContatto(SsPuntoContatto ssPuntoContatto) {
		this.ssPuntoContatto = ssPuntoContatto;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}
	
	public SsOOrganizzazione getSsOOrganizzazione() {
		return ssOOrganizzazione;
	}

	public void setSsOOrganizzazione(SsOOrganizzazione ssOOrganizzazione) {
		this.ssOOrganizzazione = ssOOrganizzazione;
	}
}