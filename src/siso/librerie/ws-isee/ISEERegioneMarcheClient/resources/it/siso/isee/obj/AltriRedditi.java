package it.siso.isee.obj;

import java.io.Serializable;

/***
 * (Quadro FC8 – Modulo FC.3)
 * @author franc
 *
 */
public class AltriRedditi implements Serializable {

	//Flag che indica se persona è esonerata alla presentazione della dichiarazione dei redditi.
	//Questo Flag sarà valorizzato positivo, qualora l’elemento AltriRedditi è contestuale alla dichiarazione.
	private boolean flagEsonerato;
	//Flag che indica se trattasi di integrazione o rettifica dei redditi da indicare in casi eccezionali.
	//Questo Flag sarà valorizzato positivo, qualora l’elemento AltriRedditi non è contestuale alla dichiarazione.
	private boolean flagIntegrazione;
	
	private boolean flagRettifica;
	//Reddito complessivo ai fini IRPEF
	private Integer redditoIRPEF;
	//Reddito da lavoratore dipendente
	private Integer lavoroDipendente;
	
	private Integer pensione;
	
	private Integer redditiAgrari;
	//Redditi assoggetati ad imposta sostitutiva o a ritenuta a titolo d’imposta..
	private Integer redditiImpSost;
	
	//Trattamenti assistenziali, previdenziali e indennitari erogati dall’INPS non soggetti a IRPEF Valore del rapporto finanziario.
	private Integer trattamentiAssistenziali;
	//Spese per le quali spetta la detrazione d’imposta.
	private Integer speseDetrazione;
	//Spese per le quali spetta la deduzione dal reddito complessivo.
	private Integer spseseDeduzione;
	//Spese sostenute, inclusive dei contributi versati, per collaboratori domestici ed addetti all’assistenza personale
	private Integer speseCollabDomestici;
	public boolean isFlagEsonerato() {
		return flagEsonerato;
	}
	public void setFlagEsonerato(boolean flagEsonerato) {
		this.flagEsonerato = flagEsonerato;
	}
	public boolean isFlagIntegrazione() {
		return flagIntegrazione;
	}
	public void setFlagIntegrazione(boolean flagIntegrazione) {
		this.flagIntegrazione = flagIntegrazione;
	}
	public boolean isFlagRettifica() {
		return flagRettifica;
	}
	public void setFlagRettifica(boolean flagRettifica) {
		this.flagRettifica = flagRettifica;
	}
	public Integer getRedditoIRPEF() {
		return redditoIRPEF;
	}
	public void setRedditoIRPEF(Integer redditoIRPEF) {
		this.redditoIRPEF = redditoIRPEF;
	}
	public Integer getLavoroDipendente() {
		return lavoroDipendente;
	}
	public void setLavoroDipendente(Integer lavoroDipendente) {
		this.lavoroDipendente = lavoroDipendente;
	}
	public Integer getPensione() {
		return pensione;
	}
	public void setPensione(Integer pensione) {
		this.pensione = pensione;
	}
	public Integer getRedditiAgrari() {
		return redditiAgrari;
	}
	public void setRedditiAgrari(Integer redditiAgrari) {
		this.redditiAgrari = redditiAgrari;
	}
	public Integer getRedditiImpSost() {
		return redditiImpSost;
	}
	public void setRedditiImpSost(Integer redditiImpSost) {
		this.redditiImpSost = redditiImpSost;
	}
	public Integer getTrattamentiAssistenziali() {
		return trattamentiAssistenziali;
	}
	public void setTrattamentiAssistenziali(Integer trattamentiAssistenziali) {
		this.trattamentiAssistenziali = trattamentiAssistenziali;
	}
	public Integer getSpeseDetrazione() {
		return speseDetrazione;
	}
	public void setSpeseDetrazione(Integer speseDetrazione) {
		this.speseDetrazione = speseDetrazione;
	}
	public Integer getSpseseDeduzione() {
		return spseseDeduzione;
	}
	public void setSpseseDeduzione(Integer spseseDeduzione) {
		this.spseseDeduzione = spseseDeduzione;
	}
	public Integer getSpeseCollabDomestici() {
		return speseCollabDomestici;
	}
	public void setSpeseCollabDomestici(Integer speseCollabDomestici) {
		this.speseCollabDomestici = speseCollabDomestici;
	}
	
	
}
