package it.siso.isee.obj;

import java.io.Serializable;

public class ComponenteSocioSanitario implements Serializable{

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
}
