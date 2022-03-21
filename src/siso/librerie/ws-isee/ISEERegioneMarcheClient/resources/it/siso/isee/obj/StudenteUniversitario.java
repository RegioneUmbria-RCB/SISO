package it.siso.isee.obj;

import java.io.Serializable;

public class StudenteUniversitario implements Serializable {

	private AnagraficaAgg attributi;
	
	/***
	 * Caso che coincide con ISEEOrdinario 
	 * non c’è ulteriore specificazione degli indicatori perché già indicato nell’elemento <ISEEOrdinario> tabella 1) dell’Attestazione
	 */
	private String iseeOrd;
	
	/**
	 * Caso dello studente con genitore non convivente che entra come 
	 * componente aggiuntiva, ma i dati della componente aggiuntiva non sono stai forniti.
	 */
	private String iseeNonCalcolabile;
	
	public String getIseeNonCalcolabile() {
		return iseeNonCalcolabile;
	}

	public void setIseeNonCalcolabile(String iseeNonCalcolabile) {
		this.iseeNonCalcolabile = iseeNonCalcolabile;
	}

	/***
	 * Caso del minore con genitore non convivente che entra come componente attratta, 
	 * i dati del genitore attratto sono indicati nell’elemento ComponenteAttratta
	 */
	private ISEEAgg iseeUniAttr;
	
	private ISEEAgg iseeUniAgg;
	
	private ISEEUniStudAttratto iseeUniStudAttratto;
	
	private ISEEUniStudAttratto iseeUniStudAttrattoCompAttratta;
	
	private ISEEUniStudAttrattoCompAggiuntiva iseeUniStudAttrattoCompAggiuntiva;

	
	
	
	public ISEEUniStudAttratto getIseeUniStudAttratto() {
		return iseeUniStudAttratto;
	}

	public void setIseeUniStudAttratto(ISEEUniStudAttratto iseeUniStudAttratto) {
		this.iseeUniStudAttratto = iseeUniStudAttratto;
	}

	public AnagraficaAgg getAttributi() {
		return attributi;
	}

	public void setAttributi(AnagraficaAgg attributi) {
		this.attributi = attributi;
	}

	public String getIseeOrd() {
		return iseeOrd;
	}

	public void setIseeOrd(String iseeOrd) {
		this.iseeOrd = iseeOrd;
	}

	public ISEEAgg getIseeUniAttr() {
		return iseeUniAttr;
	}

	public void setIseeUniAttr(ISEEAgg iseeUniAttr) {
		this.iseeUniAttr = iseeUniAttr;
	}

	public ISEEAgg getIseeUniAgg() {
		return iseeUniAgg;
	}

	public void setIseeUniAgg(ISEEAgg iseeUniAgg) {
		this.iseeUniAgg = iseeUniAgg;
	}

	public ISEEUniStudAttratto getIseeUniStudAttrattoCompAttratta() {
		return iseeUniStudAttrattoCompAttratta;
	}

	public void setIseeUniStudAttrattoCompAttratta(
			ISEEUniStudAttratto iseeUniStudAttrattoCompAttratta) {
		this.iseeUniStudAttrattoCompAttratta = iseeUniStudAttrattoCompAttratta;
	}

	public ISEEUniStudAttrattoCompAggiuntiva getIseeUniStudAttrattoCompAggiuntiva() {
		return iseeUniStudAttrattoCompAggiuntiva;
	}

	public void setIseeUniStudAttrattoCompAggiuntiva(
			ISEEUniStudAttrattoCompAggiuntiva iseeUniStudAttrattoCompAggiuntiva) {
		this.iseeUniStudAttrattoCompAggiuntiva = iseeUniStudAttrattoCompAggiuntiva;
	}
	
	
}
