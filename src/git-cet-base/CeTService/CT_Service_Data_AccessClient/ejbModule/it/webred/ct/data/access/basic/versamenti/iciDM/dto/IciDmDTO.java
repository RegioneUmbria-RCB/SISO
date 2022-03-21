package it.webred.ct.data.access.basic.versamenti.iciDM.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IciDmDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private AnagSoggIciDmDTO datiSogg; //Valorizzato solo se il c.fiscale partita IVA risultano errati
	
	private String codConcessione;
	private String codEnte;
	private String numQuietanza;
	private Date dtVersamento;
	private String cfVersante;
	private String numQuietanzaRif;
	private BigDecimal impVersato;
	
	private String flgQuadratura;
	private String desQuadratura;
	
	private String flgReperibilita;
	private String desReperibilita;
	
	private String codTipoVersamento;
	private String desTipoVersamento;

	private Date dtRegistrazione;

	private String flgCompetenza;
	private String desCompetenza;
	
	private String comuneImm;
	private String capImm;
	
	private String flgIdentificazione;
	private String desIdentificazione;
	
	private String tipoTab; //D,V (Utilizzato in Diogene)
	
	public String getCodConcessione() {
		return codConcessione;
	}
	public void setCodConcessione(String codConcessione) {
		this.codConcessione = codConcessione;
	}
	public String getCodEnte() {
		return codEnte;
	}
	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}
	public String getNumQuietanza() {
		return numQuietanza;
	}
	public void setNumQuietanza(String numQuietanza) {
		this.numQuietanza = numQuietanza;
	}
	public Date getDtVersamento() {
		return dtVersamento;
	}
	public void setDtVersamento(Date dtVersamento) {
		this.dtVersamento = dtVersamento;
	}
	public String getCfVersante() {
		return cfVersante;
	}
	public void setCfVersante(String cfVersante) {
		this.cfVersante = cfVersante;
	}
	public String getNumQuietanzaRif() {
		return numQuietanzaRif;
	}
	public void setNumQuietanzaRif(String numQuietanzaRif) {
		this.numQuietanzaRif = numQuietanzaRif;
	}
	public BigDecimal getImpVersato() {
		return impVersato;
	}
	public void setImpVersato(BigDecimal impVersato) {
		this.impVersato = impVersato;
	}
	
	public String getFlgQuadratura() {
		return flgQuadratura;
	}
	public void setFlgQuadratura(String flgQuadratura) {
		this.flgQuadratura = flgQuadratura;
	}
	public String getDesQuadratura() {
		return desQuadratura;
	}
	public void setDesQuadratura(String desQuadratura) {
		this.desQuadratura = desQuadratura;
	}
	public String getFlgReperibilita() {
		return flgReperibilita;
	}
	public void setFlgReperibilita(String flgReperibilita) {
		this.flgReperibilita = flgReperibilita;
	}
	public String getDesReperibilita() {
		return desReperibilita;
	}
	public void setDesReperibilita(String desReperibilita) {
		this.desReperibilita = desReperibilita;
	}
	public String getCodTipoVersamento() {
		return codTipoVersamento;
	}
	public void setCodTipoVersamento(String codTipoVersamento) {
		this.codTipoVersamento = codTipoVersamento;
	}
	public String getDesTipoVersamento() {
		return desTipoVersamento;
	}
	public void setDesTipoVersamento(String desTipoVersamento) {
		this.desTipoVersamento = desTipoVersamento;
	}
	public Date getDtRegistrazione() {
		return dtRegistrazione;
	}
	public void setDtRegistrazione(Date dtRegistrazione) {
		this.dtRegistrazione = dtRegistrazione;
	}
	public String getFlgCompetenza() {
		return flgCompetenza;
	}
	public void setFlgCompetenza(String flgCompetenza) {
		this.flgCompetenza = flgCompetenza;
	}
	public String getDesCompetenza() {
		return desCompetenza;
	}
	public void setDesCompetenza(String desCompetenza) {
		this.desCompetenza = desCompetenza;
	}
	public String getComuneImm() {
		return comuneImm;
	}
	public void setComuneImm(String comuneImm) {
		this.comuneImm = comuneImm;
	}
	public String getCapImm() {
		return capImm;
	}
	public void setCapImm(String capImm) {
		this.capImm = capImm;
	}
	
	public String getFlgIdentificazione() {
		return flgIdentificazione;
	}
	public void setFlgIdentificazione(String flgIdentificazione) {
		this.flgIdentificazione = flgIdentificazione;
	}
	public String getDesIdentificazione() {
		return desIdentificazione;
	}
	public void setDesIdentificazione(String desIdentificazione) {
		this.desIdentificazione = desIdentificazione;
	}

	public AnagSoggIciDmDTO getDatiSogg() {
		return datiSogg;
	}
	public void setDatiSogg(AnagSoggIciDmDTO datiSogg) {
		this.datiSogg = datiSogg;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipoTab() {
		return tipoTab;
	}
	public void setTipoTab(String tipoTab) {
		this.tipoTab = tipoTab;
	}
	  
}
