package it.webred.cs.csa.web.manbean.interscambio;

import javax.xml.bind.JAXBException;

import it.umbriadigitale.argo.ejb.client.cs.dto.OrgInterscambioDTO;
import it.umbriadigitale.interscambio.data.wrapper.CartellaSocialeWrapper;
import it.umbriadigitale.interscambio.data.wrapper.MessageDataWrapper;
import it.umbriadigitale.interscambio.service.InterscambioCartellaSocialeService;
import it.umbriadigitale.interscambio.utils.XMLPrinterUtils;
import it.webred.cs.csa.ejb.dto.AlertDTO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.EventoDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.utilities.DateTimeUtils;

public class InterscambioCartellaSociale extends BaseInterscambioCartellaSociale {

	public void creaNotifica(AlertDTO adto, CsACaso caso, CsOOperatoreSettore operatoreSettore, String operazione) throws Exception {

		// FROM
		adto.setOrganizzazioneFrom(operatoreSettore.getCsOSettore().getCsOOrganizzazione());
		adto.setSettoreFrom(operatoreSettore.getCsOSettore());
		adto.setOperatoreFrom(operatoreSettore.getCsOOperatore());

		// TO
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(caso.getId()); 
		
		//Cerco l'operatore responsabile (o creatore) del caso
		CsOOperatoreSettore responsabile = casoService.findDestinatarioAlertCaso(dto);
		adto.setOpSettoreTo(responsabile);

		//TIPO NOTIFICA
		adto.setTipo(DataModelCostanti.TipiAlertCod.IMPORT_EXPORT);
		//DENOMINAZIONE
		String denominazione = caso.getCsASoggetto().getCsAAnagrafica()
				.getCognome()
				+ " "
				+ caso.getCsASoggetto().getCsAAnagrafica().getNome()
				+ " (" + caso.getCsASoggetto().getCsAAnagrafica().getCf() + ")";
		//DESCRIZIONE
		String descrizione = "L'operatore "
				+ operatoreSettore.getCsOOperatore().getCsOOperatoreAnagrafica()
						.getCognome()
				+ " "
				+ operatoreSettore.getCsOOperatore().getCsOOperatoreAnagrafica()
						.getNome()
				+ " ha effettuato l'" + operazione + " della cartella relativa al caso "
				+ denominazione.toUpperCase();

		//TITOLO ESTESO
		String titDescrizione = "Notifica: " + operazione + " " + denominazione;
		
		adto.setDescrizione(descrizione);
		adto.setTitolo(titDescrizione);
        
		//TODO: Controllare le seguenti due righe 
		adto.setSettoreTo(responsabile.getCsOSettore());
		
		alertService.addAlert(adto);

		// notifica al responsabile settore (a cui è segnalato il caso (se
		// esiste) o a quello di provenienza.
		// to
//		CsItStep itStep = iterStepService.getLastIterStepByCaso(itDto);
//		adto.setOpSettoreTo(null);
//		if (itStep.getCsOSettore2() != null) {
//			adto.setOrganizzazioneTo(itStep.getCsOOrganizzazione2());
//			adto.setSettoreTo(itStep.getCsOSettore2());
//			alertService.addAlert(adto);
//		} else if (itStep.getCsOSettore1() != null) {
//			adto.setOrganizzazioneTo(itStep.getCsOOrganizzazione1());
//			adto.setSettoreTo(itStep.getCsOSettore1());
//			alertService.addAlert(adto);
//		}
	}
	
	/**
	 * Esportazione Cartella-Sociale 
	 * TODO: Valorizzare contenuti del evento 
	 * @param casoSelezionato
	 * @throws JAXBException 
	 */
	// TODO valutare che eccezione/i deve lanciare
	public void esportaCartella(CsACaso casoSelezionato, InterscambioDialogEventoBean modalBean) throws JAXBException {
		
		// TODO in ingresso mi devono arrivare i valori selezionati su pnlDialogEvento
		OrgInterscambioDTO mittente = modalBean!=null ? modalBean.getOrgInterscambioCorrente() : null;
		OrgInterscambioDTO dest = modalBean!=null ? modalBean.getOrgInterscambioDest(): null;
		
		// TODO spostare i service fuori?
		InterscambioCartellaSocialeService interscambioService = new InterscambioCartellaSocialeService();
		CartellaSocialeDataBuilder cartellaSocialeDataBuilder = new CartellaSocialeDataBuilder();
		
		// creo i due wrapper dei dati: MessageDataWrapper e CartellaSocialeWrapper
		MessageDataWrapper messageData = buildMessageData();
		CartellaSocialeWrapper cartellaSocialeData = cartellaSocialeDataBuilder.buildCartellaSocialeData(casoSelezionato);
		
		// genero l'XML della request e ne faccio la pretty print
		String contenutoXml = XMLPrinterUtils.printXmlRootElement(interscambioService.generateRequest(messageData, cartellaSocialeData));
		
		// inserisco la riga su CS_A_IMPORT_EXPORT
		EventoDTO evt = new EventoDTO("EXPORT", // TIPO EVENTO
				mittente!=null ? mittente.getNome() : "Mittente non definito", // MITTENTE 
				dest!=null ? dest.getNome() : "Destinatario non definito", // DESTINATARIO
				casoSelezionato.getCsASoggetto().getCsAAnagrafica().getNome(), // NOME SOGGETTO
				casoSelezionato.getCsASoggetto().getCsAAnagrafica().getCognome(),// COGNOME SOGGETTO
				casoSelezionato.getCsASoggetto().getCsAAnagrafica().getCf(),// CODICE FISCALE SOGGETTO
				contenutoXml	// CONTENUTO XML
		);
		
		OperatoreDTO operatore = new OperatoreDTO();
		operatore.setUserId(getCurrentOpSettore().getCsOOperatore().getUsername());
		operatore.setIdOperatoreSettore(getCurrentOpSettore().getId());
		
		evt.setOperatore(operatore);
		
		fillEnte(evt);
		
		this.creaEvento(evt);
	}
	
	
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
