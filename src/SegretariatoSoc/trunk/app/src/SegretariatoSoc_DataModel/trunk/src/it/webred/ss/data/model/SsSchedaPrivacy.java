package it.webred.ss.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="SS_SCHEDA_PRIVACY")
@NamedQuery(name="SsSchedaPrivacy.findAll", query="SELECT c FROM SsSchedaPrivacy c")
public class SsSchedaPrivacy implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SsSchedaPrivacyPK id;
	
	@Column(name="DT_INS")
	private Date dtIns;
	
	@Column(name="USER_INS")
	private String userIns;
	
	@Column(name="SCHEDA_ID")
	private Long schedaId;

	public SsSchedaPrivacyPK getId() {
		return id;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public String getUserIns() {
		return userIns;
	}

	public Long getSchedaId() {
		return schedaId;
	}

	public void setId(SsSchedaPrivacyPK id) {
		this.id = id;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public void setSchedaId(Long schedaId) {
		this.schedaId = schedaId;
	}
	
}