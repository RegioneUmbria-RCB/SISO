package it.webred.ss.data.model.tb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="SS_UFF_OPERATORE_ANAGRAFICA")
public class SsUffOperatoreAnagrafica implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId 
	  private SsUffOperatoreAnagraficaPK id;
	  
	  private String cognome;
	  private String nome;
	  
	  @Column(name="CODICE_FISCALE")
	  private String cf;
	  
	  @Column(name="DATA_NASCITA")
	  private String dataNascita;
	  
	  @Column(name="COMUNE_NASCITA")
	  private String comuneNascita;

	public SsUffOperatoreAnagraficaPK getId() {
		return id;
	}

	public void setId(SsUffOperatoreAnagraficaPK id) {
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

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
}
