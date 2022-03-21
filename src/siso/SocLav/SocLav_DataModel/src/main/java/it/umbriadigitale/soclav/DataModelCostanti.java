package it.umbriadigitale.soclav;

public class DataModelCostanti {

	public static String ISTANZA = "SocLav";
	
	public static class PermessiGenerali {
		public static final String ITEM = "SocLav";
		
		public static final String VISUALIZZA_LISTA_ANPAL = "Visualizza beneficiari ANPAL";
		public static final String VISUALIZZA_LISTA_GEPI = "Visualizza beneficiari GEPI";
		public static final String VISUALIZZA_LISTA_COMPLETA_RDC = "Visualizza tutti i beneficiari RdC";
		public static final String CONFIGURAZIONE_UTENTI = "Gestisci configurazione utenti";
		public static final String GESTIONE_CONSENSI = "Gestisci consensi";
		
	}
	
	public static class Percorsi{
		public static final String PATH_AUTENTICAZIONE="/rest/login/access";
		public static final String PATH_PERMESSI = "/rest/login/getPermessiUtente";
		public static final String PATH_PRIK = "/rest/login/verificaPriK";
	}
}
