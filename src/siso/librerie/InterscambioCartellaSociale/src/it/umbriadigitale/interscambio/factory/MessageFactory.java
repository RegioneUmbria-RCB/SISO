package it.umbriadigitale.interscambio.factory;

import static it.umbriadigitale.interscambio.factory.DatatypesModFactory.*;
import static it.umbriadigitale.interscambio.factory.DefaultElementFactory.*;
import static it.umbriadigitale.interscambio.factory.ElementFactory.*;

import org.hl7.v3.request.PRSSIN001004ZZ;

import it.umbriadigitale.interscambio.constants.OIDDefaults;
import it.umbriadigitale.interscambio.data.wrapper.CartellaSocialeWrapper;
import it.umbriadigitale.interscambio.data.wrapper.MessageDataWrapper;

public class MessageFactory implements OIDDefaults {
	
	/*
		<v3:PRSS_IN001004ZZ xmlns:v3="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" ITSVersion="XML_1.0" xsi:schemaLocation="urn:hl7-org:v3 ..\Schema\PRSS_IN001004ZZ_mod.xsd">
			<v3:id root="2.16.840.1.113883.2.9.2.30.3.2.4.3" extension="messaggio xxx"/>
			<v3:creationTime value="12092017"/>
			<v3:interactionId extension="PRSS_IN001004ZZ"/>
			<v3:processingCode code="Produzione"/>
			<v3:processingModeCode code="batch"/>
			<v3:acceptAckCode code="2.16.840.1.113883.2.9.2.30.3.2.4.3" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.4.3"/>
			<v3:receiver typeCode="RCV">
				[...]
			</v3:receiver>
			<v3:sender typeCode="SND">
				[...]
			</v3:sender>
			<v3:controlActProcess classCode="CACT" moodCode="EVN">
				[...]
			</v3:controlActProcess>
		</v3:PRSS_IN001004ZZ>
	 */
	public static PRSSIN001004ZZ createRequest(MessageDataWrapper messageData, CartellaSocialeWrapper cartellaSocialeData) {
		PRSSIN001004ZZ request = createDefaultRequest();	// oggetto di ritorno
		
		// id
		request.setId(createIIRootExtension(OID_MESSAGE_ID, messageData.getId()));
		
		// creationTime
		request.setCreationTime(createTSValue(messageData.getDataCreazioneText()));
		
		// interactionId
		request.setInteractionId(createDefaultInteractionId());
		
		// processingCode
		request.setProcessingCode(createCSCode(messageData.getTipologiaProcesso()));
		
		// processingModeCode
		request.setProcessingModeCode(createCSCode(messageData.getTipologiaModalitaProcessamento()));
		
		// acceptAckCode
		request.setAcceptAckCode(createCSCodeCodeSystem(messageData.getAcceptAckCode(), OID_ACCEPT_ACK_CODE));
		
		// receiver
		request.getReceiver().add(createReceiver(messageData.getIdDestinatario()));
		
		// sender
		request.setSender(createSender(messageData.getIdMittente()));
		
		// controlActProcess
		request.setControlActProcess(ControlActProcessFactory.createControlActProcess(cartellaSocialeData));
		
		
		return request;
	}
}
