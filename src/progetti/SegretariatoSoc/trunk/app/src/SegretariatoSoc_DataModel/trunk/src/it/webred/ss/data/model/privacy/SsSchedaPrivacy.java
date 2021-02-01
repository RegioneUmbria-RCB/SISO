package it.webred.ss.data.model.privacy;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import it.webred.ss.data.model.SsOOrganizzazione;

@Entity
@Table(name="SS_SCHEDA_PRIVACY")
@NamedQuery(name="SsSchedaPrivacy.findAll", query="SELECT c FROM SsSchedaPrivacy c")
public class SsSchedaPrivacy implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsSsPrivacyPK id;
	
	@Column(name="DT_SOTTOSCRIZIONE")
	private Date dtSottoscrizione;
	
	@Column(name="DT_INS")
	private Date dtIns;
	
	@Column(name="USER_INS")
	private String userIns;
	
	@Column(name="SCHEDA_ID") 
	private Long schedaId;
	 
	@ManyToOne 
	@JoinColumn(name="ORGANIZZAZIONE_ID", insertable=false, updatable=false)
	private SsOOrganizzazione organizzazione; 
	

	public CsSsPrivacyPK getId() {
		return id;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setId(CsSsPrivacyPK id) {
		this.id = id;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public Long getSchedaId() {
		return schedaId; 
	}
	  
	public void setSchedaId(Long schedaId) { 
		this.schedaId = schedaId; 
	}
	 
	public SsOOrganizzazione getOrganizzazione() {
		return organizzazione;
	}

	public void setOrganizzazione(SsOOrganizzazione organizzazione) {
		this.organizzazione = organizzazione;
	}

	public Date getDtSottoscrizione() {
		return dtSottoscrizione;
	}

	public void setDtSottoscrizione(Date dtSottoscrizione) {
		this.dtSottoscrizione = dtSottoscrizione;
	}

}