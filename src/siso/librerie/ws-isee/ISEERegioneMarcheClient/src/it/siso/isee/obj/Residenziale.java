package it.siso.isee.obj;

import java.io.Serializable;

/***
 * (Quadro E – Modulo MB.3)
 * @author Francesco Pellegrini.
 *
 */
public class Residenziale implements Serializable {

	//OBBLIGATORIO
	private String codiceFiscaleBeneficiario;

	//OBBLIGATORIO
	private String dataRichiestaRicovero;

	//OBBLIGATORIO
	private boolean flagFigliNonCompresi;

	//OBBLIGATORIO
	
	private boolean flagDonazioni;

	//FACOLTATIVO
	/***
	 * Flag che indica se il beneficiario ha effettuato donazione di immobili 
	 * successivamente alla prima richiesta della prestazione.
	 * (Caso A del quadro Donazione di Immobili)
	 */
	private boolean flagDonazioniSuccessive;
	
	/***
	 * Flag che indica se il beneficiario ha effettuato donazione di immobili in favore di chi è tenuto agli alimenti 
	 * nei 3 anni precedenti la prima richiesta della prestazione.
	 * (Caso B del quadro Donazione di Immobili)
	 */
	private boolean flagDonazioniPrecedentiAlimenti;
	
	//FACOLTATIVO
	/***
	 *  Flag che indica se il beneficiario ha effettuato donazione di immobili in
	 *  favore di chi NON è tenuto agli alimenti nei 3 anni precedenti la prima richiesta della prestazione.
		(Caso C del quadro Donazione di Immobili)
	 */
	private boolean flagDonazioniPrecedentiNoAlimenti;

	public String getCodiceFiscaleBeneficiario() {
		return codiceFiscaleBeneficiario;
	}

	public void setCodiceFiscaleBeneficiario(String codiceFiscaleBeneficiario) {
		this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
	}

	public String getDataRichiestaRicovero() {
		return dataRichiestaRicovero;
	}

	public void setDataRichiestaRicovero(String dataRichiestaRicovero) {
		this.dataRichiestaRicovero = dataRichiestaRicovero;
	}

	public boolean isFlagFigliNonCompresi() {
		return flagFigliNonCompresi;
	}

	public void setFlagFigliNonCompresi(boolean flagFigliNonCompresi) {
		this.flagFigliNonCompresi = flagFigliNonCompresi;
	}
	/***
	 * Flag che indica se il beneficiario ha effettuato donazione di immobili nei confronti di persone non comprese nel nucleo
	 */
	public boolean isFlagDonazioni() {
		return flagDonazioni;
	}

	public void setFlagDonazioni(boolean flagDonazioni) {
		this.flagDonazioni = flagDonazioni;
	}

	public boolean isFlagDonazioniSuccessive() {
		return flagDonazioniSuccessive;
	}

	public void setFlagDonazioniSuccessive(boolean flagDonazioniSuccessive) {
		this.flagDonazioniSuccessive = flagDonazioniSuccessive;
	}

	public boolean isFlagDonazioniPrecedentiAlimenti() {
		return flagDonazioniPrecedentiAlimenti;
	}

	public void setFlagDonazioniPrecedentiAlimenti(boolean flagDonazioniPrecedentiAlimenti) {
		this.flagDonazioniPrecedentiAlimenti = flagDonazioniPrecedentiAlimenti;
	}

	public boolean isFlagDonazioniPrecedentiNoAlimenti() {
		return flagDonazioniPrecedentiNoAlimenti;
	}

	public void setFlagDonazioniPrecedentiNoAlimenti(boolean flagDonazioniPrecedentiNoAlimenti) {
		this.flagDonazioniPrecedentiNoAlimenti = flagDonazioniPrecedentiNoAlimenti;
	}
	
	
	
}
