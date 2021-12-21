package it.umbriadigitale.soclav.service.dto.anpal;

import java.io.Serializable;
import java.sql.Date;

import it.umbriadigitale.soclav.service.dto.sap.lavoratore.DatiAnagrafici;

public class AnpalBeneficiarioDTO extends DatiAnagrafici implements Serializable {

	private Date dataDecorrenzaBeneficio;

	private Date ultimaModifica;
	
	private boolean richiedente;
	
	private String codSAP;
	
	private String cpiDenominazione;
	
	//Dati elaborati
	private boolean visualizzaDatiSociale;
	private Boolean consensoRilasciato;
	
	public Date getDataDecorrenzaBeneficio() {
		return dataDecorrenzaBeneficio;
	}

	public void setDataDecorrenzaBeneficio(Date dataDecorrenzaBeneficio) {
		this.dataDecorrenzaBeneficio = dataDecorrenzaBeneficio;
	}

	public Date getUltimaModifica() {
		return ultimaModifica;
	}

	public void setUltimaModifica(Date ultimaModifica) {
		this.ultimaModifica = ultimaModifica;
	}

	public boolean isRichiedente() {
		return richiedente;
	}

	public void setRichiedente(boolean richiedente) {
		this.richiedente = richiedente;
	}

	public String getCodSAP() {
		return codSAP;
	}

	public void setCodSAP(String codSAP) {
		this.codSAP = codSAP;
	}

	public boolean isVisualizzaDatiSociale() {
		return visualizzaDatiSociale;
	}

	public void setVisualizzaDatiSociale(boolean visualizzaDatiSociale) {
		this.visualizzaDatiSociale = visualizzaDatiSociale;
	}

	public Boolean getConsensoRilasciato() {
		return consensoRilasciato;
	}

	public void setConsensoRilasciato(Boolean consensoRilasciato) {
		this.consensoRilasciato = consensoRilasciato;
	}

	public String getCpiDenominazione() {
		return cpiDenominazione;
	}

	public void setCpiDenominazione(String cpiDenominazione) {
		this.cpiDenominazione = cpiDenominazione;
	}
	
	
}
