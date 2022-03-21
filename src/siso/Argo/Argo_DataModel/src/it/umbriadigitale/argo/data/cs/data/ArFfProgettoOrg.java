package it.umbriadigitale.argo.data.cs.data;

// Generated 26-ott-2015 13.12.17 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "AR_FF_PROGETTO_ORG")
public class ArFfProgettoOrg implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AR_PROG_ORG_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AR_PROG_ORG_ID_GENERATOR")
	private Long id;
	
	@Column(name="ORGANIZZAZIONE_ID")
	private Long organizzazioneId;
	
	@Column(name="PROGETTO_ID")
	private Long progettoId;

	@ManyToOne
	@JoinColumn(name="ORGANIZZAZIONE_ID", insertable=false, updatable=false)
	private ArOOrganizzazione arOrganizzazione;
	
	@Column(name="USER_INS")
	private String usrIns;
	
	@Column(name="DT_INS")
	private Date dtIns;
	
	private Boolean abilitato;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ArOOrganizzazione getArOrganizzazione() {
		return arOrganizzazione;
	}
	public void setArOrganizzazione(ArOOrganizzazione arOrganizzazione) {
		this.arOrganizzazione = arOrganizzazione;
	}
	public String getUsrIns() {
		return usrIns;
	}
	public void setUsrIns(String usrIns) {
		this.usrIns = usrIns;
	}
	public Date getDtIns() {
		return dtIns;
	}
	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}
	public Boolean getAbilitato() {
		return abilitato;
	}
	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}
	public Long getOrganizzazioneId() {
		return organizzazioneId;
	}
	public void setOrganizzazioneId(Long organizzazioneId) {
		this.organizzazioneId = organizzazioneId;
	}
	public Long getProgettoId() {
		return progettoId;
	}
	public void setProgettoId(Long progettoId) {
		this.progettoId = progettoId;
	}
}
