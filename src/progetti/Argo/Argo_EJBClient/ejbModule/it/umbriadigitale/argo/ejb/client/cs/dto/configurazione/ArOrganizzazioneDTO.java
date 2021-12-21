package it.umbriadigitale.argo.ejb.client.cs.dto.configurazione;

import java.io.Serializable;

public class ArOrganizzazioneDTO implements Serializable {

	private Long id;
	private String codRouting;
	private String descrizione;
	private String zonaSociale;
	private Boolean abilitato;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodRouting() {
		return codRouting;
	}
	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getZonaSociale() {
		return zonaSociale;
	}
	public void setZonaSociale(String zonaSociale) {
		this.zonaSociale = zonaSociale;
	}
	public Boolean getAbilitato() {
		return abilitato;
	}
	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

}

