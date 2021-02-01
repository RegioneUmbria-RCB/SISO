package it.webred.cs.data.model.view;

import it.webred.cs.data.model.CsOOperatore;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="CS_AM_ANAGRAFICA_OPERATORE")
@NamedQuery(name="CsAmAnagraficaOperatore.findAll", query="SELECT c FROM CsAmAnagraficaOperatore c ORDER BY cognome, nome")
public class CsAmAnagraficaOperatore implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String username;

	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;
	
	private String cognome;
	
	private String nome;
	
	private String email;
	
	private String enti;
	
	@OneToOne
	@JoinColumn (name="ID_CS_OPERATORE")
	private CsOOperatore operatore;
	
	public CsAmAnagraficaOperatore() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnti() {
		return enti;
	}

	public void setEnti(String enti) {
		this.enti = enti;
	}

	public CsOOperatore getOperatore() {
		return operatore;
	}

	public void setOperatore(CsOOperatore operatore) {
		this.operatore = operatore;
	}

}