package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="CS_O_OPERATORE_ANAGRAFICA")
public class CsOOperatoreAnagrafica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String abilitato;

//INIZIO MOD-RL
	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;
//FINE MOD-RL
		
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_VAL")
	private Date dataFineVal;

	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_VAL")
	private Date dataInizioVal;

	@Basic
	private String tooltip;

	@Basic
	private String username;
	
	@Basic
	private String cognome;
	
	@Basic
	private String nome;
	
	private String email;
	
	public CsOOperatoreAnagrafica() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//INIZIO MOD-RL
	public String getCodiceFiscale() {
		return codiceFiscale;
	}  

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	} 
//FINE MOD-RL
	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public Date getDataFineVal() {
		return this.dataFineVal;
	}

	public void setDataFineVal(Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}

	public Date getDataInizioVal() {
		return this.dataInizioVal;
	}

	public void setDataInizioVal(Date dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	
	public String getDenominazione(){
		String denominazione = "";
		if (cognome!=null || nome!=null)
			denominazione = cognome + " " + nome;
		if(denominazione.trim().isEmpty())
			denominazione = username;
		return denominazione;
	}
}