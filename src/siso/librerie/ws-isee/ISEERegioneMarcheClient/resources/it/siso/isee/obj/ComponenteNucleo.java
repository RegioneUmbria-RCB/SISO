package it.siso.isee.obj;

import java.io.Serializable;

/***
 * Quadro A (MB.1 – MB.1rid)
 * @author franc
 *
 */
public class ComponenteNucleo extends ElementoNucleo implements Serializable{

	
	/***
	 *  Solo per la DICHIARAZIONE
	 *  Attributo obbligatorio. Tipo Dati: booleano
		Può essere valorizzato positivo solo per i minorenni.
	 */
	private boolean flagAssenzaReddito = false;
	
	private FoglioComponenteNucleo foglioCompopneteNucleo;

	public boolean isFlagAssenzaReddito() {
		return flagAssenzaReddito;
	}
	public void setFlagAssenzaReddito(boolean flagAssenzaReddito) {
		this.flagAssenzaReddito = flagAssenzaReddito;
	}
	public FoglioComponenteNucleo getFoglioCompopneteNucleo() {
		return foglioCompopneteNucleo;
	}
	public void setFoglioCompopneteNucleo(FoglioComponenteNucleo foglioCompopneteNucleo) {
		this.foglioCompopneteNucleo = foglioCompopneteNucleo;
	}
	
	
}
