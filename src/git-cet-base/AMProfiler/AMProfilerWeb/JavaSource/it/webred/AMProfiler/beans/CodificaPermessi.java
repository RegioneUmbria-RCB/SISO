package it.webred.AMProfiler.beans;


/**
 * Classe contenente la codifica dei permessi dell'applicazione
 * 
 * @author Petracca Marco
 * @version $Revision: 1.2.2.1 $ $Date: 2010/09/07 15:12:11 $
 */
public class CodificaPermessi 
{

	//Nome dell'applicazione
	static public final String NOME_APP = "AMProfiler";
	
	//0ggetti dell'applicazione
	static public final String ITEM_MAPPING = "AMProfiler";

	//	CODIFICA DEI PERMESSI Mapping Fonti Dati	
	static public final String GESTIONE = "Gestione Permessi";
	static public final String VISUALIZZAZIONE= "Visualizzazione";
	static public final String CANCELLA_UTENTE= "Cancella Utente";
	static public final String NUOVO_PERMESSO= "Nuovo Permesso";
	static public final String NUOVO_GRUPPO= "Nuovo Gruppo";
	
	// CODIFICA VALORI campo disable_cause di AM_USER
	static public final String CANCELLAZIONE_LOGICA_UTENTE = "CANCELLED";
	
}
