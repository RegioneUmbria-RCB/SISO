package it.siso.isee.obj;

import java.io.Serializable;
/**
 * (Modulo FC.4 - sez. II)
 * @author Francesco Pellegrini
 *
 */
public class NucleoFamiliareComponenti implements Serializable {

	//OBBLIGATORIO
	private String numeroComponenti;
	
	/**
	 * Flag che indica se nel nucleo familiare della componente aggiuntiva, in presenza di figli minorenni, entrambi i genitori,
	 *  o l’unico genitore presente, hanno svolto attività di lavoro o di impresa per almeno sei mesi nell’anno di riferimento dei redditi dichiarati.
	 */
	private boolean flagAttivitaLavorativa;
	
	/***
	 * OBBLIGATORIO
	 * Flag che indica se nel nucleo della componente aggiuntiva è presente almeno un minorenne inferiore a tre anni.
	 */
	private boolean flagPresenzaMinorenne;
	
	/**
	 * FACOLTATIVO
	 * Attributo facoltativo, numero figli maggiori di del nucleo familiare della componente aggiuntiva.
	 */
	private int numeroFigli;
	
	/***
	 * facoltativo, numero componenti del nucleo familiare della componente aggiuntiva.
	 */
	private int numeroFigliConviventi;
	
	//OBBLIGATORIO
	private boolean flagPresenzaRicoverato;
	
	//FACOLTATIVO
	private int numeroRicoverati;

	private boolean flagPresenzaConvivente;
	
	/***
	 * numero delle persone in convivenza anagrafica.
	 * FACOLTATIVO
	 */
	private int numeroConviventi;
	/***
	 * OBBLIGATORIO
	 * Flag che indica se nel nucleo della componente aggiuntiva fa parte uno o più componenti con disabilità media.
	 */
	private boolean flagDisabilitaMedia;
	
	private int numeroDisabiliMedia;
	
	private int numeroDisabiliMediaMinorenni;
	//OBBLIGATORIO
	private boolean flagDisabilitaGrave;
	
	private int numeroDisabiliGrave;
	
	private int numeroDisabiliGraveMinorenni;
	
	private boolean flagNonAutosufficienti;
	
	//FACOLTATIVO
	private int numeroNonAutosuff;
	//FACOLTATIVO
	private int numeroNonAutosuffMinorenni;
	public String getNumeroComponenti() {
		return numeroComponenti;
	}
	public void setNumeroComponenti(String numeroComponenti) {
		this.numeroComponenti = numeroComponenti;
	}
	public boolean isFlagAttivitaLavorativa() {
		return flagAttivitaLavorativa;
	}
	public void setFlagAttivitaLavorativa(boolean flagAttivitaLavorativa) {
		this.flagAttivitaLavorativa = flagAttivitaLavorativa;
	}
	public boolean isFlagPresenzaMinorenne() {
		return flagPresenzaMinorenne;
	}
	public void setFlagPresenzaMinorenne(boolean flagPresenzaMinorenne) {
		this.flagPresenzaMinorenne = flagPresenzaMinorenne;
	}
	public int getNumeroFigli() {
		return numeroFigli;
	}
	public void setNumeroFigli(int numeroFigli) {
		this.numeroFigli = numeroFigli;
	}
	public int getNumeroFigliConviventi() {
		return numeroFigliConviventi;
	}
	public void setNumeroFigliConviventi(int numeroFigliConviventi) {
		this.numeroFigliConviventi = numeroFigliConviventi;
	}
	public boolean isFlagPresenzaRicoverato() {
		return flagPresenzaRicoverato;
	}
	public void setFlagPresenzaRicoverato(boolean flagPresenzaRicoverato) {
		this.flagPresenzaRicoverato = flagPresenzaRicoverato;
	}
	public int getNumeroRicoverati() {
		return numeroRicoverati;
	}
	public void setNumeroRicoverati(int numeroRicoverati) {
		this.numeroRicoverati = numeroRicoverati;
	}
	public boolean isFlagPresenzaConvivente() {
		return flagPresenzaConvivente;
	}
	public void setFlagPresenzaConvivente(boolean flagPresenzaConvivente) {
		this.flagPresenzaConvivente = flagPresenzaConvivente;
	}
	public int getNumeroConviventi() {
		return numeroConviventi;
	}
	public void setNumeroConviventi(int numeroConviventi) {
		this.numeroConviventi = numeroConviventi;
	}
	public boolean isFlagDisabilitaMedia() {
		return flagDisabilitaMedia;
	}
	public void setFlagDisabilitaMedia(boolean flagDisabilitaMedia) {
		this.flagDisabilitaMedia = flagDisabilitaMedia;
	}
	public int getNumeroDisabiliMedia() {
		return numeroDisabiliMedia;
	}
	public void setNumeroDisabiliMedia(int numeroDisabiliMedia) {
		this.numeroDisabiliMedia = numeroDisabiliMedia;
	}
	public int getNumeroDisabiliMediaMinorenni() {
		return numeroDisabiliMediaMinorenni;
	}
	public void setNumeroDisabiliMediaMinorenni(int numeroDisabiliMediaMinorenni) {
		this.numeroDisabiliMediaMinorenni = numeroDisabiliMediaMinorenni;
	}
	public boolean isFlagDisabilitaGrave() {
		return flagDisabilitaGrave;
	}
	public void setFlagDisabilitaGrave(boolean flagDisabilitaGrave) {
		this.flagDisabilitaGrave = flagDisabilitaGrave;
	}
	public int getNumeroDisabiliGrave() {
		return numeroDisabiliGrave;
	}
	public void setNumeroDisabiliGrave(int numeroDisabiliGrave) {
		this.numeroDisabiliGrave = numeroDisabiliGrave;
	}
	public int getNumeroDisabiliGraveMinorenni() {
		return numeroDisabiliGraveMinorenni;
	}
	public void setNumeroDisabiliGraveMinorenni(int numeroDisabiliGraveMinorenni) {
		this.numeroDisabiliGraveMinorenni = numeroDisabiliGraveMinorenni;
	}
	public boolean isFlagNonAutosufficienti() {
		return flagNonAutosufficienti;
	}
	public void setFlagNonAutosufficienti(boolean flagNonAutosufficienti) {
		this.flagNonAutosufficienti = flagNonAutosufficienti;
	}
	public int getNumeroNonAutosuff() {
		return numeroNonAutosuff;
	}
	public void setNumeroNonAutosuff(int numeroNonAutosuff) {
		this.numeroNonAutosuff = numeroNonAutosuff;
	}
	public int getNumeroNonAutosuffMinorenni() {
		return numeroNonAutosuffMinorenni;
	}
	public void setNumeroNonAutosuffMinorenni(int numeroNonAutosuffMinorenni) {
		this.numeroNonAutosuffMinorenni = numeroNonAutosuffMinorenni;
	}
	
	
	
	
}
