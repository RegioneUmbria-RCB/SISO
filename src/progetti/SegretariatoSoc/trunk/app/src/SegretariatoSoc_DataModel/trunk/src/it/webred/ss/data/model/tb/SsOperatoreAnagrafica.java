package it.webred.ss.data.model.tb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name="SS_OPERATORE_ANAGRAFICA")
public class SsOperatoreAnagrafica implements Serializable {
	
	private static final long serialVersionUID = 1L;

	  @Id
	  private String operatore;
	  private String cognome;
	  private String nome;
	  
	  @Column(name="CODICE_FISCALE")
	  private String cf;
	  
	  @Column(name="DATA_NASCITA")
	  private String dataNascita;
	  
	  @Column(name="COMUNE_NASCITA")
	  private String comuneNascita;

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
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
	
	public String getStampaDesc(){
		if(!StringUtils.isBlank(cognome) && !StringUtils.isBlank(nome)){
			return cognome + " " + nome;
		}
		return null;
	}
}
