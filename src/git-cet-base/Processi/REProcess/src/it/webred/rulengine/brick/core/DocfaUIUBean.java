package it.webred.rulengine.brick.core;

import java.util.ArrayList;

public class DocfaUIUBean {
	
	private String protocolloReg;
	private String dataFornitura;
	private String foglio;
	private String particella;
	private String subalterno;
	private String tipoOperazione;
	private String vani;
	private String zona;
	private String zonaDocfa;
	private String zonaDocfaProp;
	private String microzona;
	private String rendita;
	private String renditaDocfa;
	private String renditaDocfaProp;
	private String classe;
	private String classeDocfa;
	private String classeDocfaProp;
	private String partita;
	private String piano;
	private String superficie;
	private String superficieDocfa;
	private String superficieDocfaProp;
	private String codCategoria;
	private String codCategoriaDocfa;
	private String codCategoriaDocfaProp;
	private String descrCategoria;
	private String dataInizioVal;
	private String dataFineVal;
	private String indirizzoDocfa;
	private String civicoDocfa;
	private ArrayList elencoIndirizzi;
	private ArrayList elencoGraffati;
	private ArrayList elencoTitolari;
	private String possesso;
	private String presenzaGraffati;
	private String tipoOperazioneOld; // in caso di tipo V qui lo memorizzo quando lo aggiorno a tipo S per l'elaborazione/integrazione delle info.
	
	public DocfaUIUBean() {
		
		protocolloReg = "";
		dataFornitura = "";
		foglio = "";
		particella = "";
		subalterno = "";
		tipoOperazione = "";
		vani = "";
		zona = "";
		microzona = "";
		rendita = "";
		classe = "";
		partita = "";
		piano = "";
		superficie = "";
		codCategoria = "";
		descrCategoria = "";
		dataInizioVal = "";
		dataFineVal = "";
		zonaDocfa = "";
		renditaDocfa = "";
		classeDocfa = "";
		superficieDocfa = "";
		codCategoriaDocfa = "";
		elencoIndirizzi = new ArrayList();
		elencoGraffati = new ArrayList();
		elencoTitolari = new ArrayList();
		possesso = "";
		presenzaGraffati = "";
		indirizzoDocfa = "";
		civicoDocfa = "";
		tipoOperazioneOld = "";
	}
		
	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getCodCategoria() {
		return codCategoria;
	}

	public void setCodCategoria(String codCategoria) {
		this.codCategoria = codCategoria;
	}

	public String getDataFineVal() {
		return dataFineVal;
	}

	public void setDataFineVal(String dataFineVal) {
		this.dataFineVal = dataFineVal;
	}

	public String getDataFornitura() {
		return dataFornitura;
	}

	public void setDataFornitura(String dataFornitura) {
		this.dataFornitura = dataFornitura;
	}

	public String getDataInizioVal() {
		return dataInizioVal;
	}

	public void setDataInizioVal(String dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}

	public String getDescrCategoria() {
		return descrCategoria;
	}

	public void setDescrCategoria(String descrCategoria) {
		this.descrCategoria = descrCategoria;
	}

	public ArrayList getElencoIndirizzi() {
		return elencoIndirizzi;
	}

	public void setElencoIndirizzi(ArrayList elencoIndirizzi) {
		this.elencoIndirizzi = elencoIndirizzi;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getMicrozona() {
		return microzona;
	}

	public void setMicrozona(String microzona) {
		this.microzona = microzona;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getPartita() {
		return partita;
	}

	public void setPartita(String partita) {
		this.partita = partita;
	}

	public String getPiano() {
		return piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getProtocolloReg() {
		return protocolloReg;
	}

	public void setProtocolloReg(String protocolloReg) {
		this.protocolloReg = protocolloReg;
	}

	public String getRendita() {
		return rendita;
	}

	public void setRendita(String rendita) {
		this.rendita = rendita;
	}

	public String getSubalterno() {
		return subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getSuperficie() {
		return superficie;
	}

	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}

	public String getVani() {
		return vani;
	}

	public void setVani(String vani) {
		this.vani = vani;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public ArrayList getElencoGraffati() {
		return elencoGraffati;
	}

	public void setElencoGraffati(ArrayList elencoGraffati) {
		this.elencoGraffati = elencoGraffati;
	}

	public String getTipoOperazione() {
		return tipoOperazione;
	}

	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}

	public ArrayList getElencoTitolari() {
		return elencoTitolari;
	}

	public void setElencoTitolari(ArrayList elencoTitolari) {
		this.elencoTitolari = elencoTitolari;
	}

	public String getClasseDocfa() {
		return classeDocfa;
	}

	public void setClasseDocfa(String classeDocfa) {
		this.classeDocfa = classeDocfa;
	}

	public String getCodCategoriaDocfa() {
		return codCategoriaDocfa;
	}

	public void setCodCategoriaDocfa(String codCategoriaDocfa) {
		this.codCategoriaDocfa = codCategoriaDocfa;
	}

	public String getRenditaDocfa() {
		return renditaDocfa;
	}

	public void setRenditaDocfa(String renditaDocfa) {
		this.renditaDocfa = renditaDocfa;
	}

	public String getSuperficieDocfa() {
		return superficieDocfa;
	}

	public void setSuperficieDocfa(String superficieDocfa) {
		this.superficieDocfa = superficieDocfa;
	}

	public String getZonaDocfa() {
		return zonaDocfa;
	}

	public void setZonaDocfa(String zonaDocfa) {
		this.zonaDocfa = zonaDocfa;
	}

	public String getClasseDocfaProp() {
		return classeDocfaProp;
	}

	public void setClasseDocfaProp(String classeDocfaProp) {
		this.classeDocfaProp = classeDocfaProp;
	}

	public String getCodCategoriaDocfaProp() {
		return codCategoriaDocfaProp;
	}

	public void setCodCategoriaDocfaProp(String codCategoriaDocfaProp) {
		this.codCategoriaDocfaProp = codCategoriaDocfaProp;
	}

	public String getRenditaDocfaProp() {
		return renditaDocfaProp;
	}

	public void setRenditaDocfaProp(String renditaDocfaProp) {
		this.renditaDocfaProp = renditaDocfaProp;
	}

	public String getSuperficieDocfaProp() {
		return superficieDocfaProp;
	}

	public void setSuperficieDocfaProp(String superficieDocfaProp) {
		this.superficieDocfaProp = superficieDocfaProp;
	}

	public String getZonaDocfaProp() {
		return zonaDocfaProp;
	}

	public void setZonaDocfaProp(String zonaDocfaProp) {
		this.zonaDocfaProp = zonaDocfaProp;
	}

	public String getPossesso() {
		return possesso;
	}

	public void setPossesso(String possesso) {
		this.possesso = possesso;
	}

	public String getPresenzaGraffati() {
		return presenzaGraffati;
	}

	public void setPresenzaGraffati(String presenzaGraffati) {
		this.presenzaGraffati = presenzaGraffati;
	}

	public String getCivicoDocfa() {
		return civicoDocfa;
	}

	public void setCivicoDocfa(String civicoDocfa) {
		this.civicoDocfa = civicoDocfa;
	}

	public String getIndirizzoDocfa() {
		return indirizzoDocfa;
	}

	public void setIndirizzoDocfa(String indirizzoDocfa) {
		this.indirizzoDocfa = indirizzoDocfa;
	}

	public String getTipoOperazioneOld()
	{
		return tipoOperazioneOld;
	}

	public void setTipoOperazioneOld(String tipoOperazioneOld)
	{
		this.tipoOperazioneOld = tipoOperazioneOld;
	}
	
	

}
