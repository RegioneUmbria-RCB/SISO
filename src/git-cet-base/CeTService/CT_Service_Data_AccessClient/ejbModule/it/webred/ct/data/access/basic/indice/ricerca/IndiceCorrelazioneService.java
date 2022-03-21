package it.webred.ct.data.access.basic.indice.ricerca;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface IndiceCorrelazioneService {
	public final String LISTA_CORRELAZIONI_OGGETTI_DIRETTE="LISTA_CORRELAZIONI_OGGETTI_DIRETTE@IndiceCorrelazioneService";
	public final String LISTA_CORRELAZIONI_OGGETTI_INDIRETTE="LISTA_CORRELAZIONI_OGGETTI_INDIRETTE@IndiceCorrelazioneService";
	public final String LISTA_CORRELAZIONI_OGGETTI="LISTA_CORRELAZIONI_OGGETTI@IndiceCorrelazioneService";
	
	public final String LISTA_CORRELAZIONI_FABBRICATO_DIRETTE="LISTA_CORRELAZIONI_FABBRICATO_DIRETTE@IndiceCorrelazioneService";
	public final String LISTA_CORRELAZIONI_FABBRICATO_INDIRETTE="LISTA_CORRELAZIONI_FABBRICATO_INDIRETTE@IndiceCorrelazioneService";
	public final String LISTA_CORRELAZIONI_FABBRICATO="LISTA_CORRELAZIONI_FABBRICATO@IndiceCorrelazioneService";
	
	//METODI DI RICERCA DIRETTI PER ENTITY 
	public List<Object>  getSoggettiCorrelati(RicercaIndiceDTO soggetto);
	
	public List<Object>  getVieCorrelate(RicercaIndiceDTO via);

	public List<Object>  getCiviciCorrelati(RicercaIndiceDTO via);
	
	public List<Object>  getFabbricatiCorrelati(RicercaIndiceDTO via);
	
	public List<Object>  getOggettiCorrelati(RicercaIndiceDTO oggetto);
	//METODI DI RICERCA DIRETTI PER ID 
	public List<Object>  getOggettiCorrelatiById(RicercaIndiceDTO oggetto);
		
	//METODI DI RICERCA CORRELAZIONI INDIRETTE
	public List<Object>  getCiviciCorrelatiFromFabbricato(RicercaIndiceDTO fabbricato);

	public List<Object> getCiviciCorrelatiFromUI(RicercaIndiceDTO rf) ;
	
	//METODI DI RICERCA DI TUTTE LE CORRELAZIONI
	public List<Object> getCorrelazioniOggettiFromUI(RicercaIndiceDTO rf);
	public Hashtable<String,  List<Object>> getCorrelazioniOggettiFromUI(RicercaIndiceDTO rf, boolean dueListe);
	public Hashtable<String,  List<Object>> getCorrelazioniFabbricatoFromFabbricato(RicercaIndiceDTO rf, boolean dueListe); 
}
