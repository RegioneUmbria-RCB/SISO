package it.webred.ct.data.access.aggregator.elaborazioni.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class ZonaDTO extends CeTBaseObject implements Serializable {
	
	private String zonaCensuaria;
	private String foglio;
	private String oldMicrozona;
	private String newMicrozona;

	private boolean ricZcZonaOmi;
	
	//altri attributi
	private String codiceCategoriaEdilizia;  //1,2,3,4
	private String stato;
	private String mappale;
	private Double consistenza = new Double(2.0);  //default
	private Double superficie = new Double(1.0);   //default
	private String tipoIntervento;
	private boolean flgAscensore;
	private boolean flgPostoAuto;
	
	
	public ZonaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ZonaDTO(String zonaCensuaria, String foglio, String oldMicrozona, String newMicrozona) {
		super();

		this.zonaCensuaria = zonaCensuaria;
		this.foglio = foglio;
		this.oldMicrozona = oldMicrozona;
		this.newMicrozona = newMicrozona;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getOldMicrozona() {
		return oldMicrozona;
	}

	public void setOldMicrozona(String oldMicrozona) {
		this.oldMicrozona = oldMicrozona;
	}

	public String getNewMicrozona() {
		return newMicrozona;
	}

	public void setNewMicrozona(String newMicrozona) {
		this.newMicrozona = newMicrozona;
	}

	public String getCodiceCategoriaEdilizia() {
		return codiceCategoriaEdilizia;
	}

	public void setCodiceCategoriaEdilizia(String codiceCategoriaEdilizia) {
		this.codiceCategoriaEdilizia = codiceCategoriaEdilizia;
	}

	public String getZonaCensuaria() {
		return zonaCensuaria;
	}

	public void setZonaCensuaria(String zonaCensuaria) {
		this.zonaCensuaria = zonaCensuaria;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
	
	
	/**
	 * Il metodo inserisce tanti zeri all'inizio della stringa fino ad arrivare alla lunghezza di 3
	 * @param old
	 * @return
	 */
	public String getMicrozonaWithZeros(boolean old) {
		String zzona = old ? this.oldMicrozona : this.newMicrozona;
		
		int len = zzona.length();
		for(int i=len; i < 3; i++) {
			zzona = "0"+zzona;
		}
		
		return zzona;
	}

	public String getMappale() {
		return mappale;
	}

	public void setMappale(String mappale) {
		this.mappale = mappale;
	}

	public Double getConsistenza() {
		return consistenza;
	}

	public void setConsistenza(Double consistenza) {
		this.consistenza = consistenza;
	}

	public Double getSuperficie() {
		return superficie;
	}

	public void setSuperficie(Double superficie) {
		this.superficie = superficie;
	}

	public boolean isFlgAscensore() {
		return flgAscensore;
	}

	public void setFlgAscensore(boolean flgAscensore) {
		this.flgAscensore = flgAscensore;
	}

	public String getTipoIntervento() {
		return tipoIntervento;
	}

	public void setTipoIntervento(String tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}

	public boolean isFlgPostoAuto() {
		return flgPostoAuto;
	}

	public void setFlgPostoAuto(boolean flgPostoAuto) {
		this.flgPostoAuto = flgPostoAuto;
	}

	public boolean isRicZcZonaOmi() {
		return ricZcZonaOmi;
	}

	public void setRicZcZonaOmi(boolean ricZcZonaOmi) {
		this.ricZcZonaOmi = ricZcZonaOmi;
	}
}
