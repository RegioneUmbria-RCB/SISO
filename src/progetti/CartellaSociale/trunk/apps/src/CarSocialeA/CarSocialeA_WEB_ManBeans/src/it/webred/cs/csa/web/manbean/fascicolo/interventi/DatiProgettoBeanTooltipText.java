package it.webred.cs.csa.web.manbean.fascicolo.interventi;

public final class DatiProgettoBeanTooltipText {

	private final String settoreTitolare = "Titolare del servizio, chi possiede la potestà concessoria. L'eventuale esportazione per il SIUSS verrà riferita a questo ente. Solitamente è l'ente di residenza del cittadino ovvero l'ente che riceve la richiesta di intervento."
			+ "Gestisce il servizio con proprie risorse o risorse trasferite da altri enti."; // SISO-1136
	private final String settoreGestore = "E' colui che gestisce e/o coordina il servizio e che ha la necessità di verificare stato e avanzamento delle erogazioni. Potrebbe essere lo stesso titolare o in caso di delega della gestione potrebbe essere un ente limitrofo o l'ente capofila.";

	private final String progettoAltro = "Specificare di quale progetto si tratta, inserire una nuova descrizione o selezionarla dal menu in autocompletamento"; // SISO-1131



	public String getSettoreTitolare() {
		return settoreTitolare;
	}




	public String getSettoreGestore() {
		return settoreGestore;
	}




	/**
	 * @return the progettoAltro
	 */
	public String getProgettoAltro() {
		return progettoAltro;
	}

	
	
}
