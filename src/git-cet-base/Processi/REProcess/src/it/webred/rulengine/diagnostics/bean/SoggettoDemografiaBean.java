package it.webred.rulengine.diagnostics.bean;

import java.io.Serializable;
import java.util.Date;

public class SoggettoDemografiaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Date dtFineVal;
	private String idCivicoRes;
	private String idFam;
	private String idOrigFam;
	private String tipoFam;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDtFineVal() {
		return dtFineVal;
	}
	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}
	public String getIdCivicoRes() {
		return idCivicoRes;
	}
	public void setIdCivicoRes(String idCivicoRes) {
		this.idCivicoRes = idCivicoRes;
	}
	public String getIdFam() {
		return idFam;
	}
	public void setIdFam(String idFam) {
		this.idFam = idFam;
	}
	public String getIdOrigFam() {
		return idOrigFam;
	}
	public void setIdOrigFam(String idOrigFam) {
		this.idOrigFam = idOrigFam;
	}
	public String getTipoFam() {
		return tipoFam;
	}
	public void setTipoFam(String tipoFam) {
		this.tipoFam = tipoFam;
	}
	
}
