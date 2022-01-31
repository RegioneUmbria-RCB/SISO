package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraParser;
import it.webred.mui.model.MiDupFabbricatiInfo;
import it.webred.mui.model.MiDupImportLog;
import it.webred.mui.model.MiDupNotaTras;

import java.util.List;

public class MuiFornituraRecordTipo6Checker extends
		MuiFornituraRecordAbstractChecker {

	@Override
	protected void addImportLog(MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column, String code, String note) {
		addImportLog(getMiDupNotaTras(),logs,MuiFornituraParser.IMMOBILEINFO_RECORD_TYPE, "MI_DUP_FABBRICATI_INFO",column, code,
				note);
	}

	protected void setLogEntity(Object pojo,MiDupImportLog log){
		MiDupFabbricatiInfo fabbricato= (MiDupFabbricatiInfo)pojo;
		log.setMiDupFabbricatiInfo(fabbricato);
	}

	@Override
	protected boolean doCheck(Object pojo, List<MiDupImportLog> logs) {
		MiDupFabbricatiInfo fabbricato= (MiDupFabbricatiInfo)pojo;
		checkG0000(fabbricato.getIdNota(),getMiDupNotaTras(),logs,"ID_NOTA");
		checkG0000(fabbricato.getCategoria(),getMiDupNotaTras(),logs,"CATEGORIA");
		checkG0000(fabbricato.getClasse(),getMiDupNotaTras(),logs,"CLASSE");
		checkG0000(fabbricato.getRenditaEuro(),getMiDupNotaTras(),logs,"RENDITA_EURO");
		checkG0000(fabbricato.getTToponimo(),getMiDupNotaTras(),logs,"T_TOPONIMO");
		checkG0000(fabbricato.getTIndirizzo(),getMiDupNotaTras(),logs,"T_INDIRIZZO");
		checkG0000(fabbricato.getTCivico1(),getMiDupNotaTras(),logs,"T_CIVICO1");
		if(!isIn(fabbricato.getTipologiaImmobile(),new String[]{"F"})){
			addImportLog(logs,"TIPOLOGIA_IMMOBILE","R0601","Valore non ammesso in TIPOLOGIA_IMMOBILE");
		}
		if(fabbricato.getCodiceEsito()!=null){
			addImportLog(logs,"CODICE_ESITO","R0602","Valore non NULL in CODICE_ESITO ricerca a catasto effettuata dal notaio non ha avuto esito positivo");
		}
		if("A".equals(fabbricato.getCategoria()) && isEmpty(fabbricato.getVani())){
			addImportLog(logs,"VANI","R0603","Valore non ammesso in VANI");
		}
		if("B".equals(fabbricato.getCategoria()) && isEmpty(fabbricato.getMc())){
			addImportLog(logs,"MC","R0604","Valore non ammesso in MC");
		}
		if("C".equals(fabbricato.getCategoria()) && isEmpty(fabbricato.getMq())){
			addImportLog(logs,"MQ","R0605","Valore non ammesso in MQ");
		}
		if(!isEmpty(fabbricato.getIdCatastaleImmobile()) && isEmpty(fabbricato.getCIndirizzo())){
			addImportLog(logs,"C_INDIRIZZO","R0606","Valore NULL non ammesso in C_INDIRIZZO se  immobile riconosciuto a catasto");
		}
		return true;
	}

}
