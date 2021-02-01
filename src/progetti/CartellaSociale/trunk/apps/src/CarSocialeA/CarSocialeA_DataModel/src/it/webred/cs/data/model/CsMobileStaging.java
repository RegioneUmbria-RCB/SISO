package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the CS_D_CLOB database table.
 * 
 */
@Entity
@Table(name="CS_MOBILE_STAGING")
public class CsMobileStaging implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_MOBILE_STAGING_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_MOBILE_STAGING_ID_GENERATOR")
	private long id;

	@Lob
	private String json;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SINCRO_DT")
	private Date dtSincro;
	
	@Column(name="SINCRO_USR")
	private String usrSincro;
	
	@Column(name="SINCRO_ENTE")
	private String enteSincro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_ELAB")
	private Date dtElab;
	
	@Lob
	@Column(name="MSG_ELAB")
	private String msgElab;
	
	@Column(name="FLG_ELAB")
	private Boolean flgElab;
	
	private String tipo;
	
	
	@Column(name="SESSION_ID")
	private String sessionId;
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getJson() {
		return json;
	}


	public void setJson(String json) {
		this.json = json;
	}


	public Date getDtSincro() {
		return dtSincro;
	}


	public void setDtSincro(Date dtSincro) {
		this.dtSincro = dtSincro;
	}


	public String getUsrSincro() {
		return usrSincro;
	}


	public void setUsrSincro(String usrSincro) {
		this.usrSincro = usrSincro;
	}


	public Date getDtElab() {
		return dtElab;
	}


	public void setDtElab(Date dtElab) {
		this.dtElab = dtElab;
	}


	public String getMsgElab() {
		return msgElab;
	}


	public void setMsgElab(String msgElab) {
		this.msgElab = msgElab;
	}


	public Boolean getFlgElab() {
		return flgElab;
	}


	public void setFlgElab(Boolean flgElab) {
		this.flgElab = flgElab;
	}


	public String getEnteSincro() {
		return enteSincro;
	}


	public void setEnteSincro(String enteSincro) {
		this.enteSincro = enteSincro;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getSessionId() {
		return sessionId;
	}


	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}