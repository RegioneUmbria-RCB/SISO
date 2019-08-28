package it.umbriadigitale.interscambio.enums;

public enum EAddress {
	COMUNE_RESIDENZA("comune residenza"),
	REGIONE_RESIDENZA("regione residenza"),
	NAZIONE_RESIDENZA("nazione residenza"),
	COMUNE_DOMICILIO("comune domicilio"),
	COMUNE_NASCITA("comune di nascita");
	
	private String use;
	
	
	private EAddress(String use) {
		this.use = use;
	}
	
	
	public String getUse() {
		return use;
	}
}
