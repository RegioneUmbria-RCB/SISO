package it.siso.isee.obj;

import java.io.Serializable;

public class SicurezzaIdentificazioneMittente implements Serializable{

     
			private String codiceEnte; //>DSU00081</CodiceEnte>";
			private String codiceUfficio;//>DirStud</CodiceUfficio>";
			private String cfOperatore;//>CCCRRT57P09L500V</CFOperatore>";
			public String getCodiceEnte() {
				return codiceEnte;
			}
			public void setCodiceEnte(String codiceEnte) {
				this.codiceEnte = codiceEnte;
			}
			public String getCodiceUfficio() {
				return codiceUfficio;
			}
			public void setCodiceUfficio(String codiceUfficio) {
				this.codiceUfficio = codiceUfficio;
			}
			public String getCfOperatore() {
				return cfOperatore;
			}
			public void setCfOperatore(String cfOperatore) {
				this.cfOperatore = cfOperatore;
			}
			
			
}
