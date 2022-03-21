package it.webred.cs.csa.ejb.dto.pai.affido;

import java.io.Serializable;

public class CsPaiAffidoSoggettoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private Long id;
	
	private String cognome;
	
	private String nome;
	
	private String codiceRuolo;
	
	private String descrizioneRuolo;

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

	public String getCodiceRuolo() {
		return codiceRuolo;
	}

	public void setCodiceRuolo(String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}

	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}

	public void setDescrizioneRuolo(String descrizioneRuolo) {
		this.descrizioneRuolo = descrizioneRuolo;
	}

	
}
