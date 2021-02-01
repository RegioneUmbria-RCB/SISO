package eu.smartpeg.rilevazionepresenze.datautil;

public class DataModelCostanti {

	public static String CODICE_CATASTALE_MUNICIPI_ROMA =  "H501";

	public static class Villaggi{
		
		public enum TIPO_VILLAGGIO {
			ATTREZZATO("Attrezzato"), // --> 0
			TOLLERATO("Tollerato");  // --> 1
			
			String descrizione;
	        Long codice;
			
	        private TIPO_VILLAGGIO (String descrizione) {
				this.descrizione = descrizione;
			}

			public String getDescrizione() {return descrizione;}
			public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
			public long getCodice() {return codice;}
			public void setCodice(long codice) {this.codice = codice;}
		}
		
	}
	
}
