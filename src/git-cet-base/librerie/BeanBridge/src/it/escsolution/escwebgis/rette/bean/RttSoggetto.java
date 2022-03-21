package it.escsolution.escwebgis.rette.bean;

import it.escsolution.escwebgis.common.EscObject;
import it.escsolution.escwebgis.cosapNew.bean.CosapTassa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class RttSoggetto extends EscObject implements Serializable {


	private static final long serialVersionUID = 1L;
	String id;
	String codSoggetto;
	String codiceFiscale;
	String provenienza;
	String cognome;
	String nome;
	String sesso;
	String dataNascita;
	String partitaIva;
	String comuneNascita;
	String localitaNascita;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodSoggetto() {
		return codSoggetto;
	}
	public void setCodSoggetto(String codSoggetto) {
		this.codSoggetto = codSoggetto;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
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
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}
	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	public String getLocalitaNascita() {
		return localitaNascita;
	}
	public void setLocalitaNascita(String localitaNascita) {
		this.localitaNascita = localitaNascita;
	}
	
}
