package it.webred.cs.csa.web.manbean.amministrazione;

import it.umbriadigitale.interscambio.data.wrapper.MessageDataWrapper;
import it.webred.utilities.DateTimeUtils;

import javax.xml.bind.JAXBException;

public class IterCartellaSociale extends BaseIterCartellaSociale {

 
	 
	// TODO al momento riempitivo; verosimilmente, riceverà in ingresso strutture dati da cui estrarre le informazioni necessarie
	private MessageDataWrapper buildMessageData() {
		return new MessageDataWrapper(
			"idMessaggio",						// id
			todayDdmmyyyy(),					// dataCreazioneText
			"idInterazione",					// idInterazione,
			"tipologiaProcesso",				// tipologiaProcesso
			"tipologiaModalitaProcessamento", 	// tipologiaModalitaProcessamento
			"acceptAckCode",					// acceptAckCode
			"idMittente",						// idMittente
			"idDestinatario"					// idDestinatario
		);
	}
	
	private String todayDdmmyyyy() {
		return DateTimeUtils.dateToString(new java.util.Date(), "ddMMyyyy");
	}
}

