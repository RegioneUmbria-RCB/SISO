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
	
	public static class StatiRichiesta {
		public enum STATO_RICHIESTA {
			INVIATA("INVIATA","Inviata"),
			ACCETTATA_STRUTTURA("ACCETTATA_STRUTTURA","Accettata"),
			ACCETTATA("ACCETTATA","Accettata da Ente"),
			RIFIUTATA_STRUTTURA("RIFIUTATA_STRUTTURA","Rifiutata"),
			RIFIUTATA("RIFIUTATA","Rifiutata da Ente");
			
			private String codice;
			private String descrizione;
			private STATO_RICHIESTA(String codice, String descrizione) {
				this.codice = codice;
				this.descrizione = descrizione;
			}
			public String getCodice() {
				return codice;
			}
			public void setCodice(String codice) {
				this.codice = codice;
			}
			public String getDescrizione() {
				return descrizione;
			}
			public void setDescrizione(String descrizione) {
				this.descrizione = descrizione;
			}
						
			
		}
		
	}
	
	public static class AmParameterKey {
		public static String TIPO_FUNZIONE_STRUTTURA = "smartwelfare.rp.tipo.funzione.struttura";
	}
	
}
