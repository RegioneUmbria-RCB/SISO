package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraParser;
import it.webred.mui.model.MiDupImportLog;
import it.webred.mui.model.MiDupNotaTras;
import it.webred.mui.model.MiDupTerreniInfo;

import java.util.List;

public class MuiFornituraRecordTipo8Checker extends
		MuiFornituraRecordAbstractChecker {

	@Override
	protected void addImportLog(MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column, String code, String note) {
		addImportLog(getMiDupNotaTras(),logs,MuiFornituraParser.TERRENO_RECORD_TYPE, "MI_DUP_TERRENI_INFO",column, code,note);
	}

	protected void setLogEntity(Object pojo,MiDupImportLog log){
		MiDupTerreniInfo terreno= (MiDupTerreniInfo)pojo;
		log.setMiDupTerreniInfo(terreno);
	}

	@Override
	protected boolean doCheck(Object pojo, List<MiDupImportLog> logs) {
		MiDupTerreniInfo terreno= (MiDupTerreniInfo)pojo;
		checkG0000(terreno.getIdNota(),getMiDupNotaTras(),logs,"ID_NOTA");
		checkG0000(terreno.getIdImmobile(),getMiDupNotaTras(),logs,"ID_IMMOBILE");
		//TODO:R0701 – Oggetto non riscontrato in catasto
		// Verifica la presenza del fabbricato in catasto con i parametri catastali
		//Se non trovato in catasto si crea una riga di log
		//Se non presenti ma valorizzati i campi TIPO_DENUNCIA, NUMERO_PROTOCOLLO E ANNO la situazione è corretta altrimenti si crea una riga di log
		if(!isIn(terreno.getTipologiaImmobile(),new String[]{"A","V","T"} )){
			addImportLog(logs,"TIPOLOGIA_IMMOBILE","R0801","Valore non amesso in TIPOLOGIA_IMMOBILE");
		}
		if(!isEmpty(terreno.getCodiceEsito())){
			addImportLog(logs,"CODICE_ESITO","R0802","Valore non NULL in CODICE_ESITO ricerca a catasto effettuata dal notaio non ha avuto esito positivo");
		}
		if(isEmpty(terreno.getFoglio() )){
			addImportLog(logs,"FOGLIO","R0803","Valore NULL in FOGLIO verificare la presenza del fabbricato in catasto con i parametri catastali");
		}
		if(isEmpty(terreno.getNumero() )){
			addImportLog(logs,"NUMERO","R0803.1","Valore NULL in NUMERO verificare la presenza del fabbricato in catasto con i parametri catastali");
		}
		if(isEmpty(terreno.getSubalterno() )){
			addImportLog(logs,"SUBALTERNO","R0803.2","Valore NULL in SUBALTERNO verificare la presenza del fabbricato in catasto con i parametri catastali");
		}
		if(!isIn(terreno.getFlagReddito(),new int[]{0,1} )){
			addImportLog(logs,"FLAG_REDDITO","R0804","Valore non amesso in FLAG_REDDITO");
		}
		if("T".equals(terreno.getTipologiaImmobile())){
			if(isIn(terreno.getRedditoDominicaleEuro(),new int[]{0} )){
				addImportLog(logs,"REDDITO_DOMINICALE_EURO","R0805","Valore 0 non ammesso in REDDITO_DOMINICALE_EURO va controllata in seguito ma la situazione non è bloccante");
			}
			if(isEmpty(terreno.getRedditoDominicaleEuro())){
				addImportLog(logs,"REDDITO_DOMINICALE_EURO","R0805.1","Valore non ammesso in REDDITO_DOMINICALE_EURO");
			}
		}
		return true;
	}

}
