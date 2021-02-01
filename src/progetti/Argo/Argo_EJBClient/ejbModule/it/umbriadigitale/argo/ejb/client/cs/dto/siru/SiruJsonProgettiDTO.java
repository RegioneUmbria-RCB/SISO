package it.umbriadigitale.argo.ejb.client.cs.dto.siru;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SiruJsonProgettiDTO implements Serializable {

/*	{
		  "progetti": {
		    "progetto": [
		      {
		        "id_progetto": "string",
		        "codice_locale_progetto": "string",
		        "id_intervento_specifico": "string",
		        "descrizione_intervento_specifico": "string",
		        "id_attivita": "string",
		        "titolo_corso": "string",
		        "codice_corso": "string",
		        "partecipanti_previsti": "string",
		        "denominazione_soggetti": "string",
		        "partita_iva": "string",
		        "codice_fisale": "string"
		      }
		    ]
		  }
		}*/
	
	 private String id_progetto;
	 private String codice_locale_progetto;
	 private String id_intervento_specifico;
	 private String descrizione_intervento_specifico;
	 private String id_attivita;
	 private String titolo_corso;
	 private String codice_corso;
	 private String partecipanti_previsti;
	 private String denominazione_soggetto;
	 private String partita_iva;
	 private String codice_fiscale;
	 
	public String getId_progetto() {
		return id_progetto;
	}
	public void setId_progetto(String id_progetto) {
		this.id_progetto = id_progetto;
	}
	public String getCodice_locale_progetto() {
		return codice_locale_progetto;
	}
	public void setCodice_locale_progetto(String codice_locale_progetto) {
		this.codice_locale_progetto = codice_locale_progetto;
	}
	public String getId_intervento_specifico() {
		return id_intervento_specifico;
	}
	public void setId_intervento_specifico(String id_intervento_specifico) {
		this.id_intervento_specifico = id_intervento_specifico;
	}
	public String getDescrizione_intervento_specifico() {
		return descrizione_intervento_specifico;
	}
	public void setDescrizione_intervento_specifico(
			String descrizione_intervento_specifico) {
		this.descrizione_intervento_specifico = descrizione_intervento_specifico;
	}
	public String getId_attivita() {
		return id_attivita;
	}
	public void setId_attivita(String id_attivita) {
		this.id_attivita = id_attivita;
	}
	public String getTitolo_corso() {
		return titolo_corso;
	}
	public void setTitolo_corso(String titolo_corso) {
		this.titolo_corso = titolo_corso;
	}
	public String getCodice_corso() {
		return codice_corso;
	}
	public void setCodice_corso(String codice_corso) {
		this.codice_corso = codice_corso;
	}
	public String getPartecipanti_previsti() {
		return partecipanti_previsti;
	}
	public void setPartecipanti_previsti(String partecipanti_previsti) {
		this.partecipanti_previsti = partecipanti_previsti;
	}
	public String getDenominazione_soggetto() {
		return denominazione_soggetto;
	}
	public void setDenominazione_soggetto(String denominazione_soggetto) {
		this.denominazione_soggetto = denominazione_soggetto;
	}
	public String getPartita_iva() {
		return partita_iva;
	}
	public void setPartita_iva(String partita_iva) {
		this.partita_iva = partita_iva;
	}
	public String getCodice_fiscale() {
		return codice_fiscale;
	}
	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}
}
