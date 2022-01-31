package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraParser;
import it.webred.mui.model.MiDupImportLog;
import it.webred.mui.model.MiDupNotaTras;
import it.webred.mui.model.MiDupTitolarita;

import java.util.List;

public class MuiFornituraRecordTipo4Checker extends
		MuiFornituraRecordAbstractChecker {

	@Override
	protected void addImportLog(MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column, String code, String note) {
		addImportLog(getMiDupNotaTras(),logs,MuiFornituraParser.TITOLARITA_RECORD_TYPE, "MI_DUP_TITOLARITA",column, code,
				note);
	}

	@Override
	protected boolean doCheck(Object pojo, List<MiDupImportLog> logs) {
		MiDupTitolarita titolarita = (MiDupTitolarita)pojo;
		checkG0000(titolarita.getIdNota(),getMiDupNotaTras(),logs,"ID_NOTA");
		checkG0000(titolarita.getIdSoggettoNota(),getMiDupNotaTras(),logs,"ID_SOGGETTO_NOTA");
		checkG0000(titolarita.getIdImmobile(),getMiDupNotaTras(),logs,"ID_IMMOBILE");
		if(!isIn(titolarita.getTipoSoggetto(),new String[]{"P","G"})){
			addImportLog(logs,"TIPO_SOGGETTO","R0401","Valore non ammesso in TIPO_SOGGETTO");
		}
		if(!isIn(titolarita.getTipologiaImmobile(),new String[]{"F","T"})){
			addImportLog(logs,"TIPOLOGIA_IMMOBILE","R0402","Valore non ammesso in TIPOLOGIA_IMMOBILE");
		}
		if(!isIn(titolarita.getScFlagTipoTitolNota(),new String[]{"C",null})){
			addImportLog(logs,"SC_FLAG_TIPO_TITOL_NOTA","R0403","Valore non ammesso in SC_FLAG_TIPO_TITOL_NOTA");
		} else if("C".equals(titolarita.getScFlagTipoTitolNota())){
			if(isEmpty(titolarita.getScCodiceDiritto())){
				addImportLog(logs,"SC_CODICE_DIRITTO","R0404","Valore non ammesso in SC_CODICE_DIRITTO");
				//TODO:Verifica che il campo sia valorizzato, applicata se SC_TIPO_TITOL_NOTA = ‘C’
				//Se il campo contiene il valore null o il valore blank (spazio) si crea una riga di log
				//Se il valore trovato non è presente nella tabella MI_DUP_DIRITTI_DECO si crea una riga di log
			}
			java.math.BigDecimal quota= getQuota(titolarita.getScQuotaNumeratore(),titolarita.getScQuotaDenominatore());
			if(quota == null || quota.intValue() <= 0){
				addImportLog(logs,"SC_QUOTA_NUMERATORE","R0405.2","Valore non ammesso in SC_QUOTA_NUMERATORE");
				addImportLog(logs,"SC_QUOTA_DENOMINATORE","R0405","Valore non ammesso in SC_QUOTA_DENOMINATORE");
			}
			if("C".equals(titolarita.getScRegime())){
				if( quota.intValue() == 100){
					addImportLog(logs,"SC_QUOTA_NUMERATORE","R0405.3","Valore non ammesso in SC_QUOTA_NUMERATORE, Se il campo SC_REGIME = ‘C’ la percentuale non può essere = 100");
					addImportLog(logs,"SC_QUOTA_DENOMINATORE","R0405.1","Valore non ammesso in SC_QUOTA_DENOMINATORE,Se il campo SC_REGIME = ‘C’ la percentuale non può essere = 100");
				}
				if(isEmpty(titolarita.getScSoggettoRif())){
					addImportLog(logs,"SC_SOGGETTO_RIF","R0407","Valore non ammesso in SC_SOGGETTO_RIF");
				}
			}
			if(!isIn(titolarita.getScRegime(), new String[]{"S","C","P","D"})){
				addImportLog(logs,"SC_REGIME","R0406","Valore non ammesso in SC_REGIME");
			}
		}
		if(!isIn(titolarita.getSfFlagTipoTitolNota(),new String[]{"F",null})){
			addImportLog(logs,"SF_FLAG_TIPO_TITOL_NOTA","R0408","Valore non ammesso in SF_FLAG_TIPO_TITOL_NOTA");
		} else if("C".equals(titolarita.getSfFlagTipoTitolNota())){
			if(isEmpty(titolarita.getSfCodiceDiritto())){
				addImportLog(logs,"SF_CODICE_DIRITTO","R0409","Valore non ammesso in SF_CODICE_DIRITTO");
				//TODO:Verifica che il campo sia valorizzato, applicata se SF_TIPO_TITOL_NOTA = ‘F’
				//Se il campo contiene il valore null o il valore blank (spazio) si crea una riga di log
				//Se il valore trovato non è presente nella tabella MI_DUP_DIRITTI_DECO si crea una riga di log
			}
			java.math.BigDecimal quota= getQuota(titolarita.getSfQuotaNumeratore(),titolarita.getSfQuotaDenominatore());
			if(quota == null || quota.intValue() <= 0){
				addImportLog(logs,"SF_QUOTA_NUMERATORE","R0410.2","Valore non ammesso in SF_QUOTA_NUMERATORE");
				addImportLog(logs,"SF_QUOTA_DENOMINATORE","R0410","Valore non ammesso in SF_QUOTA_DENOMINATORE");
			}
			if("C".equals(titolarita.getSfRegime())){
				if( quota.intValue() == 100){
					addImportLog(logs,"SF_QUOTA_NUMERATORE","R0410.3","Valore non ammesso in SF_QUOTA_NUMERATORE, Se il campo SF_REGIME = ‘C’ la percentuale non può essere = 100");
					addImportLog(logs,"SF_QUOTA_DENOMINATORE","R0410.1","Valore non ammesso in SF_QUOTA_DENOMINATORE,Se il campo SF_REGIME = ‘C’ la percentuale non può essere = 100");
				}
				if(isEmpty(titolarita.getSfSoggettoRif())){
					addImportLog(logs,"SF_SOGGETTO_RIF","R0412","Valore non ammesso in SF_SOGGETTO_RIF");
				}
			}
			if(!isIn(titolarita.getSfRegime(), new String[]{"S","C","P","D"})){
				addImportLog(logs,"SF_REGIME","R0411","Valore non ammesso in SF_REGIME");
			}
		}
		return true;
	}

	protected void setLogEntity(Object pojo,MiDupImportLog log){
		MiDupTitolarita titolarita = (MiDupTitolarita)pojo;
		log.setMiDupTitolarita(titolarita);
	}

	private java.math.BigDecimal getQuota(String num,String den){
		java.math.BigDecimal quota;
		try {
			double numeratore = Integer.valueOf(num);
			double denominatore = Integer.valueOf(den);
			                         				
			quota = java.math.BigDecimal
				.valueOf(numeratore / denominatore / 10d);
		} catch (Throwable e) {
			return null;
		}
		return quota;
	}
}
