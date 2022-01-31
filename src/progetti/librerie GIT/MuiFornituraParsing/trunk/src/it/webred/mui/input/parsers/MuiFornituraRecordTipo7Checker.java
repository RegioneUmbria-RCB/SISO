package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraParser;
import it.webred.mui.model.MiDupFabbricatiIdentifica;
import it.webred.mui.model.MiDupImportLog;
import it.webred.mui.model.MiDupNotaTras;

import java.util.List;

public class MuiFornituraRecordTipo7Checker extends
		MuiFornituraRecordAbstractChecker {

	@Override
	protected void addImportLog(MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column, String code, String note) {
		addImportLog(getMiDupNotaTras(),logs,MuiFornituraParser.IMMOBILEIDENTIFICA_RECORD_TYPE, "MI_DUP_FABBRICATI_IDENTIFICA",column, code,
				note);
	}

	protected void setLogEntity(Object pojo,MiDupImportLog log){
		MiDupFabbricatiIdentifica identifica= (MiDupFabbricatiIdentifica)pojo;
		log.setMiDupFabbricatiIdentifica(identifica);
	}

	@Override
	protected boolean doCheck(Object pojo, List<MiDupImportLog> logs) {
		MiDupFabbricatiIdentifica identifica= (MiDupFabbricatiIdentifica)pojo;
		checkG0000(identifica.getIdNota(),getMiDupNotaTras(),logs,"ID_NOTA");
		checkG0000(identifica.getIdImmobile(),getMiDupNotaTras(),logs,"ID_IMMOBILE");
		//TODO:R0701 – Oggetto non riscontrato in catasto
		// Verifica la presenza del fabbricato in catasto con i parametri catastali
		//Se non trovato in catasto si crea una riga di log
		//Se non presenti ma valorizzati i campi TIPO_DENUNCIA, NUMERO_PROTOCOLLO E ANNO la situazione è corretta altrimenti si crea una riga di log
		if(isEmpty(identifica.getFoglio() ) && isEmpty(identifica.getNumero() ) && isEmpty(identifica.getSubalterno() )){
			if(isEmpty(identifica.getTipoDenuncia() )){
				addImportLog(logs,"TIPO_DENUNCIA","R0703.2","Valore NULL in TIPO_DENUNCIA");
			}
			if(isEmpty(identifica.getNumeroProtocollo() )){
				addImportLog(logs,"NUMERO_PROTOCOLLO","R0703.1","Valore NULL in NUMERO_PROTOCOLLO");
			}
			if(isEmpty(identifica.getAnno() )){
				addImportLog(logs,"ANNO","R0703","Valore NULL in ANNO");
			}
		}
		else {
			if(isEmpty(identifica.getFoglio() )){
				addImportLog(logs,"FOGLIO","R0701","Valore NULL in FOGLIO");
			}
			if(isEmpty(identifica.getNumero() )){
				addImportLog(logs,"NUMERO","R0701.1","Valore NULL in NUMERO");
			}
			if(isEmpty(identifica.getSubalterno() )){
				addImportLog(logs,"SUBALTERNO","R0701.2","Valore NULL in SUBALTERNO");
			}
		}
		if(!isIn(identifica.getTipoDenuncia(),new String[]{"P","V","S",null})){
			addImportLog(logs,"TIPO_DENUNCIA","R0702","Valore non ammesso in TIPO_DENUNCIA");
		}
		if(!isYear(identifica.getAnno(),true )){
			addImportLog(logs,"ANNO","R0704","Valore non ammesso in ANNO");
		}
		return true;
	}

}
