package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraParser;
import it.webred.mui.model.MiDupImportLog;
import it.webred.mui.model.MiDupNotaTras;

import java.util.List;

public class MuiFornituraRecordTipo2Checker extends
		MuiFornituraRecordAbstractChecker {

	protected boolean doCheck(Object pojo,List<MiDupImportLog> logs) {
		MiDupNotaTras miDupNotaTras = (MiDupNotaTras) pojo;
		checkG0000(miDupNotaTras.getIdNota(),miDupNotaTras,logs,"ID_NOTA");
		checkG0000(miDupNotaTras.getNumeroNotaTras(),miDupNotaTras,logs,"NUMERO_NOTA_TRAS");
		checkG0002(miDupNotaTras.getAnnoNota(),miDupNotaTras,logs,"ANNO_NOTA");
		checkG0001(miDupNotaTras.getDataValiditaAtto(),miDupNotaTras,logs,"DATA_VALIDITA_ATTO","Data decorrenza atto");
		checkG0001(miDupNotaTras.getDataPresAtto(),miDupNotaTras,logs,"DATA_PRES_ATTO");
		checkG0001(miDupNotaTras.getDataRegInAtti(),miDupNotaTras,logs,"DATA_REG_IN_ATTI");
		checkG0000(miDupNotaTras.getNumeroRepertorio(),miDupNotaTras,logs,"NUMERO_REPERTORIO");
		
		if (!"T".equals(miDupNotaTras.getTipoNota())) {
			addImportLog(miDupNotaTras, logs,"TIPO_NOTA", "R0200", "Valore non ammesso in TIPO_NOTA");
		}
		if (!isIn(miDupNotaTras.getEsitoNota(), new int[] { 0, 1, 2 })) {
			addImportLog(miDupNotaTras, logs,"ESITO_NOTA", "R0201",
					" Valore non ammesso in Esito Nota");
		} else if (new Integer(miDupNotaTras.getEsitoNota()) != 0) {
			if (!isInteger(miDupNotaTras.getEsitoNotaNonReg(), false)) {
				addImportLog(miDupNotaTras, logs,"ESITO_NOTA_NON_REG", "R0202", " Valore non ammesso in Esito Nota non Reg");
			}
		}
		if (!isIn(miDupNotaTras.getFlagRettifica(), new int[] { 0,1})) {
			addImportLog(miDupNotaTras, logs,"FLAG_RETTIFICA", "R0203",
					" Valore non ammesso in FLAG_RETTIFICA");
		} else if(new Integer(miDupNotaTras.getFlagRettifica()) != 0){
			addImportLog(miDupNotaTras, logs,"FLAG_RETTIFICA", "R0203.1", "Valore non ammesso in FLAG_RETTIFICA, FLAG_RETTIFICA == 1 => da verificare se non non è già stata trasferita sotto forma di comunicazione");
			if(!"T".equals(miDupNotaTras.getTipoNotaRet())){
				addImportLog(miDupNotaTras, logs, "TIPO_NOTA_RET", "R0204",	"Valore non ammesso in  TIPO_NOTA_RET");
			}
			if(isEmpty(miDupNotaTras.getNumeroNotaRet())){
				addImportLog(miDupNotaTras, logs, "NUMERO_NOTA_RET","R0205",	"Valore non ammesso in  NUMERO_NOTA_RET");
			}
			if(isDate(miDupNotaTras.getDataPresAttoRet())){
				addImportLog(miDupNotaTras, logs, "DATA_PRES_ATTO_RET","R0206",	"Valore non ammesso in  DATA_PRES_ATTO_RET");
			}
		}
		//millu forza a 0 se vuoto
		if(miDupNotaTras.getRegistrazioneDif() == null || miDupNotaTras.getRegistrazioneDif().trim().equals(""))
			miDupNotaTras.setRegistrazioneDif("0");
		if(!isIn(miDupNotaTras.getRegistrazioneDif(), new int[] {0,1})){
			addImportLog(miDupNotaTras, logs,"REGISTRAZIONE_DIF", "R0207", " Valore non ammesso in REGISTRAZIONE_DIF");
		}
		
		return true;
	}

	protected void addImportLog(MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column, String code, String note) {
		addImportLog(miDupNotaTras,logs,MuiFornituraParser.NOTA_RECORD_TYPE, "MI_DUP_NOTA_TRAS",column, code, note);
	}

	protected void setLogEntity(Object pojo,MiDupImportLog log){
	}

}
