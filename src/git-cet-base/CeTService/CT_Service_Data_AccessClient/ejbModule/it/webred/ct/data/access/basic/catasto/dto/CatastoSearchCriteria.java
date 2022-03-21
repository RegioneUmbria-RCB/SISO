
package it.webred.ct.data.access.basic.catasto.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class CatastoSearchCriteria extends CeTBaseObject {

	private static final long serialVersionUID = 1L;

	private String codNazionale;
	private String codCategoria;
	
	private Boolean nonANorma;

	private String foglio;
	private String particella;
	private String unimm;
	
	//Ricerca per indirizzo
	private String idVia;
	private String idCivico;
	
	private String idSoggetto;
	
	private List<String> listaCategorie = new ArrayList<String>();
	
	private Boolean uiuAttuale;

	private Boolean faiRicercaInCatastoTerreni  = new Boolean(false);
	
	private Boolean faiRicercaInCatastoUrbano = new Boolean(false);
	
	public String getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(String idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	//Operatori di ricerca delle stringhe
	private String viaOp;
	
	//Operatori di ricerca delle stringhe
	private String nomeSoggettoOp;
	
	private String cognomeSoggettoOp;
	
	private String denominazioneSoggettoOp;
	

	public String getCodNazionale() {
		if(codNazionale==null)
			codNazionale = this.getEnteId();
		return codNazionale;
	}

	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
		super.setEnteId(codNazionale);
	}

	public String getNomeSoggettoOp() {
		return nomeSoggettoOp;
	}

	public void setNomeSoggettoOp(String nomeSoggettoOp) {
		this.nomeSoggettoOp = nomeSoggettoOp;
	}

	public String getCognomeSoggettoOp() {
		return cognomeSoggettoOp;
	}

	public void setCognomeSoggettoOp(String cognomeSoggettoOp) {
		this.cognomeSoggettoOp = cognomeSoggettoOp;
	}

	public String getDenominazioneSoggettoOp() {
		return denominazioneSoggettoOp;
	}

	public void setDenominazioneSoggettoOp(String denominazioneSoggettoOp) {
		this.denominazioneSoggettoOp = denominazioneSoggettoOp;
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

	
	public String getIdVia() {
		return idVia;
	}

	public void setIdVia(String idVia) {
		this.idVia = idVia;
	}

	public String getIdCivico() {
		return idCivico;
	}

	public void setIdCivico(String idCivico) {
		this.idCivico = idCivico;
	}

	
	public void setNonANorma(Boolean nonANorma) {
		this.nonANorma = nonANorma;
	}

	public Boolean getNonANorma() {
		return nonANorma;
	}


	public void setCodCategoria(String codCategoria) {
		this.codCategoria = codCategoria;
	}

	public String getCodCategoria() {
		return codCategoria;
		
	}
	
	public void setListaCategorie(List<String> listaCategorie) {
		this.listaCategorie = listaCategorie;
	}
	public List<String> getListaCategorie() {
		return listaCategorie;
	}

	public void setUiuAttuale(Boolean uiuAttuale) {
		this.uiuAttuale = uiuAttuale;
	}

	public Boolean getUiuAttuale() {
		return uiuAttuale;
	}

	public Boolean getFaiRicercaInCatastoTerreni() {
		return faiRicercaInCatastoTerreni;
	}

	public void setFaiRicercaInCatastoTerreni(Boolean faiRicercaInCatastoTerreni) {
		this.faiRicercaInCatastoTerreni = faiRicercaInCatastoTerreni;
	}

	public Boolean getFaiRicercaInCatastoUrbano() {
		return faiRicercaInCatastoUrbano;
	}

	public void setFaiRicercaInCatastoUrbano(Boolean faiRicercaInCatastoUrbano) {
		this.faiRicercaInCatastoUrbano = faiRicercaInCatastoUrbano;
	}

	

}
