package it.webred.cs.csa.web.manbean.amministrazione;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import it.umbriadigitale.interscambio.data.wrapper.MessageDataWrapper;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.utilities.DateTimeUtils;

public class IterCartellaSociale extends CsUiCompBaseBean {

 
	protected AccessTableSoggettoSessionBeanRemote  soggettoService = (AccessTableSoggettoSessionBeanRemote) 
			getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
	
	protected AccessTableIterStepSessionBeanRemote iterService =
			(AccessTableIterStepSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");

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
	
	public List<SelectItem> getListaIterStati(CeTBaseObject cet) {
		return convertiLista(confService.getListaIterStati(cet));
	}

	public List<SelectItem> findOperatoriSettore(OperatoreDTO dto) {
		List<SelectItem> operatores = new ArrayList<SelectItem>();
		try{
			List<KeyValueDTO> result = confEnteService.findListaOperatoreSettoreBySettore(dto);
			for (KeyValueDTO it : result)
				operatores.add(new SelectItem( it.getCodice(), it.getDescrizione()));	
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}	
		return operatores;
	}

}

