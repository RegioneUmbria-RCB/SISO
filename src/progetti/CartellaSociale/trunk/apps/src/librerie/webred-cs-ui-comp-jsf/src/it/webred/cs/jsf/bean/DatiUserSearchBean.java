package it.webred.cs.jsf.bean;

import it.webred.cs.data.DataModelCostanti;


public class DatiUserSearchBean {

	private Object soggetto;
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

	public Object getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(Object soggetto) {
		this.soggetto = soggetto;
	}

	public boolean isAnagrafeSanitaria() {
		return id.startsWith(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_PREFIX);
	}
	
	public boolean isAnagrafeSigess() {
		return id.startsWith(DataModelCostanti.TipoRicercaSoggetto.SIGESS);
	}
}
