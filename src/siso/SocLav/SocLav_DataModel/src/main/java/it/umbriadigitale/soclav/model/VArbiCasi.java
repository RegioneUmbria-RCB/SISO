package it.umbriadigitale.soclav.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_ARBI_CASI")
public class VArbiCasi {

	@Id
	@Column(name="ID_CASO")
	private String idCaso;
	
	private String cognome;
	private String nome;
	private String cf;
	private String sesso;
	
	@Column(name="DATA_NASCITA")
	private Date dataNascita;
	
	@Column(name="ZONA_SOCIALE")
	private String zonaSociale;
	private String organizzazione;
	private String settore;
	private String categoria;
	private String iter;
	
	@Column(name="DATA_ITER")
	private Date dataIter;

	public String getIdCaso() {
		return idCaso;
	}

	public void setIdCaso(String idCaso) {
		this.idCaso = idCaso;
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

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
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

	public String getZonaSociale() {
		return zonaSociale;
	}

	public void setZonaSociale(String zonaSociale) {
		this.zonaSociale = zonaSociale;
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getIter() {
		return iter;
	}

	public void setIter(String iter) {
		this.iter = iter;
	}

	public Date getDataIter() {
		return dataIter;
	}

	public void setDataIter(Date dataIter) {
		this.dataIter = dataIter;
	}
}
