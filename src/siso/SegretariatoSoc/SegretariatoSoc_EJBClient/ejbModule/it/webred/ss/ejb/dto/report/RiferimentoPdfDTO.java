package it.webred.ss.ejb.dto.report;

import java.io.Serializable;

public class RiferimentoPdfDTO implements Serializable{

	public final String EMPTY = "";
	
	private String cognome_rif = EMPTY;
	private String nome_rif = EMPTY;
	private String problemi_rif = EMPTY;
	private String indirizzo_rif = EMPTY;
	private String tel_rif = EMPTY;	
	private String cel_rif = EMPTY;
	private String sesso_rif = EMPTY;
	private String luogo_nascita_rif = EMPTY;
	private String data_nascita_rif = EMPTY;
	private String sc_rif = EMPTY;	
	private String parentela_rif = EMPTY;
	private String email_rif = EMPTY;
	
	
	public String getCognome_rif() {
		return cognome_rif;
	}
	public void setCognome_rif(String cognome_rif) {
		this.cognome_rif = cognome_rif;
	}
	public String getNome_rif() {
		return nome_rif;
	}
	public void setNome_rif(String nome_rif) {
		this.nome_rif = nome_rif;
	}
	public String getProblemi_rif() {
		return problemi_rif;
	}
	public void setProblemi_rif(String problemi_rif) {
		this.problemi_rif = problemi_rif;
	}
	public String getIndirizzo_rif() {
		return indirizzo_rif;
	}
	public void setIndirizzo_rif(String indirizzo_rif) {
		this.indirizzo_rif = indirizzo_rif;
	}
	public String getTel_rif() {
		return tel_rif;
	}
	public void setTel_rif(String tel_rif) {
		this.tel_rif = tel_rif;
	}
	public String getCel_rif() {
		return cel_rif;
	}
	public void setCel_rif(String cel_rif) {
		this.cel_rif = cel_rif;
	}
	public String getSesso_rif() {
		return sesso_rif;
	}
	public void setSesso_rif(String sesso_rif) {
		this.sesso_rif = sesso_rif;
	}
	public String getLuogo_nascita_rif() {
		return luogo_nascita_rif;
	}
	public void setLuogo_nascita_rif(String luogo_nascita_rif) {
		this.luogo_nascita_rif = luogo_nascita_rif;
	}
	public String getData_nascita_rif() {
		return data_nascita_rif;
	}
	public void setData_nascita_rif(String data_nascita_rif) {
		this.data_nascita_rif = data_nascita_rif;
	}
	public String getSc_rif() {
		return sc_rif;
	}
	public void setSc_rif(String sc_rif) {
		this.sc_rif = sc_rif;
	}
	public String getParentela_rif() {
		return parentela_rif;
	}
	public void setParentela_rif(String parentela_rif) {
		this.parentela_rif = parentela_rif;
	}
	public String getEmail_rif() {
		return email_rif;
	}
	public void setEmail_rif(String email_rif) {
		this.email_rif = email_rif;
	}
	
	
	
}
