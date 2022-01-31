package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraParser;
import it.webred.mui.model.MiDupImportLog;
import it.webred.mui.model.MiDupIndirizziSog;
import it.webred.mui.model.MiDupNotaTras;

import java.util.List;

public class MuiFornituraRecordTipo5Checker extends
		MuiFornituraRecordAbstractChecker {

	@Override
	protected void addImportLog(MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column, String code, String note) {
		addImportLog(getMiDupNotaTras(),logs,MuiFornituraParser.INDIRIZZO_RECORD_TYPE, "MI_DUP_INDIRIZZI_SOG",column, code,
				note);
	}

	protected void setLogEntity(Object pojo,MiDupImportLog log){
		MiDupIndirizziSog indirizzo = (MiDupIndirizziSog)pojo;
		log.setMiDupIndirizziSog(indirizzo);
	}

	@Override
	protected boolean doCheck(Object pojo, List<MiDupImportLog> logs) {
		MiDupIndirizziSog indirizzo = (MiDupIndirizziSog)pojo;
		checkG0000(indirizzo.getIdNota(),getMiDupNotaTras(),logs,"ID_NOTA");
		checkG0000(indirizzo.getIdSoggettoNota(),getMiDupNotaTras(),logs,"ID_SOGGETTO_NOTA");
		checkG0000(indirizzo.getComune(),getMiDupNotaTras(),logs,"COMUNE");
		checkG0000(indirizzo.getProvincia(),getMiDupNotaTras(),logs,"PROVINCIA");
		checkG0000(indirizzo.getCap(),getMiDupNotaTras(),logs,"CAP");
		checkG0000(indirizzo.getIndirizzo(),getMiDupNotaTras(),logs,"INDIRIZZO");
		if(!isIn(indirizzo.getTipoIndirizzo(),new String[]{"0","1"})){
			addImportLog(logs,"TIPO_INDIRIZZO","R0501","Valore non ammesso in TIPO_INDIRIZZO");
		}
		return true;
	}

}
