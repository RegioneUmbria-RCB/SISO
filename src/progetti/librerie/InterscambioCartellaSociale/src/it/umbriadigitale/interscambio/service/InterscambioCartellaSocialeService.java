package it.umbriadigitale.interscambio.service;

import org.hl7.v3.request.PRSSIN001004ZZ;

import it.umbriadigitale.interscambio.data.wrapper.CartellaSocialeWrapper;
import it.umbriadigitale.interscambio.data.wrapper.MessageDataWrapper;
import it.umbriadigitale.interscambio.factory.MessageFactory;

/**
 * Servizio di generazione XML per Interscambio Cartella Sociale
 * 
 * @author Iacopo Sorce
 */
public class InterscambioCartellaSocialeService {
	/**
	 * Costruisce e restituisce l'elemento root della request ({@link org.hl7.v3.request.PRSSIN001004ZZ PRSS_IN001004ZZ})
	 * di Interscambio Cartella Sociale a partire dai wrapper delle informazioni <code>messageData</code> e <code>cartellaSocialeData</code>
	 * 
	 * @param messageData			Contiene le informazioni relative allo scambio messaggi dell'operazione di Interscambio Cartella Sociale
	 * @param cartellaSocialeData	Contiene i dati utili della Cartella Sociale oggetto dell'esportazione
	 * @return	{@link org.hl7.v3.request.PRSSIN001004ZZ} popolato a partire dai dati contenuti in <code>messageData</code> e
	 * 			<code>cartellaSocialeData</code>
	 */
	public PRSSIN001004ZZ generateRequest(MessageDataWrapper messageData, CartellaSocialeWrapper cartellaSocialeData) {
		return MessageFactory.createRequest(messageData, cartellaSocialeData);
	}
}
