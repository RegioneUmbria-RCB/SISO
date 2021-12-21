package it.umbriadigitale.soclav.model.anpal;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class RdCTbCodiciSapPK implements Serializable {

	    private String tabella;
		
		private String codice;

		public String getTabella() {
			return tabella;
		}

		public void setTabella(String tabella) {
			this.tabella = tabella;
		}

		public String getCodice() {
			return codice;
		}

		public void setCodice(String codice) {
			this.codice = codice;
		}
}
