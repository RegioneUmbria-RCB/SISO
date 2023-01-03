package it.webred.rulengine.brick.loadDwh.load.schedemartelli.v1;


import java.util.ArrayList;

import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.schedemartelli.SchedeMartelliConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.schedemartelli.SchedeMartelliTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.schedemartelli.bean.Testata;

public class Env<T extends SchedeMartelliTipoRecordEnv<Testata>> extends SchedeMartelliConcreteImportEnv<T> {
	
	private String SQL_RE_SCHEDE_MARTELLI_UNO = getProperty("sql.RE_SCHEDE_MARTELLI_UNO");

	//private EnvSitOmniadoc envSitOmniadoc = new EnvSitOmniadoc(envImport.RE_OMNIADOC_UNO,  envImport.getTestata().getProvenienza(), new String[]{"NR_PROT_CAPOFILA"});
	private EnvSitSchedeMartelli envSitSchedeMartelli = new EnvSitSchedeMartelli(envImport.RE_SCHEDE_MARTELLI_UNO,  envImport.getTestata().getProvenienza(), new String[]{""});


	public EnvSitSchedeMartelli getEnvSitSchedeMartelli() {
		return envSitSchedeMartelli;
	}


	public String getSQL_RE_SCHEDE_MARTELLI_UNO() {
		return SQL_RE_SCHEDE_MARTELLI_UNO;
	}


	public Env(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		ArrayList<String> alChiavi = new ArrayList<String>();
		//alChiavi.add("NR_PROT_CAPOFILA");
		hm.put(envImport.RE_SCHEDE_MARTELLI_UNO, alChiavi);
		
		return hm;
	}
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String> tr = new LinkedHashMap<String, String>();
		tr.put(envImport.RE_SCHEDE_MARTELLI_UNO , null);
		
		return tr;
	}
	
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_SCHEDE_MARTELLI");
		
		return tabs;
	}
	
}

