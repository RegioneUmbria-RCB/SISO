package it.webred.cs.json.provvedimentiMinori.ver1.tabs;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatiProvvedimentoBean {

	private String selDatiProvv;
	private boolean richiestaIndagine;
	private String selTipoProvv;
	private String selNaturaProvv;
	private String[] selTipoAffidamento;

	private String[] selCessazionePotestaPadre;
	private String[] selCessazionePotestaMadre;
	//private String selAffidatoA;
	private String numProvv;
	private Date dataProvvCancelleria;
	private Date dataProtocolloProvv;
	private String curatore;
	private boolean legaleFamiglia;
	
	private Long   idTutore;
	private String tutoreDenominazione;
	private String tutoreIndirizzo;
	private String tutoreCitta;
	private String tutoreTelefono;

	private String[] selIndagine;
	private String note;

	public DatiProvvedimentoBean()
	{
		this.selDatiProvv = "Tribunale Ordinario";
		this.selTipoProvv = "Non definito";
		this.selNaturaProvv = "Penale Attivo";
		//this.selAffidatoA = "Entrambi";
	}

	public String getSelDatiProvv() {
		return selDatiProvv;
	}

	public void setSelDatiProvv(String selDatiProvv) {
		this.selDatiProvv = selDatiProvv;
	}

	public String getSelTipoProvv() {
		return selTipoProvv;
	}

	public void setSelTipoProvv(String selTipoProvv) {
		this.selTipoProvv = selTipoProvv;
	}

	public String getSelNaturaProvv() {
		return selNaturaProvv;
	}

	public void setSelNaturaProvv(String selNaturaProvv) {
		this.selNaturaProvv = selNaturaProvv;
	}

	public String[] getSelTipoAffidamento() {
		return selTipoAffidamento;
	}

	public void setSelTipoAffidamento(String[] selTipoAffidamento) {
		this.selTipoAffidamento = selTipoAffidamento;
	}

	public String getNumProvv() {
		return numProvv;
	}

	public void setNumProvv(String numProvv) {
		this.numProvv = numProvv;
	}

	public Date getDataProvvCancelleria() {
		return dataProvvCancelleria;
	}

	public void setDataProvvCancelleria(Date dataProvvCancelleria) {
		this.dataProvvCancelleria = dataProvvCancelleria;
	}

	public Date getDataProtocolloProvv() {
		return dataProtocolloProvv;
	}

	public void setDataProtocolloProvv(Date dataProtocolloProvv) {
		this.dataProtocolloProvv = dataProtocolloProvv;
	}

	public String getCuratore() {
		return curatore;
	}

	public void setCuratore(String curatore) {
		this.curatore = curatore;
	}

	public boolean isLegaleFamiglia() {
		return legaleFamiglia;
	}

	public void setLegaleFamiglia(boolean legaleFamiglia) {
		this.legaleFamiglia = legaleFamiglia;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getIdTutore() {
		return idTutore;
	}

	public void setIdTutore(Long idTutore) {
		this.idTutore = idTutore;
	}

	public String getTutoreDenominazione() {
		return tutoreDenominazione;
	}

	public void setTutoreDenominazione(String tutoreDenominazione) {
		this.tutoreDenominazione = tutoreDenominazione;
	}

	public String getTutoreIndirizzo() {
		return tutoreIndirizzo;
	}

	public void setTutoreIndirizzo(String tutoreIndirizzo) {
		this.tutoreIndirizzo = tutoreIndirizzo;
	}

	public String getTutoreCitta() {
		return tutoreCitta;
	}

	public void setTutoreCitta(String tutoreCitta) {
		this.tutoreCitta = tutoreCitta;
	}

	public String getTutoreTelefono() {
		return tutoreTelefono;
	}

	public void setTutoreTelefono(String tutoreTelefono) {
		this.tutoreTelefono = tutoreTelefono;
	}

	public String[] getSelCessazionePotestaPadre() {
		return selCessazionePotestaPadre;
	}

	public void setSelCessazionePotestaPadre(String[] selCessazionePotestaPadre) {
		this.selCessazionePotestaPadre = selCessazionePotestaPadre;
	}

	public String[] getSelCessazionePotestaMadre() {
		return selCessazionePotestaMadre;
	}

	public void setSelCessazionePotestaMadre(String[] selCessazionePotestaMadre) {
		this.selCessazionePotestaMadre = selCessazionePotestaMadre;
	}

	public String[] getSelIndagine() {
		return selIndagine;
	}

	public void setSelIndagine(String[] selIndagine) {
		this.selIndagine = selIndagine;
	}

	public boolean isRichiestaIndagine() {
		return richiestaIndagine;
	}

	public void setRichiestaIndagine(boolean richiestaIndagine) {
		this.richiestaIndagine = richiestaIndagine;
	}

}
