package com.osmosit.siso.flussoinps.sinba_2018;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class XmlSinbaExporter {

	private final String outputFilePath;
	private final DataTypeSinbaUtils utils;

	protected static Logger log = Logger.getLogger("carsociale.log");



	public XmlSinbaExporter(String outputFilePath) {
		this.outputFilePath = outputFilePath;
		utils = new DataTypeSinbaUtils();
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
		ValiditySinbaChecker vc = new ValiditySinbaChecker(utils);
		CheckValiditySinbaResponse checkValiditySinbaResponse = vc.checkValidity(codiceEnte, indirizzoEnte, cfOperatore, listaBeneficiariConErogazione);

		if (checkValiditySinbaResponse.isValid()) {
			log.info("controllo validità superato - inizio esportazione");
			/* esportazione */
			XmlSinbaMarshaller m = new XmlSinbaMarshaller(outputFilePath, utils);
			m.marshalToXml(nomeFlusso, denominazioneEnte, codiceEnte, cfOperatore, indirizzoEnte, listaBeneficiariConErogazione);
		} else {
			log.error("controllo di validità fallito");
			String errorTot = "";
			for (String error: checkValiditySinbaResponse.getErrorMessage()) { 
				errorTot+= "<br/> "+error;
				log.error(error);
			}
			errorTot+="<br/> Contattare l'assistenza.";
			throw new Exception("Impossibile convertire i dati in un documento XML valido:"+errorTot);
		}

	}
}
