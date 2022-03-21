package it.siso.isee.obj;

import java.io.Serializable;

/***
 * (Quadro A – Modulo MB.1rid)
 * @author Francesco Pellegrini
 *Elemento obbligatorio ripetuto tante volte quanti componenti sono inclusi nel nucleo ristretto.
 */
public class ComponenteNucleoRistretto implements Serializable {

	private AnagraficaBase anagrafica;
	
	private boolean flagBeneficiario;
	/***
	 * Relazione rispetto al Beneficiario.
		B - Beneficiario
		C – Coniuge
		F – Figlio Minorenne
		MA – Minore in Affidamento preadottivo
		FC – Figlio Maggiorenne convivente
		FNC – Figlio Maggiorenne non convivente
	 */
	private String relazioneConBeneficiario;
	 
	
	
	public AnagraficaBase getAnagrafica() {
		return anagrafica;
	}
	public void setAnagrafica(AnagraficaBase anagrafica) {
		this.anagrafica = anagrafica;
	}
	public boolean isFlagBeneficiario() {
		return flagBeneficiario;
	}
	public void setFlagBeneficiario(boolean flagBeneficiario) {
		this.flagBeneficiario = flagBeneficiario;
	}
	public String getRelazioneConBeneficiario() {
		return relazioneConBeneficiario;
	}
	public void setRelazioneConBeneficiario(String relazioneConBeneficiario) {
		this.relazioneConBeneficiario = relazioneConBeneficiario;
	}
	
	
}
