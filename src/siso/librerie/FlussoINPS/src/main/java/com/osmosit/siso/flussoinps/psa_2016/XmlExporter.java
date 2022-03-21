package com.osmosit.siso.flussoinps.psa_2016;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class XmlExporter {

	private final String outputFilePath;
	private final DataTypeUtils utils;

	protected static Logger log = Logger.getLogger("carsociale.log");



	public XmlExporter(String outputFilePath) {
		this.outputFilePath = outputFilePath;
		utils = new DataTypeUtils();
	}



	public void exportToXml(
			String nomeFlusso,
			String denominazioneEnte,
			String codiceEnte,
			String cfOperatore,
			String indirizzoEnte,
			List<Map<String, Object>> listaBeneficiariConErogazione) throws Exception {

		/* controllo validità XML */
		log.info("flusso <" + nomeFlusso + "> controllo validità");
		ValidityChecker vc = new ValidityChecker(utils);
		CheckValidityResponse checkValidityResponse = vc.checkValidity(codiceEnte, indirizzoEnte, cfOperatore, listaBeneficiariConErogazione);

		if (checkValidityResponse.isValid()) {
			log.info("controllo validità superato - inizio esportazione");
			/* esportazione */
			XmlMarshaller m = new XmlMarshaller(outputFilePath, utils);
			m.marshalToXml(nomeFlusso, denominazioneEnte, codiceEnte, cfOperatore, indirizzoEnte, listaBeneficiariConErogazione);
		} else {
			log.error("controllo di validità fallito");
			String errorTot = "";
			for (String error: checkValidityResponse.getErrorMessage()) { 
				errorTot+= "<br/> "+error;
				log.error(error);
			}
			errorTot+="<br/> Contattare l'assistenza.";
			throw new Exception("Impossibile convertire i dati in un documento XML valido:"+errorTot);
		}

	}
}
