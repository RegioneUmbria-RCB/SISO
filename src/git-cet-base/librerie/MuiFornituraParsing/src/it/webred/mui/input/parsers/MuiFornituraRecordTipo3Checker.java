package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraParser;
import it.webred.mui.model.MiDupImportLog;
import it.webred.mui.model.MiDupNotaTras;
import it.webred.mui.model.MiDupSoggetti;

import java.util.List;

public class MuiFornituraRecordTipo3Checker extends
		MuiFornituraRecordAbstractChecker {

	@Override
	protected void addImportLog(MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column, String code, String note) {
		addImportLog(getMiDupNotaTras(),logs,MuiFornituraParser.SOGGETTO_RECORD_TYPE, "MI_DUP_SOGGETTI",column, code,
				note);
	}

	@Override
	protected boolean doCheck(Object pojo, List<MiDupImportLog> logs) {
		MiDupSoggetti soggetto = (MiDupSoggetti)pojo;
		checkG0000(soggetto.getIdNota(),getMiDupNotaTras(),logs,"ID_NOTA");
		checkG0000(soggetto.getIdSoggettoNota(),getMiDupNotaTras(),logs,"ID_SOGGETTO_NOTA");
		if(!isIn(soggetto.getTipoSoggetto(),new String[]{"P","G"})){
			addImportLog(logs,"TIPO_SOGGETTO","R0301","Valore non ammesso in TIPO_SOGGETTO");
		}
		else if("P".equals(soggetto.getTipoSoggetto())){
			if(isEmpty(soggetto.getCognome())){
				addImportLog(logs,"COGNOME","R0302","Valore non ammesso in COGNOME");
			}
			if(isEmpty(soggetto.getNome())){
				addImportLog(logs,"NOME","R0303","Valore non ammesso in NOME");
			}
			if(!isIn(soggetto.getSesso(),new int[] {1,2})){
				addImportLog(logs,"SESSO","R0304","Valore non ammesso in SESSO");
			}
			if(!isDate(soggetto.getDataNascita())){
				addImportLog(logs,"DATA_NASCITA","R0305","Valore non ammesso in DATA_NASCITA");
			}
			//TODO: R0306 Verifica la presenza del codice belfiore fornito, applicata se TIPO_SOGGETTO = ‘P’
			//Se il codice belfiore non esiste nella tabella SIT_COMUNI, si crea una riga di log
			if(isEmpty(soggetto.getLuogoNascita())){
				addImportLog(logs,"LUOGO_NASCITA","R0306","Valore non ammesso in LUOGO_NASCITA");
			}
			//TODO: R0307 Verifica la validita del codice fiscale, applicata se TIPO_SOGGETTO = ‘P’
			//Se il codice fiscale calcolato utilizzando i campi del record è diverso da quello acquisito, si crea una riga di log
			if(isEmpty(soggetto.getCodiceFiscale())){
				addImportLog(logs,"CODICE_FISCALE","R0307","Valore non ammesso in CODICE_FISCALE");
			}
		}
		else if("G".equals(soggetto.getTipoSoggetto())){
			if(isEmpty(soggetto.getDenominazione())){
				addImportLog(logs,"DENOMINAZIONE","R0308","Valore non ammesso in DENOMINAZIONE");
			}
			if(isEmpty(soggetto.getSede())){
				addImportLog(logs,"SEDE","R0309","Valore non ammesso in SEDE");
			}
			if(isEmpty(soggetto.getCodiceFiscaleG())){
				addImportLog(logs,"CODICE_FISCALE_G","R0310","Valore non ammesso in CODICE_FISCALE_G");
			}
		}
		return true;
	}
	protected void setLogEntity(Object pojo,MiDupImportLog log){
		log.setMiDupSoggetti((MiDupSoggetti)pojo);
	}
}
