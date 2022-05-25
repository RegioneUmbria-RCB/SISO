package it.webred.cs.jsf.bean;

import java.io.Serializable;

import it.webred.cs.data.DataModelCostanti;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;


public class DatiUserSearchBean implements Serializable, Comparable<DatiUserSearchBean>{

	private PersonaDettaglio soggetto;
	private String itemLabel;
	private String id;

	public String getItemLabel() {
		return itemLabel;
	}

	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PersonaDettaglio getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(PersonaDettaglio soggetto) {
		this.soggetto = soggetto;
	}

	public boolean isAnagrafeSanitariaUmbria(){
		return id.startsWith(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA);
	}
	
	public boolean isAnagrafeSanitaria() {
		return id.startsWith(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_PREFIX);
	}
	
	public boolean isAnagrafeSigess() {
		return id.startsWith(DataModelCostanti.TipoRicercaSoggetto.SIGESS);
	}
	
	public boolean isAnagrafeInterna(){
		return id.startsWith(DataModelCostanti.TipoRicercaSoggetto.DEFAULT);
	}
	
	@Override
	public int compareTo(DatiUserSearchBean other) {
		return getDenominazione().compareTo(other.getDenominazione());
	}
	
	public String getDenominazione() {
		return String.format("%s %s", soggetto.getCognome(), soggetto.getNome());
	}
}
