package it.escsolution.escwebgis.redditiAnnuali.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class RedditiAnnuali extends EscObject implements Serializable{
	
	private String ideTelematico;
	private String codiceFiscaleDic;
	private String flagPersona;
	private String cognome;
	private String nome;
	private String codComuneNascita;
	private String desComuneNascita;
	private String provComuneNascita;
	private String dataNascita;
	private String sesso;
	private String codiceRE;
	private String codiceRF;
	private String codiceRG;
	private String cfSostitutoImposta;
	private String tipoModello;
	private String dicCorrettiva;
	private String dicIntegrativa;
	private String statoDichiarazione;
	private String flagValuta;
	private String codCatDomFiscaleDic;
	private String desCatDomFiscaleDic;
	private String provCatDomFiscaleDic;
	private String codCatDomFiscaleAttuale;	
	private String desCatDomFiscaleAttuale;
	private String provCatDomFiscaleAttuale;
	private String indirizzoAttuale;
	private String capAttuale;
	private String annoImposta;
	private boolean linkFamiglia;
	private boolean linkAnagrafe;
	private boolean linkDichiarazioniICI;
	private boolean linkDettaglioCatasto;
	private boolean linkVersamentiICI;
	private boolean linkLocazioni;
	private String descTipoModello;
	private boolean visContribuente;
	private boolean visCollegati;
	private boolean visSostitutoImposta;
	
	private String denominazione;
	private Integer naturaGiuridica;
	private Integer situazione;
	private Integer stato;
	private Integer onlus;
	private Integer settoreOnlus;
	
	private String descNaturaGiuridica;
	private String descSituazione;
	private String descStato;
	private String descOnlus;
	private String descSettoreOnlus;
	
	
	
	private LinkedHashMap<String, DecoRedditiDichiarati> redditi;
	
	public String getChiave(){ 
		return ideTelematico + "_" + codiceFiscaleDic + "_" + annoImposta;
	}
	
	public String getIdeTelematico() {
		return ideTelematico;
	}

	public void setIdeTelematico(String ideTelematico) {
		this.ideTelematico = ideTelematico;
	}

	public String getCodiceFiscaleDic() {
		return codiceFiscaleDic;
	}

	public void setCodiceFiscaleDic(String codiceFiscaleDic) {
		this.codiceFiscaleDic = codiceFiscaleDic;
	}

	public String getFlagPersona() {
		return flagPersona;
	}

	public void setFlagPersona(String flagPersona) {
		this.flagPersona = flagPersona;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodComuneNascita() {
		return codComuneNascita;
	}

	public void setCodComuneNascita(String codComuneNascita) {
		this.codComuneNascita = codComuneNascita;
	}

	public String getDesComuneNascita() {
		return desComuneNascita;
	}

	public void setDesComuneNascita(String desComuneNascita) {
		this.desComuneNascita = desComuneNascita;
	}

	public String getProvComuneNascita() {
		return provComuneNascita;
	}

	public void setProvComuneNascita(String provComuneNascita) {
		this.provComuneNascita = provComuneNascita;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getCodiceRE() {
		return codiceRE;
	}

	public void setCodiceRE(String codiceRE) {
		this.codiceRE = codiceRE;
	}

	public String getCodiceRF() {
		return codiceRF;
	}

	public void setCodiceRF(String codiceRF) {
		this.codiceRF = codiceRF;
	}

	public String getCodiceRG() {
		return codiceRG;
	}

	public void setCodiceRG(String codiceRG) {
		this.codiceRG = codiceRG;
	}

	public String getCfSostitutoImposta() {
		return cfSostitutoImposta;
	}

	public void setCfSostitutoImposta(String cfSostitutoImposta) {
		this.cfSostitutoImposta = cfSostitutoImposta;
	}

	public String getTipoModello() {
		return tipoModello;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}

	public String getDicCorrettiva() {
		return dicCorrettiva;
	}

	public void setDicCorrettiva(String dicCorrettiva) {
		this.dicCorrettiva = dicCorrettiva;
	}

	public String getDicIntegrativa() {
		return dicIntegrativa;
	}

	public void setDicIntegrativa(String dicIntegrativa) {
		this.dicIntegrativa = dicIntegrativa;
	}

	public String getStatoDichiarazione() {
		return statoDichiarazione;
	}

	public void setStatoDichiarazione(String statoDichiarazione) {
		this.statoDichiarazione = statoDichiarazione;
	}

	public String getFlagValuta() {
		return flagValuta;
	}

	public void setFlagValuta(String flagValuta) {
		this.flagValuta = flagValuta;
	}

	public String getCodCatDomFiscaleDic() {
		return codCatDomFiscaleDic;
	}

	public void setCodCatDomFiscaleDic(String codCatDomFiscaleDic) {
		this.codCatDomFiscaleDic = codCatDomFiscaleDic;
	}

	public String getDesCatDomFiscaleDic() {
		return desCatDomFiscaleDic;
	}

	public void setDesCatDomFiscaleDic(String desCatDomFiscaleDic) {
		this.desCatDomFiscaleDic = desCatDomFiscaleDic;
	}

	public String getProvCatDomFiscaleDic() {
		return provCatDomFiscaleDic;
	}

	public void setProvCatDomFiscaleDic(String provCatDomFiscaleDic) {
		this.provCatDomFiscaleDic = provCatDomFiscaleDic;
	}

	public String getCodCatDomFiscaleAttuale() {
		return codCatDomFiscaleAttuale;
	}

	public void setCodCatDomFiscaleAttuale(String codCatDomFiscaleAttuale) {
		this.codCatDomFiscaleAttuale = codCatDomFiscaleAttuale;
	}

	public String getDesCatDomFiscaleAttuale() {
		return desCatDomFiscaleAttuale;
	}

	public void setDesCatDomFiscaleAttuale(String desCatDomFiscaleAttuale) {
		this.desCatDomFiscaleAttuale = desCatDomFiscaleAttuale;
	}

	public String getProvCatDomFiscaleAttuale() {
		return provCatDomFiscaleAttuale;
	}

	public void setProvCatDomFiscaleAttuale(String provCatDomFiscaleAttuale) {
		this.provCatDomFiscaleAttuale = provCatDomFiscaleAttuale;
	}

	public String getIndirizzoAttuale() {
		return indirizzoAttuale;
	}

	public void setIndirizzoAttuale(String indirizzoAttuale) {
		this.indirizzoAttuale = indirizzoAttuale;
	}

	public String getAnnoImposta() {
		return annoImposta;
	}

	public void setAnnoImposta(String annoImposta) {
		this.annoImposta = annoImposta;
	}

	public boolean isLinkFamiglia() {
		return linkFamiglia;
	}

	public void setLinkFamiglia(boolean linkFamiglia) {
		this.linkFamiglia = linkFamiglia;
	}

	public boolean isLinkAnagrafe() {
		return linkAnagrafe;
	}

	public void setLinkAnagrafe(boolean linkAnagrafe) {
		this.linkAnagrafe = linkAnagrafe;
	}

	public boolean isLinkDichiarazioniICI() {
		return linkDichiarazioniICI;
	}

	public void setLinkDichiarazioniICI(boolean linkDichiarazioniICI) {
		this.linkDichiarazioniICI = linkDichiarazioniICI;
	}

	public boolean isLinkDettaglioCatasto() {
		return linkDettaglioCatasto;
	}

	public void setLinkDettaglioCatasto(boolean linkDettaglioCatasto) {
		this.linkDettaglioCatasto = linkDettaglioCatasto;
	}

	public boolean isLinkVersamentiICI() {
		return linkVersamentiICI;
	}

	public void setLinkVersamentiICI(boolean linkVersamentiICI) {
		this.linkVersamentiICI = linkVersamentiICI;
	}

	public boolean isLinkLocazioni() {
		return linkLocazioni;
	}

	public void setLinkLocazioni(boolean linkLocazioni) {
		this.linkLocazioni = linkLocazioni;
	}

	public String getDescTipoModello() {
		return descTipoModello;
	}

	public void setDescTipoModello(String descTipoModello) {
		this.descTipoModello = descTipoModello;
	}

	public boolean isVisContribuente() {
		return visContribuente;
	}

	public void setVisContribuente(boolean visContribuente) {
		this.visContribuente = visContribuente;
	}

	public boolean isVisCollegati() {
		return visCollegati;
	}

	public void setVisCollegati(boolean visCollegati) {
		this.visCollegati = visCollegati;
	}

	public boolean isVisSostitutoImposta() {
		return visSostitutoImposta;
	}

	public void setVisSostitutoImposta(boolean visSostitutoImposta) {
		this.visSostitutoImposta = visSostitutoImposta;
	}

	public LinkedHashMap<String, DecoRedditiDichiarati> getRedditi() {
		return redditi;
	}
	
	public void setRedditi(LinkedHashMap<String, DecoRedditiDichiarati> redditi) {
		this.redditi = redditi;
	}
	
	public void addReddito(DecoRedditiDichiarati reddito) {
		if (redditi == null)
			redditi = new LinkedHashMap<String, DecoRedditiDichiarati>();
		redditi.put(reddito.getCodiceQuadro(), reddito);
	}

	public String getCapAttuale() {
		return capAttuale;
	}

	public void setCapAttuale(String capAttuale) {
		this.capAttuale = capAttuale;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Integer getNaturaGiuridica() {
		return naturaGiuridica;
	}

	public void setNaturaGiuridica(Integer naturaGiuridica) {
		this.naturaGiuridica = naturaGiuridica;
	}

	public Integer getSituazione() {
		return situazione;
	}

	public void setSituazione(Integer situazione) {
		this.situazione = situazione;
	}

	public Integer getStato() {
		return stato;
	}

	public void setStato(Integer stato) {
		this.stato = stato;
	}

	public Integer getOnlus() {
		return onlus;
	}

	public void setOnlus(Integer onlus) {
		this.onlus = onlus;
	}

	public Integer getSettoreOnlus() {
		return settoreOnlus;
	}

	public void setSettoreOnlus(Integer settoreOnlus) {
		this.settoreOnlus = settoreOnlus;
	}

	public String getDescNaturaGiuridica() {
		return descNaturaGiuridica;
	}

	public void setDescNaturaGiuridica(String descNaturaGiuridica) {
		this.descNaturaGiuridica = descNaturaGiuridica;
	}

	public String getDescSituazione() {
		return descSituazione;
	}

	public void setDescSituazione(String descSituazione) {
		this.descSituazione = descSituazione;
	}

	public String getDescStato() {
		return descStato;
	}

	public void setDescStato(String descStato) {
		this.descStato = descStato;
	}

	public String getDescOnlus() {
		return descOnlus;
	}

	public void setDescOnlus(String descOnlus) {
		this.descOnlus = descOnlus;
	}

	public String getDescSettoreOnlus() {
		return descSettoreOnlus;
	}

	public void setDescSettoreOnlus(String descSettoreOnlus) {
		this.descSettoreOnlus = descSettoreOnlus;
	}

}
