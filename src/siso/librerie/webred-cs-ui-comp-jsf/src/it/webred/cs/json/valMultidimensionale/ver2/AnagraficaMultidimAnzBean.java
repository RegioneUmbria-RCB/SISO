package it.webred.cs.json.valMultidimensionale.ver2;

import java.io.Serializable;


public class AnagraficaMultidimAnzBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected Long id;
	protected String cognome;
	protected String nome;
	protected String indirizzo;
	protected String citta;
	protected String parentela;
	protected String telefono;
	protected String disponibilita;
	
	public AnagraficaMultidimAnzBean(Long id, String cognome, String nome, String indirizzo, String citta, String parentela, String telefono, String disponibilita){
		this.id = id;
		this.cognome = cognome;
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.citta = citta;
		this.parentela = parentela;
		this.telefono = telefono;
		this.disponibilita = disponibilita;
	}

	//Dummy constructor for parse sub class to jackson
	public AnagraficaMultidimAnzBean(){
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getParentela() {
		return parentela;
	}

	public void setParentela(String parentela) {
		this.parentela = parentela;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(String disponibilita) {
		this.disponibilita = disponibilita;
	}
}
