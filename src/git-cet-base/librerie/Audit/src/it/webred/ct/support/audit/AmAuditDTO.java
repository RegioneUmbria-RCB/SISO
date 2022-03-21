package it.webred.ct.support.audit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AmAuditDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String args;
	private String className;
	private Date dataIns;
	private String enteId;
	private String exception;
	private String methodName;
	private String result;
	private String sessionId;
	private String userId;
	private String chiave;
	private String descrizione;

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Date getDataIns() {
		return dataIns;
	}

	public void setDataIns(Date dataIns) {
		this.dataIns = dataIns;
	}

	public String getEnteId() {
		return enteId;
	}

	public void setEnteId(String enteId) {
		this.enteId = enteId;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
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
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}