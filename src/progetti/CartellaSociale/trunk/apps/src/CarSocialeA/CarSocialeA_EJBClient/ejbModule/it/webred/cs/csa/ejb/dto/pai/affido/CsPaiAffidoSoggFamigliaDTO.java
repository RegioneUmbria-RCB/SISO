package it.webred.cs.csa.ejb.dto.pai.affido;

import java.io.Serializable;

public class CsPaiAffidoSoggFamigliaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private String cognome;
	
	private String nome;
	
	private String cf;
	
	private String sesso;
	
	private String parentela;
	
	private String cellulare;
	
	private String indirizzo;

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

	public String getParentela() {
		return parentela;
	}

	public void setParentela(String parentela) {
		this.parentela = parentela;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

}
