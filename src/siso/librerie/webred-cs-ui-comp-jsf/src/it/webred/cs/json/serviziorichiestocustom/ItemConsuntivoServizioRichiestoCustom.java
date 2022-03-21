package it.webred.cs.json.serviziorichiestocustom;

import java.util.Date;

public class ItemConsuntivoServizioRichiestoCustom {
	private Date date = new Date();
	private String description;
	private Integer numOperators = 1;
	private Integer numHours = 0;
	
	// 1-Accettazione richiesta, 2-Lavorazione, 3-Verifica iter, 4-Servizio concluso, 5-Avvio iter esterno a sportello, 6-Verifica iter esterno a sportello, 7-Servizio erogato esternamente a sportello
	private String tipoAttivita;

	public Date getDate() {
		return date;
	}

	public void setDate(Date data) {
		this.date = data;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumOperators() {
		return numOperators;
	}

	public void setNumOperators(Integer numOperators) {
		this.numOperators = numOperators;
	}

	public Integer getNumHours() {
		return numHours;
	}

	public void setNumHours(Integer numHours) {
		this.numHours = numHours;
	}

	public String getTipoAttivita() {
		return tipoAttivita;
	}

	public void setTipoAttivita(String tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	} 
	
}
