package it.umbriadigitale.soclav.service.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class CartellaSocialeDTO implements Serializable {
	
	private String cognome;
	private String nome;
	private String cf;
	private String sesso;
	private Date dataNascita;
	
	private String zonaSociale;
	private String organizzazione;
	private String settore;
	private String categoriaSoc;
	private String statoIter;
	private Date dataIter;
	
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getOrganizzazione() {
		return organizzazione;
	}
	public void setOrganizzazione(String organizzazione) {
		this.organizzazione = organizzazione;
	}
	public String getSettore() {
		return settore;
	}
	public void setSettore(String settore) {
		this.settore = settore;
	}
	public String getCategoriaSoc() {
		return categoriaSoc;
	}
	public void setCategoriaSoc(String categoriaSoc) {
		this.categoriaSoc = categoriaSoc;
	}
	public String getStatoIter() {
		return statoIter;
	}
	public void setStatoIter(String statoIter) {
		this.statoIter = statoIter;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public Date getDataIter() {
		return dataIter;
	}
	public void setDataIter(Date dataIter) {
		this.dataIter = dataIter;
	}
	public String getZonaSociale() {
		return zonaSociale;
	}
	public void setZonaSociale(String zonaSociale) {
		this.zonaSociale = zonaSociale;
	}
}
