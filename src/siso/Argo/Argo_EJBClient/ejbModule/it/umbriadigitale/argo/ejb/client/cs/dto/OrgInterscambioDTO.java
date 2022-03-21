package it.umbriadigitale.argo.ejb.client.cs.dto;

import java.io.Serializable;

public class OrgInterscambioDTO implements Serializable {

	private long id;
	private String tooltip;
	private Boolean abilitato;
	private String email;
	private String nome;
	private String belfiore;
	private String pivaCf;
	private String zonaSociale;
	 
	private String codiceOrg;
	private String flussoTipo;
	private String flussoVer;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public Boolean getAbilitato() {
		return abilitato;
	}
	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getBelfiore() {
		return belfiore;
	}
	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}
	public String getPivaCf() {
		return pivaCf;
	}
	public void setPivaCf(String pivaCf) {
		this.pivaCf = pivaCf;
	}
	public String getZonaSociale() {
		return zonaSociale;
	}
	public void setZonaSociale(String zonaSociale) {
		this.zonaSociale = zonaSociale;
	}
	public String getCodiceOrg() {
		return codiceOrg;
	}
	public void setCodiceOrg(String codiceOrg) {
		this.codiceOrg = codiceOrg;
	}
	public String getFlussoTipo() {
		return flussoTipo;
	}
	public void setFlussoTipo(String flussoTipo) {
		this.flussoTipo = flussoTipo;
	}
	public String getFlussoVer() {
		return flussoVer;
	}
	public void setFlussoVer(String flussoVer) {
		this.flussoVer = flussoVer;
	}
	
	
}
