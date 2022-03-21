package it.webred.cs.csa.ejb.dto.mobi.upload;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UploadMobileValoreDTO implements Serializable {
	
	//"valori":[{"id_attr":382234,"label":"Motivo Mancata Erogazione","":0,"uniMisDesc":"","valore":"382238","valoreDesc":"Rinvio"}]}],"
	private Long id_attr;
	private String label;
	private Long uniMis;
	private String uniMisDesc;
	private String valore;
	private String valoreDesc;
	public Long getId_attr() {
		return id_attr;
	}
	public void setId_attr(Long id_attr) {
		this.id_attr = id_attr;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Long getUniMis() {
		return uniMis;
	}
	public void setUniMis(Long uniMis) {
		this.uniMis = uniMis;
	}
	public String getUniMisDesc() {
		return uniMisDesc;
	}
	public void setUniMisDesc(String uniMisDesc) {
		this.uniMisDesc = uniMisDesc;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	public String getValoreDesc() {
		return valoreDesc;
	}
	public void setValoreDesc(String valoreDesc) {
		this.valoreDesc = valoreDesc;
	}
}
