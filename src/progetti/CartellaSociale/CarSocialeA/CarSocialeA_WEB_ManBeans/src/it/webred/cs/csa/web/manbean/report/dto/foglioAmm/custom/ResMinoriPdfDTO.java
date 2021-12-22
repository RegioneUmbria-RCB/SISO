package it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom;

import it.webred.cs.csa.utils.bean.report.dto.BasePdfDTO;

public class ResMinoriPdfDTO extends BasePdfDTO {

	private String conRetta = EMPTY_VALUE;
	private String valoreRetta = EMPTY_VALUE;
	private String contributoFamOrigine = EMPTY_VALUE;
	private String speseExtra = EMPTY_VALUE;
	private String comunita = EMPTY_VALUE;
	private String provvAG = EMPTY_VALUE;
	private String speseSanitarie = EMPTY_VALUE;
	private String speseVacanze = EMPTY_VALUE;
	private String incontriProtetti = EMPTY_VALUE;
	private String interventoEducativo = EMPTY_VALUE;
	private String rimborsoTesti = EMPTY_VALUE;
	private String altro = EMPTY_VALUE;
	private String rientriFamOrigine = EMPTY_VALUE;
	private String denominazione = EMPTY_VALUE;
	private String indirizzo = EMPTY_VALUE;
	private String luogo = EMPTY_VALUE;
	private String telefono = EMPTY_VALUE;
	
	public String getConRetta() {
		return conRetta;
	}
	public void setConRetta(String conRetta) {
		this.conRetta = format(conRetta);
	}
	public String getValoreRetta() {
		return valoreRetta;
	}
	public void setValoreRetta(String valoreRetta) {
		this.valoreRetta = format(valoreRetta);
	}
	public String getComunita() {
		return comunita;
	}
	public void setComunita(String comunita) {
		this.comunita = format(comunita);
	}
	public String getContributoFamOrigine() {
		return contributoFamOrigine;
	}
	public void setContributoFamOrigine(String contributoFamOrigine) {
		this.contributoFamOrigine = format(contributoFamOrigine);
	}
	public String getSpeseExtra() {
		return speseExtra;
	}
	public void setSpeseExtra(String speseExtra) {
		this.speseExtra = format(speseExtra);
	}
	public String getProvvAG() {
		return provvAG;
	}
	public void setProvvAG(String provvAG) {
		this.provvAG = format(provvAG);
	}
	public String getSpeseSanitarie() {
		return speseSanitarie;
	}
	public void setSpeseSanitarie(String speseSanitarie) {
		this.speseSanitarie = format(speseSanitarie);
	}
	public String getSpeseVacanze() {
		return speseVacanze;
	}
	public void setSpeseVacanze(String speseVacanze) {
		this.speseVacanze = format(speseVacanze);
	}
	public String getIncontriProtetti() {
		return incontriProtetti;
	}
	public void setIncontriProtetti(String incontriProtetti) {
		this.incontriProtetti = format(incontriProtetti);
	}
	public String getInterventoEducativo() {
		return interventoEducativo;
	}
	public void setInterventoEducativo(String interventoEducativo) {
		this.interventoEducativo = format(interventoEducativo);
	}
	public String getRimborsoTesti() {
		return rimborsoTesti;
	}
	public void setRimborsoTesti(String rimborsoTesti) {
		this.rimborsoTesti = format(rimborsoTesti);
	}
	public String getAltro() {
		return altro;
	}
	public void setAltro(String altro) {
		this.altro = format(altro);
	}
	public String getRientriFamOrigine() {
		return rientriFamOrigine;
	}
	public void setRientriFamOrigine(String rientriFamOrigine) {
		this.rientriFamOrigine = format(rientriFamOrigine);
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = format(denominazione);
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = format(indirizzo);
	}
	public String getLuogo() {
		return luogo;
	}
	public void setLuogo(String luogo) {
		this.luogo = format(luogo);
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = format(telefono);
	}
	
}
