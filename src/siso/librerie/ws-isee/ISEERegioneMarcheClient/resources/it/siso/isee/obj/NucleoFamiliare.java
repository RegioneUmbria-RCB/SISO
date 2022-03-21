package it.siso.isee.obj;

import java.io.Serializable;
/***
 * (Quadro A - Modello MB.1),
 * @author franc
 *
 */
public class NucleoFamiliare implements Serializable {

	private Anagrafica anagrafica;
	/***
	 * 	 D Dichiarante 
		 C Coniuge 
		 F Figlio Minorenne 
		 MA Figlio in Affidamento preadottivo 
		 FC Figlio Maggiorenne convivente 
		 FNC Figlio Maggiorenne non convivente (a carico ai fini IRPEF) 
		 P Altra persona convivente 
		 GNC Genitore NON Coniugato e NON convivente - Valore da indicare per la componente attratta nel Nucleo N.B da non confondere con la componente aggiuntiva 
	 */
	private String rapportoConDichiarante;
	public Anagrafica getAnagrafica() {
		return anagrafica;
	}
	public void setAnagrafica(Anagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}
	public String getRapportoConDichiarante() {
		return rapportoConDichiarante;
	}
	public void setRapportoConDichiarante(String rapportoConDichiarante) {
		this.rapportoConDichiarante = rapportoConDichiarante;
	}
	
}
