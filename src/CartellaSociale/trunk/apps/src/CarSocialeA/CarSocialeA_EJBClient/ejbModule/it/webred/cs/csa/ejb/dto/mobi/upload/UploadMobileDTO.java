package it.webred.cs.csa.ejb.dto.mobi.upload;

import java.io.Serializable;

public class UploadMobileDTO implements Serializable {
	
	
	//{"cf":"CPRMRA50A01F704P","ente":"G148","erogazioni":[],"scanners":[],"sessionId":"13a7cfea-34ed-418d-8d48-e42531e4d30a"}
	
	private String cf;
	private UploadMobileDocumentiDTO[] diari;
	private String ente;
	private UploadMobileErogazioniDTO[] erogazioni;
	private UploadMobileDocumentiDTO[] scanners;

	
	private String sessionId;
	
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public UploadMobileErogazioniDTO[] getErogazioni() {
		return erogazioni;
	}
	public void setErogazioni(UploadMobileErogazioniDTO[] erogazioni) {
		this.erogazioni = erogazioni;
	}
	public UploadMobileDocumentiDTO[] getScanners() {
		return scanners;
	}
	public void setScanners(UploadMobileDocumentiDTO[] scanners) {
		this.scanners = scanners;
	}
	public UploadMobileDocumentiDTO[] getDiari() {
		return diari;
	}
	public void setDiari(UploadMobileDocumentiDTO[] diari) {
		this.diari = diari;
	}
	
	
}
