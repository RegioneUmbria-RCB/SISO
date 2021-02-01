package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the AM_AUDIT database table.
 * 
 */
@Entity
@Table(name="AM_AUDIT")
public class AmAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

    @Lob()
	private String args;

	@Column(name="CLASS_NAME")
	private String className;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="DATA_INS")
	private Date dataIns;

	@Column(name="ENTE_ID")
	private String enteId;

	@Column(name="\"EXCEPTION\"")
	private String exception;

	@Column(name="METHOD_NAME")
	private String methodName;

    @Lob()
	@Column(name="\"RESULT\"")
	private String result;

	@Column(name="SESSION_ID")
	private String sessionId;

	@Column(name="USER_ID")
	private String userId;
	
	private String chiave;
	
	/*@Column(name="FK_AM_FONTE")
	private String fkAmFonte;
	
	@Column(name="DESCRIZIONE_FONTE")
	private String descrizioneFonte;
	
	@Column(name="FK_AM_FONTE_TIPOINFO")
	private String fkAmFonteTipoinfo;*/

    public AmAudit() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getArgs() {
		return this.args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Date getDataIns() {
		return this.dataIns;
	}

	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}

	public String getEnteId() {
		return this.enteId;
	}

	public void setEnteId(String enteId) {
		this.enteId = enteId;
	}

	public String getException() {
		return this.exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

}