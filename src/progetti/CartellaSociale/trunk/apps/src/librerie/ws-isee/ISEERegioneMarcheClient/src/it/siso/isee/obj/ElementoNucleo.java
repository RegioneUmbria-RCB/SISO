package it.siso.isee.obj;

import java.io.Serializable;

public class ElementoNucleo extends AnagraficaBase implements Serializable {
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
	 
	public String getRapportoConDichiarante() {
		return rapportoConDichiarante;
	}
	public void setRapportoConDichiarante(String rapportoConDichiarante) {
		this.rapportoConDichiarante = rapportoConDichiarante;
	}
}
