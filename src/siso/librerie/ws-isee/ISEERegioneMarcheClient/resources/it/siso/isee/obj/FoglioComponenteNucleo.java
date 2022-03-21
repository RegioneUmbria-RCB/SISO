package it.siso.isee.obj;

import java.io.Serializable;

/***
 * Elemento obbligatorio che è ripetuto tante volte quanti componenti ci sono nel nucleo familiare.
 *  All’interno di questo elemento sono contenuti tutti dati anagrafici e reddituali del componente il Nucleo Familiare (Modulo FC).
 * @author Francesco Pellegrini
 *
 */
public class FoglioComponenteNucleo implements Serializable {

	private DatiComponente datiComponete;

	public DatiComponente getDatiComponete() {
		return datiComponete;
	}

	public void setDatiComponete(DatiComponente datiComponete) {
		this.datiComponete = datiComponete;
	}
	
	private String codiceFiscale;

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	
}
