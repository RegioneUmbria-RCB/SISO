package it.escsolution.escwebgis.locazioni.bean;

import it.escsolution.escwebgis.common.EscObject;
import it.webred.ct.data.access.basic.catasto.dto.IndirizzoDTO;

import java.io.Serializable;
import java.util.List;

public class LocazioniUiu extends EscObject implements Serializable {

	private String codNazionale;
	private String cuaa;
	private String foglio;
	private String particella;
	private String unimm; 
	private String categoria;
	private String consistenza;
	private String supCat; 
	private String rendita; 
	private String classe;
	private String indirizzo;
	private String civico;
	private String percPoss;
	
	private List<IndirizzoDTO> indirizziCat;
	
	private boolean locato=false;
	
	public boolean isLocato() {
		return locato;
	}

	public void setLocato(boolean locato) {
		this.locato = locato;
	}

	public String getCuaa() {
		return cuaa;
	}

	public void setCuaa(String cuaa) {
		this.cuaa = cuaa;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getUnimm() {
		return unimm;
	}

	public void setUnimm(String unimm) {
		this.unimm = unimm;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getConsistenza() {
		return consistenza;
	}

	public void setConsistenza(String consistenza) {
		this.consistenza = consistenza;
	}

	public String getSupCat() {
		return supCat;
	}

	public void setSupCat(String supCat) {
		this.supCat = supCat;
	}

	public String getRendita() {
		return rendita;
	}

	public String getCodNazionale() {
		return codNazionale;
	}

	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
	}

	public void setRendita(String rendita) {
		this.rendita = rendita;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getPercPoss() {
		return percPoss;
	}

	public void setPercPoss(String percPoss) {
		this.percPoss = percPoss;
	}

	public List<IndirizzoDTO> getIndirizziCat() {
		return indirizziCat;
	}

	public void setIndirizziCat(List<IndirizzoDTO> indirizziCat) {
		this.indirizziCat = indirizziCat;
	}
	
}
