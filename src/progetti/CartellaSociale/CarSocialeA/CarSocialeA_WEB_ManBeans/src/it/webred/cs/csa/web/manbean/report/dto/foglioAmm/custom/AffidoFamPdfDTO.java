package it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom;

import it.webred.cs.csa.utils.bean.report.dto.BasePdfDTO;

public class AffidoFamPdfDTO extends BasePdfDTO {

	private String speseExtra = EMPTY_VALUE;
	private String speseSanitarie = EMPTY_VALUE;
	private String speseVacanze = EMPTY_VALUE;
	private String incontriProtetti = EMPTY_VALUE;
	private	String interventoEducativo = EMPTY_VALUE;
	private	String rimborsoTesti = EMPTY_VALUE;
	private	String descAltro = EMPTY_VALUE;
	private	String altro = EMPTY_VALUE;
	private	String provvAG = EMPTY_VALUE;
	private	String consensuale = EMPTY_VALUE;
	private	String anno = EMPTY_VALUE;
	private	String famAffidataria = EMPTY_VALUE;
	private	String cfCapoFamiglia = EMPTY_VALUE;
	private	String indirizzo = EMPTY_VALUE;
	private	String comune = EMPTY_VALUE;
	private	String telefono = EMPTY_VALUE;
	private	String cellulare = EMPTY_VALUE;
	private	String eteroFamiliare = EMPTY_VALUE;
	private	String aParenti = EMPTY_VALUE;
	private	String diurnoMese = EMPTY_VALUE;
	private	String giornDiurno = EMPTY_VALUE;
	private	String nGiorni = EMPTY_VALUE;
	private	String quotaPart = EMPTY_VALUE;
	private	String tipoRiscossione = EMPTY_VALUE;
	private	String accreditoA = EMPTY_VALUE;
	private	String iban = EMPTY_VALUE;
	private	String intestazione = EMPTY_VALUE;
	private	String contrFamOrigine = EMPTY_VALUE;
	
	public String getSpeseExtra() {
		return speseExtra;
	}
	public void setSpeseExtra(String speseExtra) {
		this.speseExtra = format(speseExtra);
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
	public String getDescAltro() {
		return descAltro;
	}
	public void setDescAltro(String descAltro) {
		this.descAltro = format(descAltro);
	}
	public String getAltro() {
		return altro;
	}
	public void setAltro(String altro) {
		this.altro = format(altro);
	}
	public String getProvvAG() {
		return provvAG;
	}
	public void setProvvAG(String provvAG) {
		this.provvAG = format(provvAG);
	}
	public String getConsensuale() {
		return consensuale;
	}
	public void setConsensuale(String consensuale) {
		this.consensuale = format(consensuale);
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = format(anno);
	}
	public String getFamAffidataria() {
		return famAffidataria;
	}
	public void setFamAffidataria(String famAffidataria) {
		this.famAffidataria = format(famAffidataria);
	}
	public String getCfCapoFamiglia() {
		return cfCapoFamiglia;
	}
	public void setCfCapoFamiglia(String cfCapoFamiglia) {
		this.cfCapoFamiglia = format(cfCapoFamiglia);
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = format(indirizzo);
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = format(comune);
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = format(telefono);
	}
	public String getCellulare() {
		return cellulare;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = format(cellulare);
	}
	public String getEteroFamiliare() {
		return eteroFamiliare;
	}
	public void setEteroFamiliare(String eteroFamiliare) {
		this.eteroFamiliare = format(eteroFamiliare);
	}
	public String getaParenti() {
		return aParenti;
	}
	public void setaParenti(String aParenti) {
		this.aParenti = format(aParenti);
	}
	public String getDiurnoMese() {
		return diurnoMese;
	}
	public void setDiurnoMese(String diurnoMese) {
		this.diurnoMese = format(diurnoMese);
	}
	public String getGiornDiurno() {
		return giornDiurno;
	}
	public void setGiornDiurno(String giornDiurno) {
		this.giornDiurno = format(giornDiurno);
	}
	public String getnGiorni() {
		return nGiorni;
	}
	public void setnGiorni(String nGiorni) {
		this.nGiorni = format(nGiorni);
	}
	public String getQuotaPart() {
		return quotaPart;
	}
	public void setQuotaPart(String quotaPart) {
		this.quotaPart = format(quotaPart);
	}
	public String getTipoRiscossione() {
		return tipoRiscossione;
	}
	public void setTipoRiscossione(String tipoRiscossione) {
		this.tipoRiscossione = format(tipoRiscossione);
	}
	public String getAccreditoA() {
		return accreditoA;
	}
	public void setAccreditoA(String accreditoA) {
		this.accreditoA = format(accreditoA);
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = format(iban);
	}
	public String getIntestazione() {
		return intestazione;
	}
	public void setIntestazione(String intestazione) {
		this.intestazione = format(intestazione);
	}
	public String getContrFamOrigine() {
		return contrFamOrigine;
	}
	public void setContrFamOrigine(String contrFamOrigine) {
		this.contrFamOrigine = format(contrFamOrigine);
	}

}
